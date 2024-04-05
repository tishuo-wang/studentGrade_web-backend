package redlib.backend.dto.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 王体硕
 * @description
 * @date 2024/3/10 20:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentQueryDTO extends PageQueryDTO {
    /**
     * 模糊匹配
     */
    private String studentname;
    private String userCode;

    //准确搜索
    private Integer classid;
}