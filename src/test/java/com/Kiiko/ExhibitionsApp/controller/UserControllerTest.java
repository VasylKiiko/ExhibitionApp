package com.Kiiko.ExhibitionsApp.controller;

import com.Kiiko.ExhibitionsApp.controller.assembler.UserAssembler;
import com.Kiiko.ExhibitionsApp.controller.model.UserModel;
import com.Kiiko.ExhibitionsApp.dto.UserDto;
import com.Kiiko.ExhibitionsApp.exceptions.UserNotFoundException;
import com.Kiiko.ExhibitionsApp.repository.UserRepository;
import com.Kiiko.ExhibitionsApp.service.UserService;
import com.Kiiko.ExhibitionsApp.test.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.Kiiko.ExhibitionsApp.test.util.TestDataUtil.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@Import(TestConfig.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    private final static String DEFAULT_URI_PART = "/api/v1/users";

    @MockBean
    private UserService userService;
    @MockBean
    private UserAssembler userAssembler;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserByEmailSuccessfulTest() throws Exception {
        UserDto userDto = createUserDto(TEST_EMAIL);
        UserModel userModel = new UserModel(userDto);

        when(userService.getUserByEmail(TEST_EMAIL)).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(get(DEFAULT_URI_PART + "/email/" + TEST_EMAIL))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(TEST_EMAIL));
    }

    @Test
    void getUserByEmailUserNotFoundTest() throws Exception {
        when(userService.getUserByEmail(TEST_EMAIL)).thenThrow(new UserNotFoundException());

        mockMvc.perform(get(DEFAULT_URI_PART + "/email/" + TEST_EMAIL))
                .andDo(print())
                .andExpect(status().is(500))
                .andExpect(jsonPath("$.message").value("User not found "));
    }

    @Test
    void getUserByEmailInvalidEmailTest() throws Exception {
        List<String> invalidEmails = new ArrayList<>();
        invalidEmails.add("testemail");
        invalidEmails.add("email");
        invalidEmails.add("test@.com");
        invalidEmails.add("test.email.com");
        for (String email : invalidEmails) {
            mockMvc.perform(get(DEFAULT_URI_PART + "/email/" + email))
                    .andDo(print())
                    .andExpect(status().is(400));
        }
    }

    @Test
    void getUserByIdSuccessfulTest() throws Exception {
        UserDto userDto = createUserDto(TEST_USER_ID);
        UserModel userModel = new UserModel(userDto);

        when(userService.getUserById(TEST_USER_ID)).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(get(DEFAULT_URI_PART + "/userId/" + TEST_USER_ID))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.userId").value(TEST_USER_ID));
    }

    @Test
    void getUserByIdUserNotFoundTest() throws Exception {
        when(userService.getUserById(TEST_USER_ID)).thenThrow(new UserNotFoundException());

        mockMvc.perform(get(DEFAULT_URI_PART + "/userId/" + TEST_USER_ID))
                .andDo(print())
                .andExpect(status().is(500));
    }

    @Test
    void addUserSuccessfulTest() throws Exception {
        UserDto userDto = createUserDto(TEST_EMAIL);
        UserModel userModel = new UserModel(userDto);

        when(userRepository.existsByEmail(TEST_EMAIL)).thenReturn(false);

        when(userService.addUser(userDto)).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(post(DEFAULT_URI_PART)
                .content(new ObjectMapper().writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(TEST_EMAIL));
    }

    @Test
    void addUserFailed_NotValidPassword() throws Exception {
        UserDto userDto = createUserDto(TEST_EMAIL);
        userDto.setPassword("invalid");

        mockMvc.perform(post(DEFAULT_URI_PART)
                .content(new ObjectMapper().writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserFailed_NotEqualPasswords() throws Exception {
        UserDto userDto = createUserDto(TEST_EMAIL);
        userDto.setPassword("Qwerty1234!");
        userDto.setRepeatPassword("Qwerty123!");

        mockMvc.perform(post(DEFAULT_URI_PART)
                .content(new ObjectMapper().writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserFailed_EmailAlreadyExists() throws Exception {
        UserDto userDto = createUserDto(TEST_EMAIL);

        when(userRepository.existsByEmail(TEST_EMAIL)).thenReturn(true);

        mockMvc.perform(post(DEFAULT_URI_PART)
                .content(new ObjectMapper().writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addUserFailed_NotValidEmail() throws Exception {
        String invalidEmail = "invalid.email";
        UserDto userDto = createUserDto(invalidEmail);

        when(userRepository.existsByEmail(invalidEmail)).thenReturn(false);

        mockMvc.perform(post(DEFAULT_URI_PART)
                .content(new ObjectMapper().writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUserSuccessfulTest() throws Exception {
        UserDto userDto = createUserDto(TEST_EMAIL);
        UserModel userModel = new UserModel(userDto);

        when(userService.updateUser(TEST_USER_ID, userDto)).thenReturn(userDto);
        when(userAssembler.toModel(userDto)).thenReturn(userModel);

        mockMvc.perform(put(DEFAULT_URI_PART + "/" + TEST_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateUserFailedTest_NullEmail() throws Exception {
        UserDto userDto = createUserDto(TEST_USER_ID);
        userDto.setEmail(null);

        mockMvc.perform(put(DEFAULT_URI_PART + "/" + TEST_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUserFailedTest_NullPassword() throws Exception {
        UserDto userDto = createUserDto(TEST_USER_ID);
        userDto.setPassword(null);

        mockMvc.perform(put(DEFAULT_URI_PART + "/" + TEST_USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}