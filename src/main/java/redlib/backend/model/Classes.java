package redlib.backend.model;

import java.util.Date;
import lombok.Data;


/**
 * ����:classes���ʵ����
 * @version
 * @author:  shuo
 * @����ʱ��: 2024-03-11
 */
@Data
public class Classes {
    /**
     * ID
     */
    private Integer id;

    /**
     * �༶ID
     */
    private String classid;

    /**
     * �༶����
     */
    private String classname;

    /**
     * ����������
     */
    private String teachername;

    /**
     * �����ε绰
     */
    private String teacherphone;

    /**
     * �༶λ��(��һ¥105)
     */
    private String position;

    /**
     * �༶����
     */
    private String number;

    /**
     * ����ʱ��
     */
    private Date createdAt;

    /**
     * ����ʱ��
     */
    private Date updatedAt;
}