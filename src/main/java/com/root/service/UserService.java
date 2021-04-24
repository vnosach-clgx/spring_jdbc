package com.root.service;

import com.github.javafaker.DateAndTime;
import com.github.javafaker.Name;
import com.root.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private static final String USER_SQL = "INSERT INTO user (name, surname, dob) VALUES (?,?,?)";
    public static final int USERS_SIZE = 1000;

    private final JdbcTemplate jdbcTemplate;
    private final Name fakeName;
    private final DateAndTime fakeDateTime;


    public void create() {
        List<User> users = IntStream.range(0, USERS_SIZE)
                .mapToObj(i -> new User()
                        .setName(fakeName.firstName())
                        .setSurname(fakeName.lastName())
                        .setDob(fakeDateTime.birthday().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()))
                .collect(toList());

        jdbcTemplate.batchUpdate(USER_SQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, users.get(i).getName());
                preparedStatement.setString(2, users.get(i).getSurname());
                preparedStatement.setDate(3, Date.valueOf(users.get(i).getDob()));
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
        log.info("Users created successfully");
    }

    public List<String> getUniqueUserNames(int limitFriendship, int limitLikes) {
        List<String> result = jdbcTemplate.query("SELECT DISTINCT u.name FROM user u JOIN " +
                "(SELECT f.user_1, count(*) cnt FROM friendship f GROUP BY f.user_1) f ON u.id = f.user_1 " +
                "JOIN (SELECT l.user , count(*) cnt FROM social_like l " +
                "WHERE l.timestamp between '2025-03-01 00:00:00' and '2025-03-31 23:59:59' GROUP BY l.user) l ON u.id = l.user " +
                "AND f.cnt > ? AND l.cnt > ?;", (resultSet, i) -> resultSet.getString(1), limitFriendship, limitLikes);
        log.info("Names of users who has more than 100 friends and 100 likes in March 2025 {}", result);
        return result;
    }
}
