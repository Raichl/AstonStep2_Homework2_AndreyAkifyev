package app.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendJsonMessage(String topic, Object object) {
        log.info("Попытка отправки объекта в kafka в topic {}", topic);
        kafkaTemplate.send(topic, object)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("объект отправлен успешно");
                    } else {
                        log.error("ошибка отправки объекта");
                    }
                });
    }

    public void sendMessage(String topic, String message) {
        log.info("Попытка сообщения объекта в kafka в topic {}", topic);
        kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("сообщение отправлено успешно");
                    } else {
                        log.error("ошибка отправки сообщения");
                    }
                });
    }
}
