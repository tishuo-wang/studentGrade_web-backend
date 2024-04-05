package redlib.backend.vo;

import lombok.Data;

@Data
public class GradeVO {
    private Integer id;

    private String name;

    private String userCode;

    private String classid;

    private Float chineseGrade;

    private Float mathGrade;

    private Float englishGrade;

    private String recordtime;

    private String academicyear;

    private String term;

    /**
     * ¥¥Ω®»À
     */
    private Integer createdBy;
}
