package com.bikkadit.electronicstrore.controller;


import com.bikkadit.electronicstrore.dtos.PageableResponse;
import com.bikkadit.electronicstrore.dtos.UserDto;
import com.bikkadit.electronicstrore.entities.User;
import com.bikkadit.electronicstrore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    private User user;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;

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


    @Test
    public void createUserTest() throws Exception {

//        /users +POST+ user data as json
        //data as json+status created

        UserDto dto = mapper.map(user, UserDto.class);

        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        //actual request for url

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect( jsonPath("$.name").exists());
    }
       private String convertObjectToJsonString(Object user) {

        try {
           return new ObjectMapper().writeValueAsString(user);
      } catch (Exception e) {
           e.printStackTrace();
          return null;
       }


   }


   @Test
    public void updateUserTest() throws Exception {

       // /users/{userId} + PUT request+ json

       String userId = "1";
       UserDto dto = this.mapper.map(user, UserDto.class);

      Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/users/" + userId)
                                //  .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkdXJnZXNoQGRldi5pbiIsImlhdCI6MTY3NTI0OTA0MywiZXhwIjoxNjc1MjY3MDQzfQ.HQbZ4BrQlAgd5X40RZJhSMZ0zgZAfDcQtxJaSy97YZHgdNBV0g2r7-ZXRmw1EkKhkFtdkytG_E6I7MnsxVEZqg")
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
               .andExpect((ResultMatcher) jsonPath("$.name").exists());
    }



   //get all users : testing
   @Test
   public void getAllUsersTest() throws Exception {

      UserDto object1 = UserDto.builder().name("shital").email("shital@gmail.com").password("shital@123").about("Developer").build();
      UserDto object2 = UserDto.builder().name("Ram").email("ram@gmail.com").password("ram@123").about("Testing").build();
       UserDto object3 = UserDto.builder().name("Jyoti").email("jyoti@gmail.com").password("jyoti@123").about("programmer").build();
       UserDto object4 = UserDto.builder().name("Shivansh").email("shivansh@gmail.com").password("shiv@123").about("Testing").build();
       PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
       pageableResponse.setContent(Arrays.asList(
               object1, object2, object3, object4
        ));
       pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(userService.getAllUser(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);


       this.mockMvc.perform(MockMvcRequestBuilders.get("/users/allusers")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
               .andExpect(status().isOk());

}



}
