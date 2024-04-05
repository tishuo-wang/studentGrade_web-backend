package redlib.backend.vo;
import lombok.Data;
import java.util.Date;

@Data
public class StudentVO {
    private Integer id;

    private String studentname;

    private String userCode;

    private Integer sex;

    private String parentname;

    private String phone;

    private Integer classid;

    /**
     * ¥¥Ω®»À
     */
    private Integer createdBy;

}
