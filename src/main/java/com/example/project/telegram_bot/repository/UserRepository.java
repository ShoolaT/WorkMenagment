package com.example.project.telegram_bot.repository;

import com.example.project.telegram_bot.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
