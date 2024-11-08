package redlib.backend.service.utils;


import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import redlib.backend.dto.ClassesDTO;
import redlib.backend.model.Classes;
import redlib.backend.utils.FormatUtils;
import redlib.backend.vo.ClassesVO;

import java.util.Map;

/**
 * @author 王体硕
 * @description
 * @date 2024/3/13 23:00
 */
public class ClassesUtils {
    /**
     * 规范并校验classesDTO
     *
     * @param classesDTO
     */
    public static void validateClasses(ClassesDTO classesDTO) {
        FormatUtils.trimFieldToNull(classesDTO);
        Assert.notNull(classesDTO, "输入数据不能为空");
        Assert.hasText(classesDTO.getClassname(), "班级名称不能为空");
    }

    /**
     * 将实体对象转换为VO对象
     *
     * @param classes 实体对象
     *
     * @return VO对象
     */
    public static ClassesVO convertToVO(Classes classes, Integer studentNumber) {
        ClassesVO classesVO = new ClassesVO();
        BeanUtils.copyProperties(classes, classesVO);
        classesVO.setStudentNumber(studentNumber);
        return classesVO;
    }
}
