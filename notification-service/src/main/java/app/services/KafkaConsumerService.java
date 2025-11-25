package app.services;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(
            topics = "notification-events",
            groupId = "application-group"
    )
    public void listenUserEvent(String message){
        System.out.println("получено сообщение из kafka " +message);
    }

}
