package com.bikkadit.electronicstrore.service.impl;

import com.bikkadit.electronicstrore.controller.UserController;
import com.bikkadit.electronicstrore.dtos.PageableResponse;
import com.bikkadit.electronicstrore.dtos.UserDto;
import com.bikkadit.electronicstrore.entities.User;
import com.bikkadit.electronicstrore.helper.AppConstants;
import com.bikkadit.electronicstrore.helper.Helper;
import com.bikkadit.electronicstrore.repository.UserRepository;
import com.bikkadit.electronicstrore.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {

        //generate userId in string format
        String userId = java.util.UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto to entity
        User user = mapper.map(userDto, User.class);
        User saveUser = userRepository.save(user);
        //dto to entity
        UserDto newDto = mapper.map(user, UserDto.class);
        return newDto;

    }


    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(AppConstants.NOT_FOUNd));
        user.setAbout(userDto.getAbout());
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImageName(userDto.getImageName());

        //save data
        User updatedUser = userRepository.save(user);
        UserDto updateDto = entityToDto(updatedUser);
        return updateDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(AppConstants.DELETE_USER));
        userRepository.delete(user);
    }


    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize ,String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : (Sort.by(sortBy).descending());

        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<User> pages=this.userRepository.findAll(page);

        /* ist<User> users = pages.getContent();
         List<User> userList = userRepository.findAll();
       List<UserDto> dtoList = users.stream().map((user) -> entityToDto(user)).collect(Collectors.toList());

       PageableResponse<UserDto> response=new PageableResponse<>();
       response.setContent(dtoList);
       response.setPageNumber(page.getPageNumber());
       response.setPageSize(page.getPageSize());
       response.setLastPage(pages.isLast());
       response.setTotalPages(pages.getTotalPages());
       response.setTotalElements(pages.getTotalElements());*/

        PageableResponse<UserDto> response = Helper.getPageableResponse(pages, UserDto.class);
        return response;
    }
    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(AppConstants.NOT_FOUNd));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException(AppConstants.NOT_FOUNd));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    private UserDto entityToDto(User savedUser) {
//       UserDto userDto= UserDto.builder()
//               .userId(savedUser.getUserId())
//               .name(savedUser.getName())
//               .email(savedUser.getEmail())
//               .password(savedUser.getPassword())
//               .about(savedUser.getPassword())
//               .gender(savedUser.getGender())
//               .imageName(savedUser.getImageName())
//               .build();
        return mapper.map(savedUser, UserDto.class);

    }

    private User dtoToEntity(UserDto userDto) {
//                User user=User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender()).build();
        return mapper.map(userDto, User.class);

    }
}
