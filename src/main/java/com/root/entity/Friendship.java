package com.root.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Friendship {
    private Long userId;
    private Long relatedUserId;
    private LocalDateTime timestamp;
}
