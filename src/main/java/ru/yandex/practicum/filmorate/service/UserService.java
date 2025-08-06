package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.FriendRepository;
import ru.yandex.practicum.filmorate.repository.FriendshipStatusRepository;
import ru.yandex.practicum.filmorate.repository.UserRepository;

import java.util.List;

/**
 * Сервисный класс для работы с пользователями.
 * Обеспечивает бизнес-логику приложения для операций с пользователями и их дружескими связями.
 * Взаимодействует с репозиторием {@link UserRepository} для доступа к данным.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FriendshipStatusRepository statusRepository;

    /**
     * Получает список всех пользователей.
     *
     * @return список объектов {@link User}
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Находит пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return найденный объект {@link User}
     * @throws NotFoundException если пользователь с указанным id не найден
     */
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    /**
     * Создает нового пользователя.
     * Если имя пользователя не указано, используется логин.
     *
     * @param user объект пользователя для создания
     * @return созданный объект {@link User} с присвоенным идентификатором
     */
    public User create(User user) {
        // Проверяем, что пользователь новый (без ID)
        if (user.getId() != null) {
            throw new IllegalArgumentException("New user must not have an ID");
        }

        // Устанавливаем имя, если оно пустое
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        // Проверяем уникальность email и login
        validateUserUniqueness(user);

        return userRepository.save(user);
    }

    private void validateUserUniqueness(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ValidationException("Email already exists");
        }
        if (userRepository.existsById(user.getId())) {
            throw new ValidationException("Id already exists");
        }
    }

    /**
     * Обновляет существующего пользователя.
     *
     * @param user объект пользователя с обновленными данными
     * @return обновленный объект {@link User}
     * @throws NotFoundException если пользователь с указанным id не найден
     */
    public User update(User user) {
        findById(user.getId()); // Проверка существования пользователя
        return userRepository.update(user);
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя для удаления
     */
    public void delete(Integer id) {
        userRepository.delete(id);
    }
}