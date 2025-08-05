package ru.yandex.practicum.filmorate.repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FriendshipStatus;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendshipStatusInitializer {
    private final FriendshipStatusRepository statusRepository;
    private final JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        List<FriendshipStatus> defaultStatuses = List.of(
                new FriendshipStatus(1, "PENDING"),
                new FriendshipStatus(2, "CONFIRMED")
        );

        for (FriendshipStatus status : defaultStatuses) {
            if (statusRepository.findById(status.getId()).isEmpty()) {
                String sql = "INSERT INTO friendship_status (id, name) VALUES (?, ?)";
                jdbcTemplate.update(sql, status.getId(), status.getName());
            }
        }
    }
}
