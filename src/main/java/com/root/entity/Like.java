package com.root.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Like {
    private Long postId;
    private Long userId;
    private LocalDateTime timestamp;
}
