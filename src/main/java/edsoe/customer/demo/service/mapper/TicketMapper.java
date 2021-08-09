package edsoe.customer.demo.service.mapper;

import edsoe.customer.demo.model.Department;
import edsoe.customer.demo.model.Ticket;
import edsoe.customer.demo.repository.DepartmentRepository;
import edsoe.customer.demo.service.Exception.DepartmentNotFoundException;
import edsoe.customer.demo.service.dto.TicketCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TicketMapper {

    @Autowired
    DepartmentRepository departmentRepository;

    public Ticket ticketDtoToTicket(TicketCreateDto ticketCreateDto) throws DepartmentNotFoundException {
        Ticket ticket = new Ticket();
        ticket.setFeedback(ticketCreateDto.getFeedback());
        ticket.setCustomerNumber(ticketCreateDto.getCustomerNumber());
        ticket.setReceivedDate(new Date());
        Optional<Department> department = departmentRepository.findById(ticketCreateDto.getDepartmentId());
        if(department.isPresent())
            ticket.setDepartment(department.get());
        else
            throw new DepartmentNotFoundException();
        return ticket;
    }
}
