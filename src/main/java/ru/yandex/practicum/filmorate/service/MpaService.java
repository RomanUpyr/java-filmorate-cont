package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.repository.MpaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaRepository mpaRepository;

    public List<Mpa> findAll() {
        return mpaRepository.findAll();
    }

    public Mpa findById(Integer id) {
        return mpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MPA rating with id " + id + " not found. Available ratings: " + getAllRatings()));
    }

    private String getAllRatings() {
        return mpaRepository.findAll().stream()
                .map(m -> m.getId() + ":" + m.getCode())
                .collect(Collectors.joining(", "));
    }
}
