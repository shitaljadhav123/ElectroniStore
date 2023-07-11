package com.bikkadit.electronicstrore.entities;

import lombok.*;

import javax.persistence.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
   // @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String userId;

    private String name;
    private String email;
    private String password;
    private String gender;
    private String about;
    private String imageName;
}
