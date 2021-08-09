package edsoe.customer.demo.service;

import edsoe.customer.demo.model.Ticket;
import edsoe.customer.demo.service.Exception.DepartmentNotFoundException;
import edsoe.customer.demo.service.dto.TicketCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Map;

public interface TicketService {

    Map<String, Object> getListTicket(Date startDate, Date endDate, Integer departmentId, String departmentName,
                                      String customerNumber, int page, int size);

    Ticket saveTicket(TicketCreateDto ticketDto) throws DepartmentNotFoundException;

    Ticket findTicketById(int id);
}
