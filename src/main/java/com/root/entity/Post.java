package com.root.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class Post {

    private Long id;
    private Long userId;
    private String text;
    private LocalDateTime timestamp;

}
