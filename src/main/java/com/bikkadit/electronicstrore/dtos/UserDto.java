package com.bikkadit.electronicstrore.dtos;

import com.bikkadit.electronicstrore.validation.ImageNameValid;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @NotBlank
    @Size(min= 3, max=15,message="Invalid name !!")
    private String name;

    @Email(message ="Invalid user Email!!")
    private String email;

    @NotBlank(message="Invalid password")
    private String password;

    @Size(min=4,max=6, message="Invalid Gender !!")
    private String gender;

    @NotBlank(message="Write something about yourself")
    private String about;

    @ImageNameValid
    private String imageName;
}
