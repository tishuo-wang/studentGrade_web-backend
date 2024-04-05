package redlib.backend.model;

import lombok.Data;

/**
 * 描述:student表的实体类
 * @version
 * @author:  shuo
 * @创建时间: 2024-03-12
 */
@Data
public class Student {
    /**
     * ID
     */
    private Integer id;

    /**
     * 姓名
     */
    private String studentname;

    /**
     * 学号
     */
    private String userCode;

    /**
     * 性别(1为男，2为女)
     */
    private Integer sex;

    /**
     * 家长姓名
     */
    private String parentname;

    /**
     * 家长电话
     */
    private String phone;

    /**
     * 班级ID
     */
    private Integer classid;

    /**
     * 创建人
     */
    private Integer createdBy;

    /**
     * 更新人
     */
    private Integer updatedBy;
}