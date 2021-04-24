package com.root.config;

import com.root.service.FriendshipService;
import com.root.service.LikeService;
import com.root.service.PostService;
import com.root.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DbConfig {
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public CommandLineRunner storeData(UserService userService,
                                       PostService postService,
                                       FriendshipService friendshipService,
                                       LikeService likeService) {
        return args -> {
            userService.create();
            postService.create();
            friendshipService.create();
            likeService.create();
        };
    }

    @Bean
    public CommandLineRunner getUniqUserNames(UserService userService) {
        return args -> userService.getUniqueUserNames(100, 100);
    }
}
