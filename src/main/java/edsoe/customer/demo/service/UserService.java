package edsoe.customer.demo.service;

import edsoe.customer.demo.model.User;
import edsoe.customer.demo.service.dto.UserRegistrationDto;
import edsoe.customer.demo.service.dto.UserRespondDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    UserRespondDto findByEmail(String email);

    UserRespondDto save(UserRegistrationDto registration);

    UserRespondDto getCurrentUser();

    void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException;

    User getByResetPasswordToken(String token);

    void updatePassword(User user, String newPassword);
}
