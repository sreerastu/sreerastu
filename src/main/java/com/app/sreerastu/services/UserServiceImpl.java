package com.app.sreerastu.services;

import com.app.sreerastu.domain.Booking;
import com.app.sreerastu.domain.User;
import com.app.sreerastu.domain.Vendor;
import com.app.sreerastu.dto.LoginApiDto;
import com.app.sreerastu.exception.AuthenticationException;
import com.app.sreerastu.exception.DuplicateUserException;
import com.app.sreerastu.exception.InvalidUserIdException;
import com.app.sreerastu.exception.UserNotFoundException;
import com.app.sreerastu.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Override
    public User authenticate(String emailAddress, String password) throws AuthenticationException {
        return userRepository.findByEmailAddressAndPassword(emailAddress,password);

       /* //Response
        if (Objects.isNull(loginResult)) {
            throw new AuthenticationException("Invalid credentials");
        }
        return "Login Successful";*/
    }

    @Override
    public User getUserByMail(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress);
    }

    public List<Booking> getBookingsByUserId(int userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return user.getBookings();
     }
}
