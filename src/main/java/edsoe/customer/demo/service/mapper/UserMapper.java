package edsoe.customer.demo.service.mapper;

import edsoe.customer.demo.model.User;
import edsoe.customer.demo.service.dto.UserRegistrationDto;
import edsoe.customer.demo.service.dto.UserRespondDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserRespondDto userToUserRespondDto(User user) {
        UserRespondDto userRespondDto = new UserRespondDto();
        userRespondDto.setEmail(user.getEmail());
        userRespondDto.setName(user.getName());
        userRespondDto.setId(user.getId());
        return userRespondDto;
    }

    public User userRegistrationDtoToUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setEmail(userRegistrationDto.getEmail());
        user.setName(userRegistrationDto.getName());
        return user;
    }

}
