package com.app.sreerastu.services;

import com.app.sreerastu.domain.User;
import com.app.sreerastu.exception.DuplicateUserException;
import com.app.sreerastu.exception.InvalidUserIdException;
import com.app.sreerastu.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // @Autowired
    private UserRepository userRepository;


    @Override
    public User createUser(User user) throws DuplicateUserException {
        try {
            return userRepository.save(user);
        } catch (Exception ex) {
            throw new DuplicateUserException("user already exists");
        }
    }

    @Override
    public User updateUser(int userId, User user) throws InvalidUserIdException {
        User existingUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new InvalidUserIdException("Invalid userId"));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setContactNumber(user.getContactNumber());
        existingUser.setEmailAddress(user.getEmailAddress());
        existingUser.setPassword(user.getPassword());
        return userRepository.save(existingUser);

    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int userId) throws InvalidUserIdException {
        return userRepository.findById(userId).orElseThrow(() -> new InvalidUserIdException("Invalid userId"));
    }

    @Override
    public String deleteUserById(int userId) throws InvalidUserIdException {
        try {
            userRepository.deleteById(userId);
        } catch (Exception ex) {
            throw new InvalidUserIdException("Invalid userId");
        }
        return "User Successfully deleted" + userId;
    }

 /*   @Override
    public User findByEmailIdAndPassword(LoginApiResponse loginApiResponse) {


        //Validation


        //
        User login = userRepository.findByEmailIdAndPassword(loginApiResponse);
        return login;
    }*/
}
