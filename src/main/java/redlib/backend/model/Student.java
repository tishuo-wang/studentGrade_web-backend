package redlib.backend.model;

import lombok.Data;

/**
 * ����:student���ʵ����
 * @version
 * @author:  shuo
 * @����ʱ��: 2024-03-12
 */
@Data
public class Student {
    /**
     * ID
     */
    private Integer id;

    /**
     * ����
     */
    private String studentname;

    /**
     * ѧ��
     */
    private String userCode;

    /**
     * �Ա�(1Ϊ�У�2ΪŮ)
     */
    private Integer sex;

    /**
     * �ҳ�����
     */
    private String parentname;

    /**
     * �ҳ��绰
     */
    private String phone;

    /**
     * �༶ID
     */
    private Integer classid;

    /**
     * ������
     */
    private Integer createdBy;

    /**
     * ������
     */
    private Integer updatedBy;
}