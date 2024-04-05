package redlib.backend.dao;

import org.apache.ibatis.annotations.Param;
import redlib.backend.dto.query.ClassesQueryDTO;
import redlib.backend.dto.query.GradeAverageQueryDTO;
import redlib.backend.dto.query.GradeQueryDTO;
import redlib.backend.model.Classes;
import redlib.backend.model.Grade;
import redlib.backend.model.Student;
import redlib.backend.vo.GradeAverVO;

import java.util.List;

public interface GradeMapper {
    Grade selectByPrimaryKey(Integer id);

    int insert(Grade record);

    int updateByPrimaryKey(Grade record);

    Integer count(GradeQueryDTO queryDTO);

    List<Grade> list(@Param("queryDTO") GradeQueryDTO queryDTO, @Param("offset") Integer offset, @Param("limit") Integer limit);

    void deleteByCodes(@Param("codeList") List<Integer> codeList);

    List<Grade> count_aver(GradeAverageQueryDTO queryDTO);

}