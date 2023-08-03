package com.bikkadit.electronicstrore.service;

import com.bikkadit.electronicstrore.dtos.PageableResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;


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

        assertEquals("Shital",user1.getName());

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

        assertEquals(userDto.getName(),updatedUser.getName(),"name is not validated");
    }

    //delete user test cases

    @Test
    public void deleteUserTest(){

        String userId="userIdabc";

        Mockito.when(userRepository.findById("userIdabc")).thenReturn(Optional.of(user));

        userService.deleteUser(userId);

        Mockito.verify(userRepository,Mockito.times(1)).delete(user);

    }

    //get all user test cases
    @Test
    public void getAllUsersTest(){

       User user1 = User.builder()
                .name("priya")
                .email("priya@gmail.com")
                .about("I am a software developer")
                .gender("Female")
                .imageName("abc.jpg")
                .password("abcd")
                .build();

        User user2= User.builder()
                .name("Shiv")
                .email("shiv@gmail.com")
                .about("I am a software developer")
                .gender("Male")
                .imageName("a.jpg")
                .password("abcd")
                .build();

        List<User> userList = Arrays.asList(user, user1, user2);

        Page<User> page = new PageImpl<>(userList);

        Mockito.when(userRepository.findAll((Pageable)Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUser = userService.getAllUser(1,2,"name","asc");

        assertEquals(3,allUser.getContent().size());
    }

    //search user test cases
    @Test
    public void searchUserTest(){

        User user= User.builder()
                .name("Shiv")
                .email("shiv@gmail.com")
                .about("I am a software developer")
                .gender("Male")
                .imageName("a.jpg")
                .password("abcd")
                .build();

        User user1 = User.builder() 
                .name("priya Jadhav")
                .email("priya@gmail.com")
                .about("I am a software developer")
                .gender("Female")
                .imageName("abc.jpg")
                .password("abcd")
                .build();

        User user2= User.builder()
                .name("Shiv Jadhav")
                .email("shiv@gmail.com")
                .about("I am a software developer")
                .gender("Male")
                .imageName("a.jpg")
                .password("abcd")
                .build();



        User user3= User.builder()
                .name("Shital")
                .email("shital@gmail.com")
                .about("I am a software developer")
                .gender("Female")
                .imageName("a.jpg")
                .password("abcd")
                .build();

         String keywords="S";
        Mockito.when(userRepository.findByNameContaining(keywords)).thenReturn(Arrays.asList(user,user1,user2,user3));

        List<UserDto> userDtos = userService.searchUser(keywords);

        assertEquals(4,userDtos.size(),"size not matched !!");
    }

    @Test
    void getUserById() {

        String userId = UUID.randomUUID().toString();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
        UserDto user = userService.getUserById(userId);
        assertEquals("abcd",user.getPassword());
    }

    @Test
    void getUserByEmail() {

        String email="shital@gmail.com";

        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserDto user3 = userService.getUserByEmail(email);
        String name="Shital";
        assertEquals(name,user3.getName());
 }
}
