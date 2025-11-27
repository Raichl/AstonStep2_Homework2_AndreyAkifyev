package app.services;

import app.config.PropertiesTextManager;
import app.model.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final String DELETE_TYPE = "remove";
    private final String CREATE_TYPE = "create";

    private final PropertiesTextManager propertiesTextManager;

    private final EmailService emailService;

    public void sendNotification(NotificationDto notificationDto) {
        try {
            String message = getSendingMessage(notificationDto.getNotificationType());
            sendEmail(notificationDto.getEmail(), message);
        } catch (Exception e) {
            throw new RuntimeException("ошибка отправки email", e);
        }
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
