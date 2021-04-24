package com.root.service;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Number;
import com.root.entity.Like;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static com.root.service.PostService.POST_SIZE;
import static com.root.service.UserService.USERS_SIZE;
import static java.time.ZoneId.systemDefault;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeService {
    private static final String LIKE_SQL = "INSERT INTO social_like (post, user, timestamp) VALUES (?,?,?)";
    private static final int LIKE_SIZE = 300_000;
    private static final java.util.Date TIMESTAMP_FROM = Date.from(LocalDateTime.of(2025, 3, 1, 0, 0, 0).atZone(systemDefault()).toInstant());
    private static final java.util.Date TIMESTAMP_TO = Date.from(LocalDateTime.of(2025, 4, 5, 23, 59, 59).atZone(systemDefault()).toInstant());

    private final JdbcTemplate jdbcTemplate;
    private final Number fakeNumber;
    private final DateAndTime fakeDateTime;


    public void create() {
        List<Like> likes = IntStream.range(0, LIKE_SIZE)
                .mapToObj(i -> new Like()
                        .setUserId(fakeNumber.numberBetween(1L, USERS_SIZE))
                        .setPostId(fakeNumber.numberBetween(1L, POST_SIZE))
                        .setTimestamp(fakeDateTime.between(TIMESTAMP_FROM, TIMESTAMP_TO)
                                .toInstant()
                                .atZone(systemDefault())
                                .toLocalDateTime()))
                .collect(toList());

        jdbcTemplate.batchUpdate(LIKE_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, likes.get(i).getPostId());
                preparedStatement.setLong(2, likes.get(i).getUserId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(likes.get(i).getTimestamp()));
            }

            @Override
            public int getBatchSize() {
                return likes.size();
            }
        });

        log.info("Likes created successfully");
    }
}
