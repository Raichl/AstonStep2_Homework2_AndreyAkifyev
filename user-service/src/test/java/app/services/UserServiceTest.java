package app.services;

import app.model.dto.UserDto;
import app.model.entity.User;
import app.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @Test
    void createUser_Success() {
        UserDto userDto = new UserDto("Ванек", "Test@mail.com", 25);
        User savedUser = new User("Ванек", "Test@mail.com", 25);
        savedUser.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = userService.createUser(userDto);

        assertNotNull(result);
        assertEquals("Ванек", result.getName());
        assertEquals("Test@mail.com", result.getEmail());
        assertEquals(25, result.getAge());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void getUserById_Success() {
        User user = new User("Ванек", "Test@mail.com", 25);
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDto result = userService.findById(1L);

        assertNotNull(result);
        assertEquals("Ванек", result.getName());
        assertEquals("Test@mail.com", result.getEmail());
        assertEquals(25, result.getAge());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_NotFound_ThrowException() {
        when(userRepository.findById(23L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.findById(23L));

        assertEquals("Пользователь не найден", exception.getMessage());
    }

    @Test
    void getAllUsers_Success() {
        User user1 = new User("Ванек", "Test@mail.com", 25);
        User user2 = new User("Санек", "Test2@mail.com", 22);
        User user3 = new User("Пенек", "Test3@mail.com", 23);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2, user3));

        List<UserDto> result = userService.getAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Ванек", result.get(0).getName());
        assertEquals("Санек", result.get(1).getName());
        assertEquals("Пенек", result.get(2).getName());
        verify(userRepository).findAll();

    }

    @Test
    void updateUser_Success() {
        User existingUser = new User("старое имя", "Test@mail.com", 25);
        existingUser.setId(1L);

        UserDto updateDto = new UserDto("новое имя", "Test@mail.com", 25);
        User updateUser = new User("новое имя", "Test@mail.com", 25);
        updateUser.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updateUser);

        UserDto result = userService.update(1L, updateDto);

        assertNotNull(result);
        assertEquals("новое имя", result.getName());
        assertEquals("Test@mail.com", result.getEmail());
        assertEquals(25, result.getAge());

        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUser_Success() {
        doNothing().when(userRepository).deleteById(1L);
        userService.delete(1L);
        verify(userRepository).deleteById(1L);
    }
}
