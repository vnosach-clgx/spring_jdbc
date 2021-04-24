package com.root.service;

import com.root.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void selectUserNames() {
        List<String> userNames = userService.getUniqueUserNames(1, 2);
        assertThat(userNames).usingRecursiveComparison().isEqualTo(List.of("John"));
    }

}
