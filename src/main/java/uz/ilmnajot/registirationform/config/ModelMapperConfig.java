package uz.ilmnajot.registirationform.config;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.ilmnajot.registirationform.entity.Student;
import uz.ilmnajot.registirationform.entity.User;
import uz.ilmnajot.registirationform.model.request.StudentForm;
import uz.ilmnajot.registirationform.model.response.StudentDto;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
