package redlib.backend.dto.query;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 王体硕
 * @description
 * @date 2024/3/14 9:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GradeQueryDTO extends PageQueryDTO{

    private String userCode;
    private String name;
    private String classid;
    private String term;

}
