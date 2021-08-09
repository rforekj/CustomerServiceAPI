package edsoe.customer.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String customerNumber;

    private String feedback;

    private Date receivedDate;

    private String reply;

    private Date repliedDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
