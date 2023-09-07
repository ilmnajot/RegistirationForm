package uz.ilmnajot.registirationform.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.registirationform.model.common.ApiResponse;
import uz.ilmnajot.registirationform.model.request.RegisterForm;
import uz.ilmnajot.registirationform.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public HttpEntity<ApiResponse> registerUser(@RequestBody RegisterForm form) {
        ApiResponse apiResponse = userService.save(form);
        return apiResponse != null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping("/login")
    public HttpEntity<ApiResponse> getToken(@RequestBody RegisterForm form) {
        ApiResponse userServiceToken = userService.getToken(form);
        return userServiceToken != null
                ? ResponseEntity.ok(userServiceToken)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PutMapping("/verifyEmail")
    public HttpEntity<ApiResponse> verifyEmail(@RequestParam String email, @RequestParam String emailCode){
        ApiResponse apiResponse = userService.verifyEmail(email, emailCode);
        return apiResponse!=null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @GetMapping("/{id}")
    public HttpEntity<ApiResponse> getById(@PathVariable Long id) {
        ApiResponse apiResponse = userService.getById(id);
        return apiResponse != null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    @DeleteMapping("/{id}")
    public HttpEntity<ApiResponse> deleteById(@PathVariable Long id) {
        ApiResponse apiResponse = userService.deleteById(id);
        return apiResponse != null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public HttpEntity<ApiResponse> updateById(@RequestBody RegisterForm form, @PathVariable Long id) {
        ApiResponse updated = userService.update(form, id);
        return updated != null
                ? ResponseEntity.ok(updated)
                : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/all")
    public HttpEntity<ApiResponse> getAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {
        ApiResponse userServiceAll = userService.getAll(page, size);
        return userServiceAll != null
                ? ResponseEntity.ok(userServiceAll)
                : ResponseEntity.badRequest().build();
    }
}
