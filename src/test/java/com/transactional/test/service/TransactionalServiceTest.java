package com.transactional.test.service;

import com.transactional.test.domain.User;
import com.transactional.test.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@DisplayName("자동 트랜잭션 테스트")
class TransactionalServiceTest {

    @Autowired
    TransactionalService transactionalService;

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
    @DisplayName("클래스 확인")
    void checkClass(){
        log.info("transactionalService' class : {}", transactionalService.getClass());
    }

    @Test
    @DisplayName("저장")
    void save(){
        transactionalService.save("lass");
        final User user = userRepository.findByUsername("lass").orElseThrow();
        assertThat(user.getUsername()).isEqualTo("lass");
    }

    @Test
    @DisplayName("읽기")
    void get() {
        final User user1 = transactionalService.findByUsername("testUser1");
        assertThat(user1.getUsername()).isEqualTo("testUser1");
    }

    @Test
    @DisplayName("수정")
    void update() {
        final User user1 = transactionalService.update("testUser1","testUpdateUser1");
        assertThat(userRepository.findByUsername("testUpdateUser1").orElseThrow().getUsername()).isEqualTo(user1.getUsername());
    }

    @Test
    @DisplayName("삭제")
    void delete() {
        transactionalService.delete("testUser1");
        assertThat(userRepository.findByUsername("testUser1").isEmpty()).isTrue();
    }
}