package uz.ilmnajot.registirationform.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentForm {

    private String firstName;

    private String lastName;

    private String email;

    private int age;

    private String password;
}
