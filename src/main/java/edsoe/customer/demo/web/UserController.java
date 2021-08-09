package edsoe.customer.demo.web;

import edsoe.customer.demo.config.JwtTokenUtil;
import edsoe.customer.demo.model.User;
import edsoe.customer.demo.service.DepartmentService;
import edsoe.customer.demo.service.UserService;
import edsoe.customer.demo.service.dto.JwtRequest;
import edsoe.customer.demo.service.dto.JwtResponse;
import edsoe.customer.demo.service.dto.UserRegistrationDto;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private JavaMailSender mailSender;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRegistrationDto user, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return ResponseEntity.status(400).body(result.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage));
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return ResponseEntity.status(400).body("Password must match");
        }
        if (userDetailsService.findByEmail(user.getEmail()) == null) {
            return ResponseEntity.ok(userDetailsService.save(user));
        } else {
            return ResponseEntity.status(400).body("There is already an account registered with that email");
        }

    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> processForgotPassword(@RequestParam String email) {
        String token = RandomString.make(30);
        try {
            userDetailsService.updateResetPasswordToken(token, email);
            String resetPasswordLink = "http://localhost:8087/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            return ResponseEntity.ok("We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(404).body("Not found user with this email");
        } catch (UnsupportedEncodingException | MessagingException e) {
            return ResponseEntity.status(500).body("Error while sending email");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> processResetPassword(@RequestParam String token, @RequestParam String password) {
        User user = userDetailsService.getByResetPasswordToken(token);
        if (user == null) {
            return ResponseEntity.status(401).body("Invalid Token");
        } else {
            userDetailsService.updatePassword(user, password);
            return ResponseEntity.ok("Success");
        }
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("hjeuxt251099@gmail.com", "Reset Password");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

}
