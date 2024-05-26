package com.transactional.test.service;

import com.transactional.test.domain.User;
import com.transactional.test.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class ManualTransactionalService {

    private final UserRepository userRepository;
    private final PlatformTransactionManager transactionManager;

    public ManualTransactionalService(UserRepository userRepository, PlatformTransactionManager transactionManager) {
        this.userRepository = userRepository;
        this.transactionManager = transactionManager;
    }

    public void save(String username) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            User user = new User(username);
            userRepository.save(user);
            transactionManager.commit(status);
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    public User findById(Long userId) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            transactionManager.commit(status);
            return user;
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    public User findByUsername(String username) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            transactionManager.commit(status);
            return user;
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    public User update(String before, String after) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            final User user = userRepository.findByUsername(before).orElseThrow(()->new IllegalArgumentException("user not found"));
            user.update(after);
            transactionManager.commit(status);
            return user;
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    public void delete(String username) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            userRepository.deleteByUsername(username);
            transactionManager.commit(status);
        } catch (RuntimeException e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
