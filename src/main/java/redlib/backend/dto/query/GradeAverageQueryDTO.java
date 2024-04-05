package redlib.backend.dto.query;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 王体硕
 * @description
 * @date 2024/3/15 15:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GradeAverageQueryDTO extends PageQueryDTO{

    private String academicyear;

    private String term;
}
