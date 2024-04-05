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
     * �½�
     *
     * @param classesDTO �༶�������
     * @return �༶����
     */
    Integer addClasses(ClassesDTO classesDTO);
    ClassesDTO getById(Integer id);
    /**
     * ���°༶����
     *
     * @param classesDTO �༶�������
     * @return �༶����
     */
    Integer updateClasses(ClassesDTO classesDTO);

    /**
     * ���ݱ����б�����ɾ���༶
     *
     * @param ids �����б�
     */
    void deleteByCodes(List<Integer> ids);

    Workbook export(ClassesQueryDTO queryDTO);

    int importClasses(
            InputStream inputStream,
            String fileName) throws Exception;

}
