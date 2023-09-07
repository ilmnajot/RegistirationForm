package uz.ilmnajot.registirationform.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ilmnajot.registirationform.entity.Student;
import uz.ilmnajot.registirationform.model.common.ApiResponse;
import uz.ilmnajot.registirationform.model.request.StudentForm;
import uz.ilmnajot.registirationform.model.response.StudentDto;
import uz.ilmnajot.registirationform.repository.StudentRepository;

import java.util.Optional;

@Service
public class StudentService implements BaseService<StudentForm, Long> {

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ApiResponse getById(Long id) {
        return studentRepository.findById(id).isPresent()
                ? new ApiResponse("found user", true, studentRepository.findById(id).get())
                : new ApiResponse("not found", false, null);

    }

    @Override
    public ApiResponse getToken(StudentForm studentForm) {
        return null;
    }

    @Override
    public ApiResponse deleteById(Long aLong) {
        return null;
    }

    @Override
    public ApiResponse update(StudentForm studentForm, Long aLong) {
        return null;
    }

    @Override
    public ApiResponse save(StudentForm studentForm) {
        Optional<Student> optionalUser = studentRepository.findUserByEmail(studentForm.getEmail());
        Student savedStudent = studentRepository.save(new Student(
                studentForm.getFirstName(),
                studentForm.getLastName(),
                studentForm.getEmail(),
                studentForm.getAge(),
                passwordEncoder.encode(studentForm.getPassword())));
        modelMapper.createTypeMap(savedStudent, StudentDto.class)
                .addMappings(mapper -> mapper.map(src -> savedStudent.getFirstName() + " " + savedStudent.getLastName(), StudentDto::setFullName));
        StudentDto studentDto = modelMapper.map(savedStudent, StudentDto.class);

        return optionalUser.isPresent()
                ? new ApiResponse("sorry, student with email: " + studentForm.getEmail() + " already exits, please try with another email", false, null)
                : new ApiResponse("saved successfully", true, studentDto);

    }

}
