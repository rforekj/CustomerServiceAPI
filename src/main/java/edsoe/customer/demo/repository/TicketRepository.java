package edsoe.customer.demo.repository;

import edsoe.customer.demo.model.Department;
import edsoe.customer.demo.model.Ticket;
import edsoe.customer.demo.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Date;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {
    Page<Ticket> findAll(Pageable pageable);

    Page<Ticket> findAllByReceivedDateBetween(Date startDate, Date endDate, Pageable pageable);

    Page<Ticket> findAllByDepartment_Id(int departmentId, Pageable pageable);

    Page<Ticket> findAllByDepartment_Name(String departmentName, Pageable pageable);

    Page<Ticket> findAllByCustomerNumber(String customerNumber, Pageable pageable);
}
