package com.transactional.test.service;

import com.transactional.test.domain.User;
import com.transactional.test.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("트랜잭션 미적용 테스트")
class NonTransactionalServiceTest {

    @Autowired
    NonTransactionalService nonTransactionalService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user1 = new User("testUser1");
        userRepository.save(user1);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("저장")
    void save(){
        nonTransactionalService.save("lass");
        final User user = userRepository.findByUsername("lass").orElseThrow();
        assertThat(user.getUsername()).isEqualTo("lass");
    }

    @Test
    @DisplayName("읽기")
    void get() {
        final User user1 = nonTransactionalService.findByUsername("testUser1");
        assertThat(user1.getUsername()).isEqualTo("testUser1");
    }

    @Test
    @DisplayName("수정")
    void update() {
        final User user1 = nonTransactionalService.update("testUser1","testUpdateUser1");

        // No value present
        // assertThat(userRepository.findByUsername("testUpdateUser1").orElseThrow().getUsername()).isEqualTo(user1.getUsername());

        // 수정이 적용되지 않음.
        assertThat(userRepository.findByUsername("testUpdateUser1").isEmpty()).isTrue();
    }

    @Test
    @DisplayName("삭제")
    void delete() {
        // No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
        // nonTransactionalService.delete("testUser1");
        // assertThat(userRepository.findByUsername("testUser1").isEmpty()).isTrue();

        // 삭제가 되지 않음. InvalidDataAccessApiUsageException 발생
        assertThrows(InvalidDataAccessApiUsageException.class, () -> nonTransactionalService.delete("testUser1"));
    }
  
}