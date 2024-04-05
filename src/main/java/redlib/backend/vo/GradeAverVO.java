package redlib.backend.vo;


import lombok.Data;

@Data
public class GradeAverVO {

    private String classid;

    private Float averageChinese;

    private Float averageMath;

    private Float averageEnglish;

    private Float total;
}
