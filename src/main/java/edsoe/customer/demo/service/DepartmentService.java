package edsoe.customer.demo.service;

import edsoe.customer.demo.model.Department;
import edsoe.customer.demo.service.Exception.DepartmentNotFoundException;
import edsoe.customer.demo.service.dto.DepartmentCreateDto;

import java.util.Map;

public interface DepartmentService {

    Map<String, Object> getListDepartment(int page, int size);

    Department findDepartmentById(int id);

    Map<String, Object> findDepartmentByName(String name, int page, int size);

    Department saveDepartment(DepartmentCreateDto departmentDto);

    Department updateDepartment(DepartmentCreateDto departmentCreateDto, int departmentId) throws DepartmentNotFoundException;

    int deleteDepartment(int departmentId) throws DepartmentNotFoundException;
}
