package redlib.backend.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import redlib.backend.dao.ClassesMapper;
import redlib.backend.dao.StudentMapper;
import redlib.backend.dto.ClassesDTO;
import redlib.backend.dto.StudentDTO;
import redlib.backend.dto.query.ClassesQueryDTO;
import redlib.backend.model.Classes;
import redlib.backend.model.Page;
import redlib.backend.model.Student;
import redlib.backend.model.Token;
import redlib.backend.service.AdminService;
import redlib.backend.service.ClassesService;
import redlib.backend.service.utils.ClassesUtils;
import redlib.backend.service.utils.StudentUtils;
import redlib.backend.utils.FormatUtils;
import redlib.backend.utils.PageUtils;
import redlib.backend.utils.ThreadContextHolder;
import redlib.backend.utils.XlsUtils;
import redlib.backend.vo.ClassesVO;
import redlib.backend.vo.StudentVO;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ClassesServiceImpl implements ClassesService {

    @Autowired
    private ClassesMapper classesMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Page<ClassesVO> listByPage(ClassesQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new ClassesQueryDTO();
        }
        //模糊搜索
        queryDTO.setClassid(FormatUtils.makeFuzzySearchTerm(queryDTO.getClassid()));
        queryDTO.setTeachername(FormatUtils.makeFuzzySearchTerm(queryDTO.getTeachername()));
        queryDTO.setClassname(FormatUtils.makeFuzzySearchTerm(queryDTO.getClassname()));

        Integer size = classesMapper.count(queryDTO);
        PageUtils pageUtils = new PageUtils(queryDTO.getCurrent(), queryDTO.getPageSize(), size);
        if (size == 0) {
            // 没有命中，则返回空数据。
            return pageUtils.getNullPage();
        }
        // 利用myBatis到数据库中查询数据，以分页的方式
        List<Classes> list = classesMapper.list(queryDTO, pageUtils.getOffset(), pageUtils.getLimit());
        List<ClassesVO> voList = new ArrayList<>();
        for (Classes classes : list) {
            Integer classidNum = Integer.valueOf(classes.getClassid());
            Integer studentNumber = studentMapper.count_studentNum(classidNum);
            ClassesVO vo = ClassesUtils.convertToVO(classes, studentNumber);
            //转VO对象
            voList.add(vo);
        }
        return new Page<>(pageUtils.getCurrent(), pageUtils.getPageSize(), pageUtils.getTotal(), voList);
    }

    @Override
    public Integer addClasses(ClassesDTO classesDTO) {
        Token token = ThreadContextHolder.getToken();
        // 校验输入数据正确性
        ClassesUtils.validateClasses(classesDTO);
        // 创建实体对象，用以保存到数据库
        Classes classes = new Classes();
        // 将输入的字段全部复制到实体对象中
        BeanUtils.copyProperties(classesDTO, classes);
        classes.setCreatedAt(new Date());
        classes.setUpdatedAt(new Date());
        // 调用DAO方法保存到数据库表
        classesMapper.insert(classes);
        return classes.getId();
    }

    @Override
    public ClassesDTO getById(Integer id) {
        Assert.notNull(id, "请提供id");
        Assert.notNull(id, "id不能为空");
        Classes classes = classesMapper.selectByPrimaryKey(id);
        Assert.notNull(classes, "id不存在");
        ClassesDTO sto = new ClassesDTO();
        BeanUtils.copyProperties(classes, sto);
        return sto;
    }

    @Override
    public Integer updateClasses(ClassesDTO classesDTO) {
        // 校验输入数据正确性
        ClassesUtils.validateClasses(classesDTO);
        Assert.notNull(classesDTO.getId(), "id不能为空");
        Classes classes = classesMapper.selectByPrimaryKey(classesDTO.getId());
        Assert.notNull(classes, "没有找到，Id为：" + classesDTO.getId());
        BeanUtils.copyProperties(classesDTO, classes);
        classes.setUpdatedAt(new Date());
        classesMapper.updateByPrimaryKey(classes);
        return classes.getId();
    }

    @Override
    public void deleteByCodes(List<Integer> ids) {
        Assert.notEmpty(ids, "id列表不能为空");
        classesMapper.deleteByCodes(ids);
    }

    @Override
    public Workbook export(ClassesQueryDTO queryDTO) {
        queryDTO.setPageSize(100);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", "ID");
        map.put("classid", "班级号");
        map.put("classname", "班级名");
        map.put("number", "人数");
        map.put("teachername", "班主任");
        map.put("teacherphone", "班主任电话");
        map.put("position", "位置");

        final AtomicBoolean finalPage = new AtomicBoolean(false);
        Workbook workbook = XlsUtils.exportToExcel(page -> {
            if (finalPage.get()) {
                return null;
            }
            queryDTO.setCurrent(page);
            List<ClassesVO> list = listByPage(queryDTO).getList();
            if (list.size() != 100) {
                finalPage.set(true);
            }
            return list;
        }, map);

        return workbook;
    }

    @Override
    public int importClasses(InputStream inputStream, String fileName) throws Exception {
        Assert.hasText(fileName, "文件名不能为空");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ID", "id");
        map.put("班级号", "classid");
        map.put("班级名", "classname");
        map.put("人数", "number");
        map.put("班主任", "teachername");
        map.put("班主任电话", "teacherphone");
        map.put("位置", "position");
        AtomicInteger row = new AtomicInteger(0);
        XlsUtils.importFromExcel(inputStream, fileName, (classesDTO) -> {
            addClasses(classesDTO);
            row.incrementAndGet();
        }, map, ClassesDTO.class);

        return row.get();
    }


}
