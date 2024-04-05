package redlib.backend.service.impl;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import redlib.backend.dao.GradeMapper;
import redlib.backend.dto.GradeDTO;
import redlib.backend.dto.query.GradeAverageQueryDTO;
import redlib.backend.dto.query.GradeQueryDTO;
import redlib.backend.model.Grade;
import redlib.backend.model.Page;
import redlib.backend.model.Token;
import redlib.backend.service.GradeService;
import redlib.backend.service.utils.GradeUtils;
import redlib.backend.utils.FormatUtils;
import redlib.backend.utils.PageUtils;
import redlib.backend.utils.ThreadContextHolder;
import redlib.backend.utils.XlsUtils;
import redlib.backend.vo.GradeAverVO;
import redlib.backend.vo.GradeVO;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.apache.ibatis.javassist.CtMethod.ConstParameter.string;


@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Override
    public Page<GradeVO> listByPage(GradeQueryDTO queryDTO) {
        if (queryDTO == null) {
            queryDTO = new GradeQueryDTO();
        }
        //模糊搜索
        queryDTO.setUserCode(FormatUtils.makeFuzzySearchTerm(queryDTO.getUserCode()));
        queryDTO.setName(FormatUtils.makeFuzzySearchTerm(queryDTO.getName()));
        queryDTO.setClassid(FormatUtils.makeFuzzySearchTerm(queryDTO.getClassid()));
        queryDTO.setTerm(FormatUtils.makeFuzzySearchTerm(queryDTO.getTerm()));

        Integer size = gradeMapper.count(queryDTO);
        PageUtils pageUtils = new PageUtils(queryDTO.getCurrent(), queryDTO.getPageSize(), size);
        if (size == 0) {
            // 没有命中，则返回空数据。
            return pageUtils.getNullPage();
        }
        // 利用myBatis到数据库中查询数据，以分页的方式
        List<Grade> list = gradeMapper.list(queryDTO, pageUtils.getOffset(), pageUtils.getLimit());
        List<GradeVO> voList = new ArrayList<>();
        for (Grade grade : list) {
            GradeVO vo = GradeUtils.convertToVO(grade);
            //转VO对象
            voList.add(vo);
        }
        return new Page<>(pageUtils.getCurrent(), pageUtils.getPageSize(), pageUtils.getTotal(), voList);
    }

    @Override
    public Integer addGrade(GradeDTO gradeDTO) {
        Token token = ThreadContextHolder.getToken();
        // 校验输入数据正确性
        GradeUtils.validateGrade(gradeDTO);
        // 创建实体对象，用以保存到数据库
        Grade grade = new Grade();
        // 将输入的字段全部复制到实体对象中
        BeanUtils.copyProperties(gradeDTO, grade);
        // 调用DAO方法保存到数据库表
        gradeMapper.insert(grade);
        return grade.getId();
    }

    @Override
    public GradeDTO getById(Integer id) {
        Assert.notNull(id, "请提供id");
        Assert.notNull(id, "id不能为空");
        Grade grade = gradeMapper.selectByPrimaryKey(id);
        Assert.notNull(grade, "id不存在");
        GradeDTO sto = new GradeDTO();
        BeanUtils.copyProperties(grade, sto);
        return sto;
    }

    @Override
    public Page<GradeAverVO> listAverageGrade(GradeAverageQueryDTO queryDTO){
        List<GradeAverVO> gradeVOList = new ArrayList<>();

        if (queryDTO == null) {
            queryDTO = new GradeAverageQueryDTO();
        }

        queryDTO.setAcademicyear(FormatUtils.makeFuzzySearchTerm(queryDTO.getAcademicyear()));
        queryDTO.setTerm(FormatUtils.makeFuzzySearchTerm(queryDTO.getTerm()));

        List<Grade> list = gradeMapper.count_aver(queryDTO);
        for(Grade grade : list){
            GradeAverVO averGrade = new GradeAverVO();
            BeanUtils.copyProperties(grade, averGrade);
            // 保留两位小数
            averGrade.setAverageChinese((float) (Math.round(averGrade.getAverageChinese()*100)*0.01));
            averGrade.setAverageMath((float) (Math.round(averGrade.getAverageMath()*100)*0.01));
            averGrade.setAverageEnglish((float) (Math.round(averGrade.getAverageEnglish()*100)*0.01));
            averGrade.setTotal((float) (Math.round(averGrade.getTotal()*100)*0.01));
            gradeVOList.add(averGrade);
        }

        int size = list.size();
        PageUtils pageUtils = new PageUtils(queryDTO.getCurrent(), queryDTO.getPageSize(), size);
        if (size == 0) {
            // 没有命中，则返回空数据。
            return pageUtils.getNullPage();
        }
        
        return new Page<>(pageUtils.getCurrent(), pageUtils.getPageSize(), pageUtils.getTotal(), gradeVOList);
    }

    @Override
    public Integer updateGrade(GradeDTO gradeDTO) {
        // 校验输入数据正确性
        GradeUtils.validateGrade(gradeDTO);
        Assert.notNull(gradeDTO.getId(), "id不能为空");
        Grade grade = gradeMapper.selectByPrimaryKey(gradeDTO.getId());
        Assert.notNull(grade, "没有找到，Id为：" + gradeDTO.getId());
        BeanUtils.copyProperties(gradeDTO, grade);
        gradeMapper.updateByPrimaryKey(grade);
        return grade.getId();
    }

    @Override
    public void deleteByCodes(List<Integer> ids) {
        Assert.notEmpty(ids, "id列表不能为空");
        gradeMapper.deleteByCodes(ids);
    }

    @Override
    public Workbook export(GradeQueryDTO queryDTO) {
        queryDTO.setPageSize(100);
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", "ID");
        map.put("name", "学生姓名");
        map.put("userCode", "学号");
        map.put("classid", "班级号");
        map.put("chineseGrade", "语文成绩");
        map.put("mathGrade", "数学成绩");
        map.put("englishGrade", "英语成绩");
        map.put("recordtime", "录入时间");
        map.put("academicyear", "学年");
        map.put("term", "学期");
        final AtomicBoolean finalPage = new AtomicBoolean(false);
        Workbook workbook = XlsUtils.exportToExcel(page -> {
            if (finalPage.get()) {
                return null;
            }
            queryDTO.setCurrent(page);
            List<GradeVO> list = listByPage(queryDTO).getList();
            if (list.size() != 100) {
                finalPage.set(true);
            }
            return list;
        }, map);

        return workbook;
    }

    @Transactional
    @Override
    public int importGrade(InputStream inputStream, String fileName) throws Exception {
        Assert.hasText(fileName, "文件名不能为空");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("ID", "id");
        map.put("学生姓名", "name");
        map.put("学号", "userCode");
        map.put("班级号", "classid");
        map.put("语文成绩", "chineseGrade");
        map.put("数学成绩", "mathGrade");
        map.put("英语成绩", "englishGrade");
        map.put("录入时间", "recordtime");
        map.put("学年", "academicyear");
        map.put("学期", "term");
        AtomicInteger row = new AtomicInteger(0);
        XlsUtils.importFromExcel(inputStream, fileName, (gradeDTO) -> {
            addGrade(gradeDTO);
            row.incrementAndGet();
        }, map, GradeDTO.class);
        return row.get();
    }
}
