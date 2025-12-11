package app.services;

import app.config.PropertiesTextManager;
import app.model.dto.NotificationDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private static final Logger CB_logger = LoggerFactory.getLogger("CIRCUIT_BREAKER-LOGGER");
    private final String DELETE_TYPE = "remove";
    private final String CREATE_TYPE = "create";

    private final PropertiesTextManager propertiesTextManager;

    private final EmailService emailService;

    @CircuitBreaker(name = "emailService", fallbackMethod = "sendNotificationFallback")
    @Retry(name = "emailService", fallbackMethod = "sendNotificationFallback")
    public void sendNotification(NotificationDto notificationDto) {
        log.info("отправка сообщения для{}, тип: {}", notificationDto.getEmail(), notificationDto.getNotificationType());
        String message = getSendingMessage(notificationDto.getNotificationType());
        sendEmail(notificationDto.getEmail(), message);
    }

    private void sendNotificationFallback(NotificationDto notificationDto, Throwable throwable){
       CB_logger.error("CircuitBreaker: Ошибка отправки - Email: {}, Type: {}, Error: {}",
               notificationDto.getEmail(),
               notificationDto.getNotificationType(),
               throwable != null ? throwable.getMessage() : "Unknown");

    }

    private void sendEmail(String email, String message) {
        emailService.sendEmail(email, "no reply", message);
    }

    private String getSendingMessage(String notificationType) {
        if (notificationType.equals(DELETE_TYPE)) {
            return PropertiesTextManager.get("notification.delete");
        } else if (notificationType.equals(CREATE_TYPE)) {
            return PropertiesTextManager.get("notification.create");
        } else {
            throw new RuntimeException("Неизвестный тип оповещения");
        }
    }
}
