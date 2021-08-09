package edsoe.customer.demo.repository;

import edsoe.customer.demo.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.nio.file.OpenOption;
import java.util.Optional;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    Page<Department> findAll(Pageable pageable);

    Page<Department> findAllByNameContaining(String name, Pageable pageable);

    @Modifying
    @Transactional
    void deleteById(int departmentId);
}
