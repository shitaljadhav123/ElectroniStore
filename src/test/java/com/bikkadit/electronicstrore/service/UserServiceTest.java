package com.bikkadit.electronicstrore.service;

import com.bikkadit.electronicstrore.dtos.UserDto;
import com.bikkadit.electronicstrore.entities.User;
import com.bikkadit.electronicstrore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    User user;

    @BeforeEach
    public void init(){
        user = User.builder()
                .name("Shital")
                .email("shital@gmail.com")
                .about("I am a software developer")
                .gender("Female")
                .imageName("a.jpg")
                .password("abcd")
                .build();

    }

    //create
    @Test
    public void createUserTest(){

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);


        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));

        System.out.println(user1.getName());

        Assertions.assertNotNull(user1);

        Assertions.assertEquals("Shital",user1.getName());

    }

    //update
    @Test
    public void updateUserTest(){

        String userId="";

        UserDto userDto = UserDto.builder()
                .name("shital Jadhav")
                .about("This is updated user with details")
                .gender("Female")
                .imageName("abc.jpg")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto, userId);

       // UserDto updatedUser = mapper.map(user, UserDto.class);

        System.out.println(updatedUser.getName());

        Assertions.assertNotNull(userDto);

        Assertions.assertEquals(userDto.getName(),updatedUser.getName(),"name is not validated");
    }

    //delete user test cases

    @Test
    public void deleteUserTest(){

        String userId="userIdabc";

        Mockito.when(userRepository.findById("userIdabc")).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        Mockito.verify(userRepository,Mockito.times(1)).delete(user);

    }
}
