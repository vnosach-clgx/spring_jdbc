package com.root.service;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Lorem;
import com.github.javafaker.Name;
import com.github.javafaker.Number;
import com.root.entity.Post;
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
public class PostService {
    private static final String POST_SQL = "INSERT INTO post (user, text, timestamp) VALUES (?,?,?)";
    public static final int POST_SIZE = 300;
    private static final java.util.Date TIMESTAMP_FROM = Date.from(LocalDateTime.of(1980, 1, 1, 0, 0, 0).atZone(systemDefault()).toInstant());

    private final JdbcTemplate jdbcTemplate;
    private final Lorem fakeLorem;
    private final DateAndTime fakeDateTime;
    private final Number fakeNumber;

    public void create() {
        List<Post> postList = IntStream.range(0, POST_SIZE)
                .mapToObj(i -> new Post()
                        .setUserId(fakeNumber.numberBetween(1L, USERS_SIZE))
                        .setText(fakeLorem.sentence(5))
                        .setTimestamp(fakeDateTime.between(TIMESTAMP_FROM, Date.from(LocalDateTime.now().atZone(systemDefault()).toInstant()))
                                .toInstant()
                                .atZone(systemDefault())
                                .toLocalDateTime()))
                .collect(toList());

        jdbcTemplate.batchUpdate(POST_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, postList.get(i).getUserId());
                preparedStatement.setString(2, postList.get(i).getText());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(postList.get(i).getTimestamp()));
            }

            @Override
            public int getBatchSize() {
                return postList.size();
            }
        });
        log.info("Posts created successfully");
    }
}
