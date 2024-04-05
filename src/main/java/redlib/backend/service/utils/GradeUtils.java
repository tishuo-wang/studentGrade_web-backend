package redlib.backend.service.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import redlib.backend.dto.GradeDTO;
import redlib.backend.model.Grade;
import redlib.backend.utils.FormatUtils;
import redlib.backend.vo.GradeVO;

public class GradeUtils {

    /**
     * 规范并校验gradeDTO
     *
     * @param gradeDTO
     */
    public static void validateGrade(GradeDTO gradeDTO) {
        FormatUtils.trimFieldToNull(gradeDTO);
        Assert.notNull(gradeDTO, "输入数据不能为空");
        Assert.hasText(gradeDTO.getName(), "学生名称不能为空");
    }

    /**
     * 将实体对象转换为VO对象
     *
     * @param grade 实体对象
     *
     * @return VO对象
     */
    public static GradeVO convertToVO(Grade grade) {
        GradeVO gradeVO = new GradeVO();
        BeanUtils.copyProperties(grade, gradeVO);
        return gradeVO;
    }
}
