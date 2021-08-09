package edsoe.customer.demo.service.mapper;

import edsoe.customer.demo.model.Department;
import edsoe.customer.demo.service.dto.DepartmentCreateDto;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DepartmentMapper {

    public Department departmentDtoToDepartment(DepartmentCreateDto departmentCreateDto) {
        Department department = new Department();
        department.setName(departmentCreateDto.getName());
        department.setCreatedDate(new Date());
        return department;
    }
}
