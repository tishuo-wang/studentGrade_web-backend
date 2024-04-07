package redlib.backend.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import redlib.backend.dao.StudentMapper;
import redlib.backend.dto.StudentDTO;
import redlib.backend.dto.query.StudentQueryDTO;
import redlib.backend.model.Admin;
import redlib.backend.model.Page;
import redlib.backend.model.Student;
import redlib.backend.model.Token;
import redlib.backend.service.StudentService;
import redlib.backend.service.utils.StudentUtils;
import redlib.backend.utils.FormatUtils;
import redlib.backend.utils.PageUtils;
import redlib.backend.utils.ThreadContextHolder;
import redlib.backend.utils.XlsUtils;
import redlib.backend.vo.StudentVO;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 分页获取部门信息
     *
     * @param queryDTO 查询条件和分页信息
     * @return 带分页信息的部门数据列表
     */
    @Override
    public Page<StudentVO> listByPage(StudentQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new StudentQueryDTO();
        }
        //模糊搜索
        queryDTO.setStudentname(FormatUtils.makeFuzzySearchTerm(queryDTO.getStudentname()));
        queryDTO.setUserCode(FormatUtils.makeFuzzySearchTerm(queryDTO.getUserCode()));
        

        Integer size = studentMapper.count(queryDTO);
        PageUtils pageUtils = new PageUtils(queryDTO.getCurrent(), queryDTO.getPageSize(), size);
        if (size == 0) {
            // 没有命中，则返回空数据。
            return pageUtils.getNullPage();
        }
        // 利用myBatis到数据库中查询数据，以分页的方式
        List<Student> list = studentMapper.list(queryDTO, pageUtils.getOffset(), pageUtils.getLimit());
        // 提取list列表中的创建人字段，到一个Set集合中去
        Set<Integer> studentIds = list.stream().map(Student::getCreatedBy).collect(Collectors.toSet());
        // 获取id到人名的映射
        Map<Integer, String> nameMap = getNameMap(studentIds);
        List<StudentVO> voList = new ArrayList<>();
        for (Student student : list) {
            StudentVO vo = StudentUtils.convertToVO(student, nameMap);
            // Department对象转VO对象
            voList.add(vo);
        }
        return new Page<>(pageUtils.getCurrent(), pageUtils.getPageSize(), pageUtils.getTotal(), voList);
    }

    @Override
    public Integer addStudent(StudentDTO studentDTO) {
        Token token = ThreadContextHolder.getToken();
        // 校验输入数据正确性
        StudentUtils.validateStudent(studentDTO);
        Student student = studentMapper.getByUserCode(studentDTO.getUserCode());
        Assert.isNull(student, "学号已经存在，请另外指定一个");

        // 创建实体对象，用以保存到数据库
        student = new Student();
        // 将输入的字段全部复制到实体对象中
        BeanUtils.copyProperties(studentDTO, student);
        student.setCreatedBy(token.getUserId());
        student.setUpdatedBy(token.getUserId());
        // 调用DAO方法保存到数据库表
        studentMapper.insert(student);
        return student.getId();
    }

    @Override
    public StudentDTO getById(Integer id) {
        Assert.notNull(id, "请提供id");
        Assert.notNull(id, "id不能为空");
        Student student = studentMapper.selectByPrimaryKey(id);
        Assert.notNull(student, "id不存在");
        StudentDTO sto = new StudentDTO();
        BeanUtils.copyProperties(student, sto);
        return sto;
    }

    @Override
    public Integer updateStudent(StudentDTO studentDTO) {
        Token token = ThreadContextHolder.getToken();

        StudentUtils.validateStudent(studentDTO);
        Assert.notNull(studentDTO.getId(), "id不能为空");

        Student student = studentMapper.selectByPrimaryKey(studentDTO.getId());
        Assert.notNull(student, "没有找到，Id为：" + studentDTO.getId());
        BeanUtils.copyProperties(studentDTO, student);
        student.setUpdatedBy(token.getUserId());
        studentMapper.updateByPrimaryKey(student);
        return student.getId();
    }

    @Override
    public void deleteByCodes(List<Integer> ids) {
        Assert.notEmpty(ids, "id列表不能为空");
        studentMapper.deleteByCodes(ids);
    }

    @Override
    public Workbook export(StudentQueryDTO queryDTO) {
        queryDTO.setPageSize(100);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", "ID");
        map.put("studentname", "学生姓名");
        map.put("sex", "性别(男1女2)");
        map.put("userCode", "学号");
        map.put("parentname", "家长姓名");
        map.put("phone", "家长电话");
        map.put("classid", "班级号");

        final AtomicBoolean finalPage = new AtomicBoolean(false);
        Workbook workbook = XlsUtils.exportToExcel(page -> {
            if (finalPage.get()) {
                return null;
            }
            queryDTO.setCurrent(page);
            List<StudentVO> list = listByPage(queryDTO).getList();
            if (list.size() != 100) {
                finalPage.set(true);
            }
            return list;
        }, map);

        return workbook;
    }

    @Override
    public int importStudent(InputStream inputStream, String fileName) throws Exception {
        Assert.hasText(fileName, "文件名不能为空");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ID", "id");
        map.put("学生姓名", "studentname");
        map.put("性别(男1女2)", "sex");
        map.put("学号", "userCode");
        map.put("家长姓名", "parentname");
        map.put("家长电话", "phone");
        map.put("班级号", "classid");
        AtomicInteger row = new AtomicInteger(0);
        XlsUtils.importFromExcel(inputStream, fileName, (studentDTO) -> {
            addStudent(studentDTO);
            row.incrementAndGet();
        }, map, StudentDTO.class);

        return row.get();
    }


    @Override
    public Map<Integer, String> getNameMap(Set<Integer> studentIds) {
        Map<Integer, String> studentMap;
        if (!CollectionUtils.isEmpty(studentIds)) {
            List<Student> student = studentMapper.listByIds(new ArrayList<>(studentIds));
            studentMap = student.stream().collect(Collectors.toMap(Student::getId, Student::getStudentname));
        } else {
            studentMap = new HashMap<>();
        }

        return studentMap;
    }


    @Override
    public List<Integer> listClassid(){
        List<Integer> a  = new ArrayList<>();
        List<Integer> result  = new ArrayList<>();
        List<Student> studentList = studentMapper.listClassId();
        for(Student student : studentList){
            if(! a.contains(student.getClassid())){
                a.add(student.getClassid());
            }
        }
        Object[] tmp=a.toArray();
        Arrays.sort(tmp);
        for(Object i : tmp){
            result.add(Integer.valueOf(i.toString()));
        }

        return result;
    }

    @Override
    public Integer studentNum(Integer stuClass){
        return studentMapper.count_studentNum(stuClass);
    }
}
