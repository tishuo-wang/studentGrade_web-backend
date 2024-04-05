package redlib.backend.dto;

import lombok.Data;

/**
 * @author 王体硕
 * @description
 * @date 2024/3/10 21:00
 */
@Data
public class StudentDTO {
    private Integer id;

    private String studentname;

    private String userCode;

    private Integer sex;

    private String parentname;

    private String phone;

    private Integer classid;
}
