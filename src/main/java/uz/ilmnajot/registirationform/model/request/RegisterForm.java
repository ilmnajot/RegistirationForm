package uz.ilmnajot.registirationform.model.request;

import lombok.Getter;
import lombok.Setter;
import uz.ilmnajot.registirationform.enums.RoleName;

@Setter
@Getter
public class RegisterForm {

    private String firstName;

    private String lastName;

    private String email;

    private int age;

    private String password;

    private RoleName roleName;


}
