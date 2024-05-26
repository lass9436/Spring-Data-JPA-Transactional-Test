package com.transactional.test.service;

import com.transactional.test.domain.User;
import com.transactional.test.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class TransactionalService {

    UserRepository userRepository;

    public void save(String username) {
        final User user = new User(username);
        userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("user not found"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException("user not found"));
    }
    
    public User update(String before, String after) {
        final User user = userRepository.findByUsername(before).orElseThrow(()->new IllegalArgumentException("user not found"));
        user.update(after);
        return user;
    }
    
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }
}
