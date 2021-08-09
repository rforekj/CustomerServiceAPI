package edsoe.customer.demo.web;

import edsoe.customer.demo.model.Ticket;
import edsoe.customer.demo.service.Exception.DepartmentNotFoundException;
import edsoe.customer.demo.service.TicketService;
import edsoe.customer.demo.service.Exception.TicketNotFoundException;
import edsoe.customer.demo.service.dto.TicketCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ticket")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("/")
    Map<String, Object> getListTicket(@RequestParam(required = false) String startDate,
                                      @RequestParam(required = false) String endDate,
                                      @RequestParam(required = false) Integer departmentId,
                                      @RequestParam(required = false) String departmentName,
                                      @RequestParam(required = false) String customerNumber,
                                      @RequestParam int page, @RequestParam int size) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date start = simpleDateFormat.parse(startDate);
            Date end = simpleDateFormat.parse(endDate);
            return ticketService.getListTicket(start, end, departmentId, departmentName, customerNumber, page, size);
        } catch (ParseException e) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", "date format dd-MM-yyyy");
            return map;
        }

    }

    @PostMapping("/")
    ResponseEntity<?> saveTicket(@RequestBody TicketCreateDto ticketDto) {
        try {
            return ResponseEntity.ok(ticketService.saveTicket(ticketDto));
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.status(404).body("Not found id department");
        }
    }

    @GetMapping("/{ticketId}")
    Ticket getTicketById(@PathVariable int ticketId) {
        return ticketService.findTicketById(ticketId);
    }


}
