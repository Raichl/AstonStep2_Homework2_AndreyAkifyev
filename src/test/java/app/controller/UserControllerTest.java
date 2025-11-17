package app.controller;

import app.model.dto.UserDto;
import app.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    void createUser_Success() throws Exception {
        UserDto request = new UserDto("User1","user1@test.com",25);
        UserDto response = new UserDto(1L,"User1","user1@test.com",25);

        when(userService.createUser(any(UserDto.class))).thenReturn(response);

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("User1"))
                .andExpect(jsonPath("$.email").value("user1@test.com"))
                .andExpect(jsonPath("$.age").value(25));

        verify(userService).createUser(any(UserDto.class));
    }


    @Test
    void getAllUsers_Success() throws Exception {
        UserDto userDto1 = new UserDto(1L,"User1","user1@test.com",25);
        UserDto userDto2 = new UserDto(2L,"User2","user2@test.com",30);

        when(userService.getAll()).thenReturn((List.of(userDto1,userDto2)));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[1].id").value(2));
        verify(userService).getAll();
    }


    @Test
    void getUserById_Success()throws Exception{
        UserDto userDto1 = new UserDto(1L,"User1","user1@test.com",25);

        when(userService.findById(1L)).thenReturn(userDto1);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("User1"));
        verify(userService).findById(1L);
    }


    @Test
    void getUserById_notFound() throws Exception {
        when(userService.findById(999L)).thenThrow(new RuntimeException("не найден"));

        mockMvc.perform(get("/users/999"))
                .andExpect(status().isNotFound());

        verify(userService).findById(999L);
    }

    @Test
    void updateUser_Success() throws Exception {
        UserDto request = new UserDto(null,"User1","user1@test.com",30);
        UserDto response = new UserDto(1L,"User1","user1@test.com",30);

        when(userService.update(eq(1L),any(UserDto.class))).thenReturn(response);

        mockMvc.perform(put("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("User1"));
        verify(userService).update(eq(1L), any(UserDto.class));
    }

    @Test
    void deleteUser_Success() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/users/1")).andExpect(status().isOk());

        verify(userService).delete(1L);
    }

}
