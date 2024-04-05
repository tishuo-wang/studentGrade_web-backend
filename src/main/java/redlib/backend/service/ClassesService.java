package redlib.backend.service;

import org.apache.poi.ss.usermodel.Workbook;
import redlib.backend.dto.ClassesDTO;
import redlib.backend.dto.StudentDTO;
import redlib.backend.dto.query.ClassesQueryDTO;
import redlib.backend.dto.query.StudentQueryDTO;
import redlib.backend.model.Page;
import redlib.backend.vo.ClassesVO;
import redlib.backend.vo.StudentVO;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ClassesService {
    Page<ClassesVO> listByPage(ClassesQueryDTO queryDTO);
    /**
     * 新建
     *
     * @param classesDTO 班级输入对象
     * @return 班级编码
     */
    Integer addClasses(ClassesDTO classesDTO);
    ClassesDTO getById(Integer id);
    /**
     * 更新班级数据
     *
     * @param classesDTO 班级输入对象
     * @return 班级编码
     */
    Integer updateClasses(ClassesDTO classesDTO);

    /**
     * 根据编码列表，批量删除班级
     *
     * @param ids 编码列表
     */
    void deleteByCodes(List<Integer> ids);

    Workbook export(ClassesQueryDTO queryDTO);

    int importClasses(
            InputStream inputStream,
            String fileName) throws Exception;

}
