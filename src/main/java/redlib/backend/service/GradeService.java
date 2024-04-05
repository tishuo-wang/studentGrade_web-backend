package redlib.backend.service;

import org.apache.poi.ss.usermodel.Workbook;
import redlib.backend.dto.GradeDTO;
import redlib.backend.dto.query.GradeAverageQueryDTO;
import redlib.backend.dto.query.GradeQueryDTO;
import redlib.backend.model.Page;
import redlib.backend.vo.GradeAverVO;
import redlib.backend.vo.GradeVO;
import redlib.backend.vo.ModuleVO;

import java.io.InputStream;
import java.util.List;


public interface GradeService {


    Page<GradeVO> listByPage(GradeQueryDTO queryDTO);

    /**
     * 新建
     *
     * @param gradeDTO 成绩输入对象
     * @return 成绩编码
     */
    Integer addGrade(GradeDTO gradeDTO);

    GradeDTO getById(Integer id);

    /**
     * 更新成绩数据
     *
     * @param gradeDTO 成绩输入对象
     * @return 成绩编码
     */
    Integer updateGrade(GradeDTO gradeDTO);

    /**
     * 根据编码列表，批量删除
     *
     * @param ids 编码列表
     */
    void deleteByCodes(List<Integer> ids);

    Workbook export(GradeQueryDTO queryDTO);

    int importGrade(
            InputStream inputStream,
            String fileName) throws Exception;

    Page<GradeAverVO> listAverageGrade(GradeAverageQueryDTO queryDTO);
}
