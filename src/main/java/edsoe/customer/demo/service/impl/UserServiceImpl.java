package edsoe.customer.demo.service.impl;

import edsoe.customer.demo.model.User;
import edsoe.customer.demo.repository.UserRepository;
import edsoe.customer.demo.service.UserService;
import edsoe.customer.demo.service.dto.UserRegistrationDto;
import edsoe.customer.demo.service.dto.UserRespondDto;
import edsoe.customer.demo.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserRespondDto findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) return userMapper.userToUserRespondDto(user.get());
        return null;
    }

    public UserRespondDto save(UserRegistrationDto registration) {
        return userMapper.userToUserRespondDto(userRepository.save(userMapper.userRegistrationDtoToUser(registration)));
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                user.get().getPassword(), new ArrayList<>());
    }


    @Override
    public UserRespondDto getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            Optional<User> user = userRepository.findByEmail(((UserDetails) principal).getUsername());
            if (user.isPresent()) return userMapper.userToUserRespondDto(user.get());
        }
        return null;
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setResetPasswordToken(token);
            userRepository.save(user.get());
        } else {
            throw new UsernameNotFoundException("Could not find any user with the email " + email);
        }
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }

}
