package com.example.project.telegram_bot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;


@Entity(name="usersDataTable")
@Data
public class User {
    @Id
    private Long chatId;

    private Boolean embedeJoke;

    private String phoneNumber;

    private Timestamp registeredAt;

    private String firstName;

    private String lastName;

    private String userName;

    private Double latitude;

    private Double longitude;

    private String bio;

    private String description;

    private String pinnedMessage;

    private String inputName;

    private String email;

}
