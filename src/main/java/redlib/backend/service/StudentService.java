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
     * �½�ѧ��
     *
     * @param studentDTO ѧ���������
     * @return ѧ������
     */
    Integer addStudent(StudentDTO studentDTO);

    StudentDTO getById(Integer id);

    /**
     * ����ѧ������
     *
     * @param studentDTO ѧ���������
     * @return ѧ������
     */
    Integer updateStudent(StudentDTO studentDTO);

    /**
     * ���ݱ����б�����ɾ��ѧ��
     *
     * @param ids �����б�
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
