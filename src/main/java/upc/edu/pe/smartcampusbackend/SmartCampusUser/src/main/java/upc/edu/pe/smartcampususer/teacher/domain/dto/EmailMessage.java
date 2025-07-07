package upc.edu.pe.smartcampususer.teacher.domain.dto;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;


@Data
public class EmailMessage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String parentEmail;
    private String subject;
    private String body;

}
