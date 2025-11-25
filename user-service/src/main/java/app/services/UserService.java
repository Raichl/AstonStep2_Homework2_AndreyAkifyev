package app.services;


import app.mapper.UserMapper;
import app.model.dto.UserDto;
import app.model.entity.User;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static app.mapper.UserMapper.toDto;
import static app.mapper.UserMapper.toEntity;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        if (userDto.getId() != null) {
            throw new RuntimeException("При создании ID должен быть null");
        }
        User user = toEntity(userDto);
        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return toDto(user);
    }

    @Transactional
    public UserDto update(Long id, UserDto userDto) {
        if (userDto.getId() != null && !userDto.getId().equals(id)) {
            throw new RuntimeException("ID в пути и теле не совпадают");
        }

        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setAge(userDto.getAge());
        User savedUser = userRepository.save(existingUser);
        return toDto(savedUser);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
