package edsoe.customer.demo.web;

import edsoe.customer.demo.model.Department;
import edsoe.customer.demo.service.DepartmentService;
import edsoe.customer.demo.service.Exception.DepartmentNotFoundException;
import edsoe.customer.demo.service.dto.DepartmentCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/department")
@CrossOrigin(origins = "*")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping
    Map<String, Object> getListDepartment(@RequestParam(required = false) String name,
                                          @RequestParam int page, @RequestParam int size) {
        if(name==null)
            return departmentService.getListDepartment(page, size);
        return departmentService.findDepartmentByName(name, page, size);
    }

    @GetMapping("/{id}")
    Department getDepartmentById(@PathVariable int id) {
        return departmentService.findDepartmentById(id);
    }

    @PostMapping
    ResponseEntity<?> saveDepartment(@RequestBody DepartmentCreateDto departmentDto) {
        return ResponseEntity.ok(departmentService.saveDepartment(departmentDto));
    }

    @PutMapping("/{departmentId}")
    ResponseEntity<?> updateDepartment(@RequestBody DepartmentCreateDto departmentDto, @PathVariable int departmentId){
        try {
            return ResponseEntity.ok(departmentService.updateDepartment(departmentDto, departmentId));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(404).body("Not found id department");
        }
    }

    @DeleteMapping("/{departmentId}")
    ResponseEntity<?> deleteDepartment(@PathVariable int departmentId) {
        try {
            return ResponseEntity.ok(departmentService.deleteDepartment(departmentId));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(404).body("Not found id department");
        }
    }

}
