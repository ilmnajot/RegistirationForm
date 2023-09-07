package uz.ilmnajot.registirationform.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ilmnajot.registirationform.model.common.ApiResponse;
import uz.ilmnajot.registirationform.model.request.StudentForm;
import uz.ilmnajot.registirationform.model.response.StudentDto;
import uz.ilmnajot.registirationform.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController( StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public HttpEntity<ApiResponse> registerStudent(@RequestBody StudentForm form) {
        ApiResponse apiResponse = studentService.save(form);
        return apiResponse != null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
