package redlib.backend.model;

import lombok.Data;

/**
 * 描述:grade表的实体类
 * @version
 * @author:  shuo
 * @创建时间: 2024-03-13
 */
@Data
public class Grade {
    /**
     * ID
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 学号
     */
    private String userCode;

    /**
     * 班级ID
     */
    private String classid;

    /**
     * 语文成绩
     */
    private Float chineseGrade;

    /**
     * 数学成绩
     */
    private Float mathGrade;

    /**
     * 英语成绩
     */
    private Float englishGrade;

    /**
     * 录入时间(如2024-3-10)
     */
    private String recordtime;

    /**
     * 学年
     */
    private String academicyear;

    /**
     * 学期
     */
    private String term;

    private Float averageChinese;
    private Float averageMath;
    private Float averageEnglish;
    private Float total;
}