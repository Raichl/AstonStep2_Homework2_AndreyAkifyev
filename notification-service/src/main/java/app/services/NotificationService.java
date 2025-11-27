package app.services;

import app.model.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final String DELETE_TYPE = "remove";
    private final String CREATE_TYPE = "create";

    private final PropertiesTextManager propertiesTextManager;

    public NotificationDto sendNotification(NotificationDto notificationDto) {
        String message = getSendingMessage(notificationDto.getNotificationType());
        NotificationDto notification = sendEmail(notificationDto.getEmail(), message);
        if (notification != null){
         return notification;
        }else{
            throw new RuntimeException("ошибка отправки email");
        }
    }

    private NotificationDto sendEmail(String email, String message) {
        return null;
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
