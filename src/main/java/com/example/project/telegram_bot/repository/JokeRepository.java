package com.example.project.telegram_bot.repository;

import com.example.project.telegram_bot.entity.Joke;
import org.springframework.data.repository.CrudRepository;

public interface JokeRepository extends CrudRepository<Joke, Integer> {

}