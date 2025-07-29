package ru.yandex.practicum.filmorate.model;

public enum StandardFriendshipStatus {
    REQUESTED(1, "REQUESTED", "Запрос отправлен"),
    PENDING(2, "PENDING", "Запрос ожидает подтверждения"),
    CONFIRMED(3, "CONFIRMED", "Дружба подтверждена"),
    REJECTED(4, "REJECTED", "Запрос отклонен");

    private final FriendshipStatus status;

    StandardFriendshipStatus(int id, String name, String description) {
        this.status = new FriendshipStatus(id, name, description);
    }

    public FriendshipStatus getStatus() {
        return status;
    }
}
