package redlib.backend.dto.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ÍõÌåË¶
 * @description
 * @date 2024/3/10 20:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClassesQueryDTO extends PageQueryDTO{
    /**
     * Ä£ºıÆ¥Åä
     */
    private String classid;
    private String teachername;
    private String classname;
}
