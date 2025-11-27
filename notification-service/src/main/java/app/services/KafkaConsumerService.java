package app.services;

import app.model.dto.NotificationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

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
        notificationService.sendNotification(notificationDto);
    }

}
