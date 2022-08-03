package com.globits.da.rest;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.ResponseRequest;
import com.globits.da.file.ExcelGenerator;
import com.globits.da.file.testReadFile;
import com.globits.da.service.EmployeeService;
import com.globits.da.validate.ValidateEmployee;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ModelMapper modelMapper;
    private Boolean isCheckUpdateSuccess = false;
    private final Path root = Paths.get("E:\\BAI_TAP_LUYEN_TAP\\Thuc Tap Java OcenTech\\Bai Tap Lv 2\\L2_Backend\\uploads");

    @GetMapping("/list")
    public ResponseEntity<ResponseRequest> getAllEmployee() {
        List<EmployeeDTO> listEmployeeDto = employeeService.getAllEmployee()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
        return listEmployeeDto.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseRequest(404, "List Employee Is Empty", listEmployeeDto)) :
                ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseRequest(200, "Perform Query Success", listEmployeeDto));
    }

    //    // Đang Lỗi
//    @GetMapping("/find-employee")
//    public List<EmployeeSearchDTO> getFindAllByKeyword(@RequestParam("keyword") String keyword) {
//        List<EmployeeSearchDTO> listEmployeeDto = employeeService.findByAllKeyword(keyword)
//                .stream()
//                .map(employee -> modelMapper.map(employee, EmployeeSearchDTO.class))
//                .collect(Collectors.toList());
//        if (listEmployeeDto.isEmpty()) {
//            logger.info("Danh Sách Trống");
//        }
//        return new ResponseEntity<>(listEmployeeDto, HttpStatus.OK);
//
//        //eturn listEmployeeDto;
//
    //   }
    @PostMapping("/add")
    public ResponseEntity<ResponseRequest> insertEmployee(@RequestBody(required = true) EmployeeDTO employeeDTO) {
        return ValidateEmployee.checkEmployeeIsEmpty(employeeDTO) ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseRequest(200
                                , "Insert Database Success"
                                , employeeService.insertEmployee(employeeDTO))) :
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                        .body(new ResponseRequest(501
                                , "Insert Database not Success", employeeDTO));
    }

    // Cách 1(add employee) trả về mã trạng thái
   /* @PutMapping("/api/employee/update/{id}")
    public ResponseEntity<?> insertEmployee(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.findEmployeeById(id)
                .map(employee -> {
                    employee.setCode(employeeDTO.getCode());
                    employee.setName(employeeDTO.getName());
                    employee.setEmail(employeeDTO.getEmail());
                    employee.setPhone(employeeDTO.getPhone());
                    employee.setAge(employeeDTO.getAge());
                    isCheckSuccess = true;
                    return new ResponseEntity<>(employeeService.updateEmployee(employee), HttpStatus.OK);
                }).orElseGet(() -> {
                    return new ResponseEntity<>(employeeDTO, HttpStatus.NOT_FOUND);
                });
    }*/

    //Cách 2 Trả về Messseger kèm data
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseRequest> insertEmployee(@PathVariable("id") Long id, @RequestBody EmployeeDTO employeeDTO) {
        employeeService.findEmployeeById(id)
                .map(employee -> {
                    employee.setCode(employeeDTO.getCode());
                    employee.setName(employeeDTO.getName());
                    employee.setEmail(employeeDTO.getEmail());
                    employee.setPhone(employeeDTO.getPhone());
                    employee.setAge(employeeDTO.getAge());
                    isCheckUpdateSuccess = true;
                    return employeeService.updateEmployee(employee);
                });
        if (isCheckUpdateSuccess) {
            employeeDTO.setId(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseRequest(200, "Update Employee Success", employeeDTO));
        } else {
            employeeDTO.setId(id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseRequest(404, "Can Not Find Employee Have Id: " + id, employeeDTO));
        }
    }

    //cách 1(xóa nhân viên) trả về data và Messeger
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseRequest> deleteEmployeeById(@PathVariable("id") Long id) {
        Optional<EmployeeDTO> employeeDTO = employeeService.findEmployeeById(id)
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class));
        if (employeeDTO.isPresent()) {
            employeeService.deleteEmployeeById(id);
            logger.info("Delete  Employee Have Id: " + id);
            return ResponseEntity.
                    status(HttpStatus.OK)
                    .body(new ResponseRequest(200, "Deleted Employee Have Id: " + id, employeeDTO));
        } else {
            logger.info("Không Tìm Thấy Employee Có Id: " + id);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseRequest(404, "Can Not Find Employee Have Id: " + id, employeeDTO));
        }
    }
    //cách 2(xóa nhân viên) trả về mã trạng thái lỗi
    //    @DeleteMapping("/list-employee/{id}")
//    public ResponseEntity<?> deleteEmployeeById(@PathVariable("id") Long id) {
//        Optional<EmployeeDTO> employeeDTO = employeeService.findEmployeeById(id)
//                .map(employee -> modelMapper.map(employee, EmployeeDTO.class));
//        if (employeeDTO.isPresent()) {
//            logger.info("Không Tìm Thấy Employee Có Id: " + id);
//        }
//        return employeeDTO.map(employee -> {
//            employeeService.deleteEmployeeById(id);
//            return new ResponseEntity<>(employee, HttpStatus.OK);
//        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }


    //   @GetMapping("/list") lấy list nhân viên
    //  public List<EmployeeDTO> list() {
    //        List<EmployeeDTO> listEmployeeDto = employeeService.getAllEmployee()
//                .stream()
//                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
//                .collect(Collectors.toList());
//        if (listEmployeeDto.isEmpty()) {
//            logger.info("Danh Sách Nhân Viên Rỗng");
//        }
//        return listEmployeeDto;
//}


    @GetMapping("/export-to-excel")
    public ResponseEntity<?> exportIntoExcelFile(HttpServletResponse response) throws IOException, IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=employee" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<EmployeeDTO> listOfStudents = employeeService.getAllEmployee();
        ExcelGenerator generator = new ExcelGenerator(listOfStudents);
        generator.exportExcelFile(response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseRequest(200, "Export Success", listOfStudents));

    }

    //@GetMapping("/read-file-excel")
    @PostMapping("/read-file-excel")
    public ResponseEntity<List<EmployeeDTO>> getAllTutorials(@RequestParam("file") MultipartFile file) {
        try {
            Path filepath = Paths.get(root.toString(), file.getOriginalFilename());
            OutputStream os = Files.newOutputStream(filepath);
            os.write(file.getBytes());
            List<EmployeeDTO> tutorials = testReadFile.excelToTutorials(filepath.toString());
            tutorials.forEach(x -> {
                System.out.println(x.getName());
            });
            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                employeeService.saveAll(tutorials);
                return new ResponseEntity<>(tutorials, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-list-employee-page")
    public Page<Employee> getListOfEmployeesByPage(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "2") Integer size) {
        Pageable pageable = PageRequest.of(page, size, new Sort(Sort.Direction.ASC, "id"));
        return employeeService.getPage(pageable);
    }
}