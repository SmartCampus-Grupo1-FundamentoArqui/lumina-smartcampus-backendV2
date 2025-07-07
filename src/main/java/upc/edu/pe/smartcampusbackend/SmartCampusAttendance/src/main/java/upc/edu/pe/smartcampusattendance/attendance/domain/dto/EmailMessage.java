package upc.edu.pe.smartcampusattendance.attendance.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@Data
public class EmailMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String parentEmail;
    private String subject;
    private String body;

}
