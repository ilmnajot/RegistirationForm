package uz.ilmnajot.registirationform.model.response;

import lombok.Getter;
import lombok.Setter;
import uz.ilmnajot.registirationform.enums.RoleName;

@Setter
@Getter
public class RegisterDto {


    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private int age;

    private RoleName roleName;

    //
//        return user;
//        User user = modelMapper.map(registerDto, User.class);
//    public User convertUserDtoToUser(RegisterDto registerDto){
//    }
//        return registerDto;
//        RegisterDto registerDto = modelMapper.map(user, RegisterDto.class);
//    public RegisterDto convertUserToUserDto(User user){
//    private final ModelMapper modelMapper;
//
//    public RegisterDto(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }


//    }
   /* public static RegisterDto userToUserDto(User user){
     RegisterDto registerDto = new RegisterDto();
        registerDto.setId(user.getId());
        registerDto.setFirstName(user.getFirstName());
        registerDto.setLastName(user.getLastName());
        registerDto.setEmail(user.getEmail());
        registerDto.setAge(user.getAge());
        return registerDto;
    }*/

}
