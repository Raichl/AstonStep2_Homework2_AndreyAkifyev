package app.controller;

import app.mapper.UserModelAssembler;
import app.model.dto.UserDto;
import app.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "операции с пользователями")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    @Operation(summary = "Получить всех пользователей", description = "Возвращение всех пользователей с ссылками")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешно получен список пользователей",
            content = @Content(schema = @Schema(implementation = CollectionModel.class))),
            @ApiResponse(responseCode = "404",description = "Пользователи не найдены")
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAllUsers() {
        log.info("Запрос на получение пользователей");
        try {
            List<UserDto> users = userService.getAll();
            CollectionModel<EntityModel<UserDto>> collection = userModelAssembler.toCollectionModel(users);
            log.info("Запрос на получение пользователей успешно выполнен");
            return ResponseEntity.ok(collection);
        } catch (RuntimeException e) {
            log.error("Отказ в получение пользователей");
            return ResponseEntity.notFound().build();
        }

    }

    @Operation(summary = "Найти пользователя по ID", description = "Возвращение пользователя по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден",
                    content = @Content(schema = @Schema(implementation = EntityModel.class))),
            @ApiResponse(responseCode = "400",description = "Неверный ID"),
            @ApiResponse(responseCode = "404",description = "Пользователь не найдены")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> findById(
            @Parameter(description = "ID Пользователя для поиска", required = true, example = "1")
            @PathVariable @Min(1) Long id) {
        log.info("Запрос на получение пользователя с id: {}", id);
        try {
            UserDto user = userService.findById(id);
            EntityModel<UserDto> resource = userModelAssembler.toModel(user);
            log.info("Пользователь с id: {} успешно найден", id);
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            log.error("Ошибка получения: пользователь с id: {} не найден", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Создание нового пользователя", description = "Создание нового пользователя и его возвращение")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан",
                    content = @Content(schema = @Schema(implementation = EntityModel.class))),
            @ApiResponse(responseCode = "400",description = "Неверные данные пользователя")
    })
    @PostMapping
    public ResponseEntity<EntityModel<UserDto>> createUser(
            @Parameter(description = "Данные нового пользователя", required = true)
            @RequestBody @Valid UserDto userDto) {
        log.info("Запрос на создание пользователя");
        try {
            UserDto savedUser = userService.createUser(userDto);
            EntityModel<UserDto> resource = userModelAssembler.toModel(savedUser);
            URI location = linkTo(methodOn(UserController.class).findById(savedUser.getId())).toUri();
            log.info("Пользователь успешно создан, назначен id: {}", savedUser.getId());
            return ResponseEntity.created(location).body(resource);
        } catch (RuntimeException e) {
            log.error("В создании пользователя отказано");
            return ResponseEntity.badRequest().build();
        }

    }

    @Operation(summary = "Обновить пользователя", description = "обновить данные существующего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен",
                    content = @Content(schema = @Schema(implementation = EntityModel.class))),
            @ApiResponse(responseCode = "400",description = "Неверные данные"),
            @ApiResponse(responseCode = "404",description = "Пользователь не найдены")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> updateUser(
            @Parameter(description = "ID Пользователя для обновления", required = true, example = "1")
            @PathVariable @Min(1) Long id, @RequestBody @Valid UserDto userDto) {
        log.info("Запрос на обновление пользователя с id: {}", id);
        try {
            UserDto updatedUser = userService.update(id, userDto);
            EntityModel<UserDto> resource = userModelAssembler.toModel(updatedUser);
            log.info("Пользователь с id: {} успешно обновлен", id);
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            log.error("Ошибка обновления: пользователь с id: {} не найден", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "400",description = "Неверный ID"),
            @ApiResponse(responseCode = "404",description = "Пользователь не найдены")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID Пользователя для удаления", required = true, example = "1")
            @PathVariable @Min(1) Long id) {
        log.info("Запрос на удаление пользователя с id: {}", id);
        try {
            userService.delete(id);
            log.info("Пользователь с id: {} успешно удален", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Ошибка удаления: пользователь с id: {} не найден", id);
            return ResponseEntity.notFound().build();
        }


    }

}
