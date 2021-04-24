package com.root.service;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Number;
import com.root.entity.Friendship;
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

import static com.root.service.UserService.USERS_SIZE;
import static java.time.ZoneId.systemDefault;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendshipService {
    private static final String FRIENDSHIP_SQL = "INSERT INTO friendship (user_1, user_2, timestamp) VALUES (?,?,?)";
    private static final java.util.Date TIMESTAMP_FROM = Date.from(LocalDateTime.of(1980, 1, 1, 0, 0, 0)
            .atZone(systemDefault())
            .toInstant());
    private static final int FRIENDSHIP_SIZE = 70_000;

    private final JdbcTemplate jdbcTemplate;
    private final Number fakeNumber;
    private final DateAndTime fakeDateTime;


    public void create() {
        List<Friendship> friendships = IntStream.range(0, FRIENDSHIP_SIZE)
                .mapToObj(i -> new Friendship()
                        .setUserId(fakeNumber.numberBetween(1L, USERS_SIZE))
                        .setRelatedUserId(fakeNumber.numberBetween(1L, USERS_SIZE))
                        .setTimestamp(fakeDateTime.between(TIMESTAMP_FROM, Date.from(LocalDateTime.now().atZone(systemDefault()).toInstant()))
                                .toInstant()
                                .atZone(systemDefault())
                                .toLocalDateTime()))
                .collect(toList());

        jdbcTemplate.batchUpdate(FRIENDSHIP_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, friendships.get(i).getUserId());
                preparedStatement.setLong(2, friendships.get(i).getRelatedUserId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(friendships.get(i).getTimestamp()));
            }

            @Override
            public int getBatchSize() {
                return friendships.size();
            }
        });
        log.info("Friendships created successfully");
    }
}
