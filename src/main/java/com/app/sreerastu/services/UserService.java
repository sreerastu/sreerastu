package com.app.sreerastu.services;

import com.app.sreerastu.domain.User;
import com.app.sreerastu.exception.DuplicateUserException;
import com.app.sreerastu.exception.InvalidUserIdException;

import java.util.List;

public interface UserService {


    User createUser(User user) throws DuplicateUserException;
    User updateUser(int userId,User user) throws InvalidUserIdException;
    List<User> getAllUsers();
    User getUserById(int userId) throws InvalidUserIdException;
    String deleteUserById(int userId) throws InvalidUserIdException;

 //   User  findByEmailIdAndPassword(LoginApiResponse loginApiResponse);

}
