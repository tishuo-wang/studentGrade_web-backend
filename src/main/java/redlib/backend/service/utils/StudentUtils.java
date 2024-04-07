package redlib.backend.service.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import redlib.backend.dto.StudentDTO;
import redlib.backend.model.Student;
import redlib.backend.utils.FormatUtils;
import redlib.backend.vo.StudentVO;

import java.util.Map;


/**
 * @author 王体硕
 * @description
 * @date 2024/3/11 18:10
 */
public class StudentUtils {
    /**
     * 规范并校验studentDTO
     *
     * @param studentDTO
     */
    public static void validateStudent(StudentDTO studentDTO) {
        FormatUtils.trimFieldToNull(studentDTO);
        Assert.notNull(studentDTO, "学生输入数据不能为空");
        Assert.hasText(studentDTO.getStudentname(), "学生名称不能为空");

    }

    /**
     * 将实体对象转换为VO对象
     *
     * @param student 实体对象
     * @param nameMap
     * @return VO对象
     */
    public static StudentVO convertToVO(Student student, Map<Integer, String> nameMap) {
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(student, studentVO);
        return studentVO;
    }
}
