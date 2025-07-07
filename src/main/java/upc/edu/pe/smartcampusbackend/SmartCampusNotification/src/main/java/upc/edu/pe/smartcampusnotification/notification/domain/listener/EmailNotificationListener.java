package upc.edu.pe.smartcampusnotification.notification.domain.listener;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import upc.edu.pe.smartcampusnotification.notification.application.services.EmailService;
import upc.edu.pe.smartcampusnotification.notification.config.RabbitConfig;
import upc.edu.pe.smartcampusnotification.notification.domain.dto.EmailMessage;

@Component
@RequiredArgsConstructor
public class EmailNotificationListener {

    private final EmailService emailService;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void receiveEmailMessage(EmailMessage message) {
        System.out.println("Received email message for: " + message.getParentEmail());
        try {
            emailService.sendEmail(message.getParentEmail(), message.getSubject(), message.getBody());
        } catch (Exception e) {
            System.err.println("Error enviando correo: " + e.getMessage());
            // Aquí puedes agregar lógica para manejar el error, como enviar a una dead-letter queue
            // o simplemente registrar el error para evitar reintentos infinitos.

        }
    }

}
