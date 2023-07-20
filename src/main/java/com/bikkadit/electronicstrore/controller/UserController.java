package com.bikkadit.electronicstrore.controller;

import com.bikkadit.electronicstrore.dtos.PageableResponse;
import com.bikkadit.electronicstrore.dtos.UserDto;
import com.bikkadit.electronicstrore.helper.ApiResponseMessage;
import com.bikkadit.electronicstrore.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    //create

    /**
     * @param userDto
     * @return
     * @Author Shital
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        logger.info("Creating new user: {}", userDto);
        UserDto createdUser = userService.createUser(userDto);
        logger.info("User created successfully: {}", createdUser);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //update

    /**
     * @param userId
     * @param userDto
     * @return
     * @Author Shital
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId, @RequestBody UserDto userDto) {
        logger.info("User Update with Id :- { }", userId);
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        logger.info("User Updated Sucessfully...!!!", updatedUserDto);

        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    //delete

    /**
     * @param userId
     * @return
     * @throws IOException
     * @Author Shital
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        logger.info("Deleteting user with ID:- {}", userId);
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder().message("User is deleted successfully !!").success(true).status(HttpStatus.OK).build();

        logger.info("User delete Sucessfully..!!!", userId);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    //get all

    /**
     * @param
     * @param
     * @return
     * @Author Shital
     */
    @GetMapping("/allusers")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam (value="pageNumber",defaultValue ="0",required = false) int pageNumber,
            @RequestParam (value="pageSize",defaultValue="10",required = true) int pageSize,
            @RequestParam (value="sortBy",defaultValue ="name",required = false) String sortBy,
            @RequestParam (value="sortDir",defaultValue="10",required =false) String sortDir
    ) {

        logger.info("Retrieving all users...!!!");
        PageableResponse<UserDto> allUsers = userService.getAllUser(pageNumber,pageSize,sortBy,sortDir);
        logger.info("Successfully retrieved all users...!!!");
        return new ResponseEntity<PageableResponse<UserDto>>(allUsers, HttpStatus.OK);
    }

    //get Single

    /**
     * @param userId
     * @return
     * @Author Shital
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        logger.info("Retrieving user with ID: {}", userId);
        UserDto userById = userService.getUserById(userId);
        logger.info("Successfully retrieved user: {}", userById);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    //get by email

    /**
     * @param email
     * @return
     * @Author Shital
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        logger.info("Retrieving user by email: {}", email);
        UserDto userByEmail = userService.getUserByEmail(email);
        logger.info("Successfully retrieved user: {}", userByEmail);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    //Search user

    /**
     * @param keywords
     * @return
     * @Author Shital
     */
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords) {
        logger.info("Searching for users containing keyword: {}", keywords);
        List<UserDto> userDtos = userService.searchUser(keywords);
        logger.info("Successfully retrieved users matching the search...!!!");
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }


}
