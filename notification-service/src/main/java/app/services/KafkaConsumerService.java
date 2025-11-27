package app.services;

import app.model.dto.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(
            topics = "notification-events",
            groupId = "application-group",
            containerFactory = "jsonKafkaListenerContainerFactory"
    )
    public void listenUserEvent(@Payload NotificationDto notificationDto) {
        log.info("Получен объект для оповещения из Kafka");
        try {
            notificationService.sendNotification(notificationDto);
            log.info("пользователь успешно оповещен");

        } catch (RuntimeException e) {
            log.error("ошибка оповещения {}",e.getMessage());
        }

    }

}
