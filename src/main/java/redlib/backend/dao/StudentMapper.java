package redlib.backend.dao;

import org.apache.ibatis.annotations.Param;
import redlib.backend.dto.query.StudentQueryDTO;
import redlib.backend.model.Admin;
import redlib.backend.model.Student;

import java.util.List;



public interface StudentMapper {
    Student selectByPrimaryKey(Integer id);

    int insert(Student record);

    int updateByPrimaryKey(Student record);

    Integer count(StudentQueryDTO queryDTO);

    List<Student> list(@Param("queryDTO") StudentQueryDTO queryDTO, @Param("offset") Integer offset, @Param("limit") Integer limit);

    void deleteByCodes(@Param("codeList") List<Integer> codeList);

    List<Student> listByIds(@Param("ids") List<Integer> ids);

    List<Student> listClassId();

    Integer count_studentNum(Integer stuClass);

    Student getByUserCode(String userCode);
}