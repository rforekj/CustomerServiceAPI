package edsoe.customer.demo.service.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
public class UserRegistrationDto {

    @NotBlank(message = "name must not be blank")
    private String name;

    @Email
    @NotBlank(message = "email must not be blank")
    @Size(min = 3, max = 100)
    private String email;


    @NotBlank(message = "password must not be blank")
    private String password;

    @NotBlank(message = "password must not be blank")
    private String confirmPassword;

}
