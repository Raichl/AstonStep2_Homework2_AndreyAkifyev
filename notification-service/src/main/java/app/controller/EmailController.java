package app.controller;

import app.model.dto.NotificationDto;
import app.services.EmailService;
import app.services.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class EmailController {

    private final NotificationService notificationService;

    private final EmailService emailService;

    @PostMapping("/")
    public ResponseEntity<NotificationDto> notificationByRest(@RequestBody @Valid NotificationDto notificationDto) {
        log.info("Получен запрос на оповещение пользователя по REST");

        notificationService.sendNotification(notificationDto);
        log.info("пользователь успешно оповещен");
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> notificationExeption(RuntimeException ex){
        log.error("ошибка оповещения пользователя {}", ex.getMessage());
        return ResponseEntity.badRequest().body("NotificationError" + ex.getMessage());
    }
}
