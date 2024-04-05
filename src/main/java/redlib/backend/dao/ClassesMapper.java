package redlib.backend.dao;

import org.apache.ibatis.annotations.Param;
import redlib.backend.dto.query.ClassesQueryDTO;
import redlib.backend.model.Classes;
import java.util.List;


public interface ClassesMapper {

    Classes selectByPrimaryKey(Integer id);

    int insert(Classes record);

    int updateByPrimaryKey(Classes record);

    Integer count(ClassesQueryDTO queryDTO);

    List<Classes> list(@Param("queryDTO") ClassesQueryDTO queryDTO, @Param("offset") Integer offset, @Param("limit") Integer limit);

    void deleteByCodes(@Param("codeList") List<Integer> codeList);

    List<Classes> listByIds(@Param("ids") List<Integer> ids);

}