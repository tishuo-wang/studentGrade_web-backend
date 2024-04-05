package redlib.backend.dto.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ����˶
 * @description
 * @date 2024/3/10 20:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClassesQueryDTO extends PageQueryDTO{
    /**
     * ģ��ƥ��
     */
    private String classid;
    private String teachername;
    private String classname;
}
