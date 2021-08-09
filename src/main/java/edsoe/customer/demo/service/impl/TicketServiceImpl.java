package edsoe.customer.demo.service.impl;

import edsoe.customer.demo.model.Ticket;
import edsoe.customer.demo.repository.TicketRepository;
import edsoe.customer.demo.service.Exception.DepartmentNotFoundException;
import edsoe.customer.demo.service.TicketService;
import edsoe.customer.demo.service.Exception.TicketNotFoundException;
import edsoe.customer.demo.service.dto.TicketCreateDto;
import edsoe.customer.demo.service.mapper.TicketMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TicketMapper ticketMapper;

    @Override
    public Map<String, Object> getListTicket(Date startDate, Date endDate, Integer departmentId, String departmentName,
                                             String customerNumber, int page, int size) {
        if (page < 1 || size < 1) return null;
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Ticket> pageTuts = null;
        if(startDate==null&&endDate==null&&departmentId==null&departmentName==null&&customerNumber==null)
            pageTuts = ticketRepository.findAll(paging);
        else if (startDate!=null&&endDate!=null)
            pageTuts = ticketRepository.findAllByReceivedDateBetween(startDate, endDate, paging);
        else if(departmentId!=null)
            pageTuts = ticketRepository.findAllByDepartment_Id(departmentId, paging);
        else if(departmentName!=null)
            pageTuts = ticketRepository.findAllByDepartment_Name(departmentName, paging);
        else if(customerNumber!=null)
            pageTuts = ticketRepository.findAllByCustomerNumber(customerNumber, paging);

        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", pageTuts.getNumber() + 1);
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        response.put("listTicket", pageTuts.getContent());
        return response;
    }

    @Override
    public Ticket saveTicket(TicketCreateDto ticketDto) throws DepartmentNotFoundException {
        return ticketRepository.save(ticketMapper.ticketDtoToTicket(ticketDto));
    }

    @Override
    public Ticket findTicketById(int id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if(ticket.isPresent())
            return ticket.get();
        return null;
    }


}
