package redlib.backend.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redlib.backend.annotation.BackendModule;
import redlib.backend.annotation.Privilege;
import redlib.backend.dto.ClassesDTO;
import redlib.backend.dto.query.ClassesQueryDTO;
import redlib.backend.model.Page;
import redlib.backend.service.ClassesService;
import redlib.backend.vo.ClassesVO;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 学生管理后端服务模块
 *
 * @author 王体硕
 * @description
 * @date 2024/3/14 9:00
 */
@RestController
@RequestMapping("/api/classes")
@BackendModule({"page:页面", "update:修改", "add:创建", "delete:删除"})     //权限类型
public class ClassesController {
    @Autowired
    private ClassesService classesService;

    @PostMapping("listClasses")
    @Privilege("page")
    public Page<ClassesVO> listClasses(@RequestBody ClassesQueryDTO queryDTO) {
        return classesService.listByPage(queryDTO);
    }

    @PostMapping("addClasses")
    @Privilege("add")
    public Integer addClasses(@RequestBody ClassesDTO classesDTO) {

        return classesService.addClasses(classesDTO);
    }

    @PostMapping("updateClasses")
    @Privilege("update")
    public Integer updateClasses(@RequestBody ClassesDTO classesDTO) {
        return classesService.updateClasses(classesDTO);
    }

    @GetMapping("getClasses")
    @Privilege("update")
    public ClassesDTO getClasses(Integer id) {

        return classesService.getById(id);
    }

    @PostMapping("deleteClasses")
    @Privilege("delete")
    public void deleteClasses(@RequestBody List<Integer> ids) {
        classesService.deleteByCodes(ids);
    }

    @PostMapping("exportClasses")
    @Privilege("page")
    public void exportClasses(@RequestBody ClassesQueryDTO queryDTO, HttpServletResponse response) throws Exception {
        Workbook workbook = classesService.export(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd$HHmmss");
        response.addHeader("Content-Disposition", "attachment;filename=file" + sdf.format(new Date()) + ".xls");
        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.close();
        workbook.close();
    }

    @PostMapping("importClasses")
    @Privilege("add")
    public int importClasses(@RequestParam("file") MultipartFile file) throws Exception {
        return classesService.importClasses(file.getInputStream(), file.getOriginalFilename());
    }

}
