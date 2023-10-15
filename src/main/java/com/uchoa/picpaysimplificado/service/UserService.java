package com.uchoa.picpaysimplificado.service;

import com.uchoa.picpaysimplificado.domain.user.User;
import com.uchoa.picpaysimplificado.domain.user.UserType;
import com.uchoa.picpaysimplificado.dto.UserDTO;
import com.uchoa.picpaysimplificado.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new Exception("User type Merchant is not authorized to make a transaction");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("User not found"));
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
