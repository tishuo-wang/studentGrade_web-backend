package redlib.backend.model;

import java.util.Date;
import lombok.Data;


/**
 * 描述:classes表的实体类
 * @version
 * @author:  shuo
 * @创建时间: 2024-03-11
 */
@Data
public class Classes {
    /**
     * ID
     */
    private Integer id;

    /**
     * 班级ID
     */
    private String classid;

    /**
     * 班级名称
     */
    private String classname;

    /**
     * 班主任名称
     */
    private String teachername;

    /**
     * 班主任电话
     */
    private String teacherphone;

    /**
     * 班级位置(如一楼105)
     */
    private String position;

    /**
     * 班级人数
     */
    private String number;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;
}