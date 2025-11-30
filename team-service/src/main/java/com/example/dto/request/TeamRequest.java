package com.example.dto.request;

import com.example.entity.TeamCategory;
import com.example.entity.TeamStatus;


public record TeamRequest(
        String name,
        TeamStatus status,
        TeamCategory category
) {
}
