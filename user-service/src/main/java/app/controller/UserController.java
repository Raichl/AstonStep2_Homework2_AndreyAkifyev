package app.controller;

import app.model.dto.UserDto;
import app.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Запрос на получение пользователей");
        try {
            List<UserDto> users = userService.getAll();
            log.info("Запрос на получение пользователей успешно выполнен");
            return ResponseEntity.ok(users);
        } catch (RuntimeException e) {
            log.error("Отказ в получение пользователей");
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable @Min(1) Long id) {
        log.info("Запрос на получение пользователя с id: {}", id);
        try {
            UserDto user = userService.findById(id);
            log.info("Пользователь с id: {} успешно найден", id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            log.error("Ошибка получения: пользователь с id: {} не найден", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        log.info("Запрос на создание пользователя");
        try {
            UserDto savedUser = userService.createUser(userDto);
            log.info("Пользователь успешно создан, назначен id: {}", savedUser.getId());
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            log.error("В создании пользователя отказано");
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable @Min(1) Long id, @RequestBody @Valid UserDto userDto) {
        log.info("Запрос на обновление пользователя с id: {}", id);
        try {
            UserDto updaterUser = userService.update(id, userDto);
            log.info("Пользователь с id: {} успешно обновлен", id);
            return ResponseEntity.ok(updaterUser);
        } catch (RuntimeException e) {
            log.error("Ошибка обновления: пользователь с id: {} не найден", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Min(1) Long id) {
        log.info("Запрос на удаление пользователя с id: {}", id);
        try {
            userService.delete(id);
            log.info("Пользователь с id: {} успешно удален", id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Ошибка удаления: пользователь с id: {} не найден", id);
            return ResponseEntity.notFound().build();
        }


    }

}
