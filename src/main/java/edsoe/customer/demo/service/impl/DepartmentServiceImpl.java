package edsoe.customer.demo.service.impl;

import edsoe.customer.demo.model.Department;
import edsoe.customer.demo.repository.DepartmentRepository;
import edsoe.customer.demo.service.DepartmentService;
import edsoe.customer.demo.service.Exception.DepartmentNotFoundException;
import edsoe.customer.demo.service.dto.DepartmentCreateDto;
import edsoe.customer.demo.service.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public Map<String, Object> getListDepartment(int page, int size) {
        if (page < 1 || size < 1) return null;
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Department> pageTuts = departmentRepository.findAll(paging);
        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", pageTuts.getNumber() + 1);
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        response.put("listDepartment", pageTuts.getContent());
        return response;
    }

    @Override
    public Department findDepartmentById(int id) {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isPresent())
            return department.get();
        return null;
    }

    @Override
    public Map<String, Object> findDepartmentByName(String name, int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Department> pageTuts = departmentRepository.findAllByNameContaining(name, paging);
        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", pageTuts.getNumber() + 1);
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        response.put("listDepartment", pageTuts.getContent());
        return response;
    }

    @Override
    public Department saveDepartment(DepartmentCreateDto departmentDto) {
        return departmentRepository.save(departmentMapper.departmentDtoToDepartment(departmentDto));
    }

    @Override
    public Department updateDepartment(DepartmentCreateDto departmentCreateDto, int departmentId) throws DepartmentNotFoundException {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if(!department.isPresent())
            throw new DepartmentNotFoundException();
        department.get().setName(departmentCreateDto.getName());
        return departmentRepository.save(department.get());
    }

    @Override
    public int deleteDepartment(int departmentId) throws DepartmentNotFoundException {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if(!department.isPresent())
            throw new DepartmentNotFoundException();
        departmentRepository.deleteById(departmentId);
        return departmentId;
    }


}
