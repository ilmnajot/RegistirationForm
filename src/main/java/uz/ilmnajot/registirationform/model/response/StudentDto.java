package uz.ilmnajot.registirationform.model.response;

import lombok.Data;

@Data
public class StudentDto {

    private Long id;

    private String fullName;

    private String email;

    private int age;

}
