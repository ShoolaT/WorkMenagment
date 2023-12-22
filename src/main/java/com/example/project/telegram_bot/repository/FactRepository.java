package com.example.project.telegram_bot.repository;

import com.example.project.telegram_bot.entity.Fact;
import org.springframework.data.repository.CrudRepository;

public interface FactRepository extends CrudRepository<Fact, Long> {
}
