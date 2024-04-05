package redlib.backend.service;

import org.apache.poi.ss.usermodel.Workbook;
import redlib.backend.dto.StudentDTO;
import redlib.backend.dto.query.StudentQueryDTO;
import redlib.backend.model.Page;
import redlib.backend.vo.StudentVO;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StudentService {
    Page<StudentVO> listByPage(StudentQueryDTO queryDTO);

    /**
     * 新建学生
     *
     * @param studentDTO 学生输入对象
     * @return 学生编码
     */
    Integer addStudent(StudentDTO studentDTO);

    StudentDTO getById(Integer id);

    /**
     * 更新学生数据
     *
     * @param studentDTO 学生输入对象
     * @return 学生编码
     */
    Integer updateStudent(StudentDTO studentDTO);

    /**
     * 根据编码列表，批量删除学生
     *
     * @param ids 编码列表
     */
    void deleteByCodes(List<Integer> ids);

    Workbook export(StudentQueryDTO queryDTO);

    Map<Integer, String> getNameMap(Set<Integer> adminIds);

    int importStudent(
            InputStream inputStream,
            String fileName) throws Exception;

    List<Integer> listClassid();

    Integer studentNum(Integer stuClass);

}
