package app.services;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void sendJsonMessage(String topic, Object object){
        kafkaTemplate.send(topic,object);
    }

    public void sendMessage(String topic, String message){
        kafkaTemplate.send(topic,message);
    }
}
