package redlib.backend.vo;
import lombok.Data;
import java.util.Date;

@Data
public class ClassesVO {
    private Integer id;

    private String classid;

    private String classname;

    private String teachername;

    private String teacherphone;

    private String position;

    private String number;

    private Integer studentNumber;

    /**
     * ¥¥Ω®»À
     */
    private Integer createdBy;
}
