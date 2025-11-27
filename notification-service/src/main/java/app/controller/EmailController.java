package app.controller;

import app.model.dto.NotificationDto;
import app.services.EmailService;
import app.services.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class EmailController {

    private final NotificationService notificationService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/")
    public ResponseEntity<NotificationDto> notificationByRest(@RequestBody @Valid NotificationDto notificationDto){
      log.info("Получен запрос на оповещение пользователя по REST");
      try {
          notificationService.sendNotification(notificationDto);
          log.info("пользователь успешно оповещен");
          return ResponseEntity.ok().build();
      } catch (RuntimeException e) {
          log.error("ошибка оповещения пользователя {}",e.getMessage());
          return ResponseEntity.badRequest().build();
      }
    }

}
