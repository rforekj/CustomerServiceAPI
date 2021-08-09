package edsoe.customer.demo.service.dto;

import lombok.Data;

@Data
public class TicketCreateDto {

    private String customerNumber;

    private String feedback;

    private int departmentId;
}
