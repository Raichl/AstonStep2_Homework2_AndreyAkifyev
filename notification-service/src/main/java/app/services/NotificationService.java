package app.services;

import app.model.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final String REMOVE_TYPE = "remove";
    private final String REMOVE_MESSAGE = "Здравствуйте! Ваш аккаунт был удалён.";
    private final String CREATE_TYPE = "create";
    private final String CREATE_MESSAGE = "Здравствуйте! Ваш аккаунт на сайте был успешно создан.";

    public void sendNotification(NotificationDto notificationDto) {
        String message = getSendingMessage(notificationDto.getNotificationType());
        sendEmail(notificationDto.getEmail(), message);
    }

    private void sendEmail(String email, String message) {

    }

    private String getSendingMessage(String notificationType) {
        if (notificationType.equals(REMOVE_TYPE)) {
            return REMOVE_MESSAGE;
        } else if (notificationType.equals(CREATE_TYPE)) {
            return CREATE_MESSAGE;
        } else {
            throw new RuntimeException("Неверный тип оповещения");
        }
    }
}
