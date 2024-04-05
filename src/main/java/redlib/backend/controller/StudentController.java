package redlib.backend.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redlib.backend.annotation.BackendModule;
import redlib.backend.annotation.Privilege;
import redlib.backend.dto.StudentDTO;
import redlib.backend.dto.query.StudentQueryDTO;
import redlib.backend.model.Page;
import redlib.backend.service.StudentService;
import redlib.backend.vo.StudentVO;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 学生管理后端服务模块
 *
 * @author 王体硕
 * @description
 * @date 2024/3/11 17:00
 */
@RestController
@RequestMapping("/api/student")
@BackendModule({"page:页面", "update:修改", "add:创建", "delete:删除"})     //权限类型
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("listStudent")     //第29行的延伸，/api/student/listStudent路径
    @Privilege("page")              //有 'page' 权限才可以进行这个操作
    public Page<StudentVO> listStudent(@RequestBody StudentQueryDTO queryDTO) {
        return studentService.listByPage(queryDTO);
    }

    @PostMapping("addStudent")
    @Privilege("add")
    public Integer addStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.addStudent(studentDTO);
    }

    @PostMapping("updateStudent")
    @Privilege("update")
    public Integer updateStudent(@RequestBody StudentDTO studentDTO) {
        return studentService.updateStudent(studentDTO);
    }

    @GetMapping("getStudent")
    @Privilege("update")
    public StudentDTO getStudent(Integer id) {

        return studentService.getById(id);
    }

    @PostMapping("deleteStudent")
    @Privilege("delete")
    public void deleteStudent(@RequestBody List<Integer> ids) {
        studentService.deleteByCodes(ids);
    }

    @PostMapping("exportStudent")
    @Privilege("page")
    public void exportStudent(@RequestBody StudentQueryDTO queryDTO, HttpServletResponse response) throws Exception {
        Workbook workbook = studentService.export(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd$HHmmss");
        response.addHeader("Content-Disposition", "attachment;filename=file" + sdf.format(new Date()) + ".xls");
        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.close();
        workbook.close();
    }

    @PostMapping("importStudent")
    @Privilege("add")
    public int importUsers(@RequestParam("file") MultipartFile file) throws Exception {
        return studentService.importStudent(file.getInputStream(), file.getOriginalFilename());
    }

    @GetMapping("listClassid")
    @Privilege("page")
    public List<Integer> listClassid() {
        return studentService.listClassid();
    }

    @GetMapping("studentNum")
    @Privilege("page")
    public Integer studentNum(@RequestParam("number") Integer stuClass) {
        return studentService.studentNum(stuClass);
    }
}
