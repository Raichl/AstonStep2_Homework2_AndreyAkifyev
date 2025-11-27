package app.mapper;

import app.model.dto.NotificationDto;
import app.model.dto.UserDto;
import app.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static UserDto toDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getAge()
        );
    }

    public static User toEntity(UserDto userDto) {
        return new User(userDto.getName(), userDto.getEmail(), userDto.getAge());
    }

    public static NotificationDto getNotificationDto(UserDto userDto, String notificationType) {
        return new NotificationDto(userDto.getEmail(), notificationType);
    }

    public static NotificationDto getNotificationDto(User user, String notificationType) {
        return new NotificationDto(user.getEmail(), notificationType);
    }
}
