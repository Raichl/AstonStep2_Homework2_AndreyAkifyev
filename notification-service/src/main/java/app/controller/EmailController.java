package app.controller;

import app.model.dto.NotificationDto;
import app.services.NotificationService;
import app.services.PropertiesTextManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class EmailController {

    private final NotificationService notificationService;

    @PostMapping("/")
    public ResponseEntity<NotificationDto> notificationByRest(@RequestBody @Valid NotificationDto notificationDto){
      log.info("Получен запрос на оповещение пользователя по REST");
      try {
          NotificationDto notification =  notificationService.sendNotification(notificationDto);
          return ResponseEntity.ok(notification);
      } catch (RuntimeException e) {
          log.error("ошибка оповещения пользователя {}",e.getMessage());
          return ResponseEntity.badRequest().build();
      }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){

        return ResponseEntity.ok(PropertiesTextManager.get("notification.create") );
    }
}
