package edsoe.customer.demo.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DepartmentCreateDto {

    @NotBlank(message = "name must not be blank")
    private String name;
}
