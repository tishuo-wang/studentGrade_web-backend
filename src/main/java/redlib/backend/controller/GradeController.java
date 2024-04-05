package redlib.backend.controller;


import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redlib.backend.annotation.BackendModule;
import redlib.backend.annotation.Privilege;
import redlib.backend.dto.GradeDTO;
import redlib.backend.dto.query.GradeAverageQueryDTO;
import redlib.backend.dto.query.GradeQueryDTO;
import redlib.backend.model.Page;
import redlib.backend.service.GradeService;
import redlib.backend.vo.GradeAverVO;
import redlib.backend.vo.GradeVO;
import redlib.backend.vo.ModuleVO;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 学生管理后端服务模块
 *
 * @author 王体硕
 * @description
 * @date 2024/3/14 21:30
 */
@RestController
@RequestMapping("/api/grade")
@BackendModule({"page:页面", "update:修改", "add:创建", "delete:删除"})     //权限类型
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @PostMapping("listGrade")
    @Privilege("page")
    public Page<GradeVO> listGrade(@RequestBody GradeQueryDTO queryDTO) {
        return gradeService.listByPage(queryDTO);
    }

    @PostMapping("addGrade")
    @Privilege("add")
    public Integer addGrade(@RequestBody GradeDTO gradeDTO) {

        return gradeService.addGrade(gradeDTO);
    }

    @PostMapping("updateGrade")
    @Privilege("update")
    public Integer updateGrade(@RequestBody GradeDTO gradeDTO) {
        return gradeService.updateGrade(gradeDTO);
    }

    @GetMapping("getGrade")
    @Privilege("page")
    public GradeDTO getGrade(Integer id) {

        return gradeService.getById(id);
    }

    @PostMapping("listAverageGrade")
    @Privilege("page")
    public Page<GradeAverVO> listAverageGrade(@RequestBody GradeAverageQueryDTO queryDTO) {
        return gradeService.listAverageGrade(queryDTO);
    }


    @PostMapping("deleteGrade")
    @Privilege("delete")
    public void deleteGrade(@RequestBody List<Integer> ids) {
        gradeService.deleteByCodes(ids);
    }

    @PostMapping("exportGrade")
    @Privilege("page")
    public void exportGrade(@RequestBody GradeQueryDTO queryDTO, HttpServletResponse response) throws Exception {
        Workbook workbook = gradeService.export(queryDTO);
        response.setContentType("application/vnd.ms-excel");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd$HHmmss");
        response.addHeader("Content-Disposition", "attachment;filename=file" + sdf.format(new Date()) + ".xls");
        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.close();
        workbook.close();
    }

    @PostMapping("importGrade")
    @Privilege("add")
    public int importGrade(@RequestParam("file") MultipartFile file) throws Exception {
        return gradeService.importGrade(file.getInputStream(), file.getOriginalFilename());
    }
}
