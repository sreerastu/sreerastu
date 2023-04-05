package com.app.sreerastu.services;

import com.app.sreerastu.domain.User;
import com.app.sreerastu.exception.AuthenticationException;
import com.app.sreerastu.exception.DuplicateUserException;
import com.app.sreerastu.exception.InvalidUserIdException;
import com.app.sreerastu.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


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
        existingUser.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
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
        return userRepository.findByEmailAddressAndPassword(emailAddress, password);
    }

    @Override
    public User getUserByMail(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress);
    }


    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAddress(emailAddress);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email address: " + emailAddress);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmailAddress(), user.getPassword(), user.getAuthorities());
    }
}
