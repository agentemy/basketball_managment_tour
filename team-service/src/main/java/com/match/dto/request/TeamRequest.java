package com.match.dto.request;

import com.match.entity.TeamCategory;
import com.match.entity.TeamStatus;


public record TeamRequest(
        String name,
        TeamStatus status,
        TeamCategory category
) {
}
