package com.peters.myapp.dtos;

import com.peters.myapp.models.User;

import java.io.Serializable;

public record AddUserRequest(User user) implements Serializable {
}
