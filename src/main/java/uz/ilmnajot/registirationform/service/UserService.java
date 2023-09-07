package uz.ilmnajot.registirationform.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.ilmnajot.registirationform.config.jwt.JwtProvider;
import uz.ilmnajot.registirationform.enums.RoleName;
import uz.ilmnajot.registirationform.model.common.ApiResponse;
import uz.ilmnajot.registirationform.model.response.RegisterDto;
import uz.ilmnajot.registirationform.model.request.RegisterForm;
import uz.ilmnajot.registirationform.entity.User;
import uz.ilmnajot.registirationform.model.response.TokenForm;
import uz.ilmnajot.registirationform.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements BaseService<RegisterForm, Long> {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final JavaMailSender javaMailSender;
    private final MailService mailService;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtProvider jwtProvider, JavaMailSender javaMailSender, MailService mailService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.javaMailSender = javaMailSender;
        this.mailService = mailService;
    }

    @Override
    public ApiResponse save(RegisterForm form) {
        Optional<User> optionalUser = userRepository.findUserByEmail(form.getEmail());
        if (optionalUser.isEmpty()) {
            User user = new User();
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            user.setEmail(form.getEmail());
            user.setAge(form.getAge());
            user.setPassword(passwordEncoder.encode(form.getPassword()));
            user.setRoleName(RoleName.USER);

            int nextedInt = new Random().nextInt(9999999);
            user.setEmailCode(String.valueOf(nextedInt).substring(0, 4));
            User saved = userRepository.save(user);
            mailService.sendMail(user.getUsername(), user.getEmailCode());
            return new ApiResponse("successfully user saved, email code is sent to your email", true, modelMapper.map(saved, RegisterDto.class));
        } else {
            return new ApiResponse("user is already registered", false);

        }

    }

    @Override
    public ApiResponse getById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        return optionalUser.isPresent()
                ? new ApiResponse("success", true, modelMapper.map(optionalUser, RegisterDto.class))
                : new ApiResponse("not found user with id " + id, false, null);


    }

    @Override
    public ApiResponse getToken(RegisterForm registerForm) {
        Optional<User> optionalUser = userRepository.findUserByEmail(registerForm.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                registerForm.getEmail(),
                registerForm.getPassword());
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);
        User user = (User) authenticated.getPrincipal();
        String token = jwtProvider.generateToken(user);
        TokenForm tokenForm = new TokenForm();
        tokenForm.setToken(token);
        return optionalUser.isEmpty()
                ? new ApiResponse("user with email " + registerForm.getEmail() + " is already exists", false, null)
                : new ApiResponse("success", true, tokenForm);

    }

    @Override
    public ApiResponse deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
            return new ApiResponse("deleted successfully", true);
        } else {
            return new ApiResponse("there is not with id : " + id, false);
        }
    }


    @Override
    public ApiResponse update(RegisterForm form, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = new User();
        user.setId(id);
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setAge(form.getAge());
        user.setPassword(form.getPassword());
        User savedUser = userRepository.save(user);
        return optionalUser.isPresent()
                ? new ApiResponse("success", true, modelMapper.map(savedUser, RegisterDto.class))
                : new ApiResponse("there is not such user wit id: " + id + " to update", false);
    }

    public ApiResponse getAll(int page, int size) {
        Page<User> all = userRepository.findAll(PageRequest.of(page, size));
        ArrayList<RegisterDto> registerDtoArrayList = new ArrayList<>();
        for (User user : all) {
            RegisterDto registerDto = modelMapper.map(user, RegisterDto.class);
            registerDtoArrayList.add(registerDto);
        }
        return new ApiResponse("success", true, registerDtoArrayList);
    }

    public ApiResponse verifyEmail(String email, String emailCode) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (emailCode.equals(user.getEmailCode())) {
                user.setEnabled(true);
                userRepository.save(user);
                return new ApiResponse("successfully verified", true);
            }
        }
        return new ApiResponse("code does not match", false);
    }
}
