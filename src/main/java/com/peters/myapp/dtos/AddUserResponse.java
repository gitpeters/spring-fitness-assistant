package com.peters.myapp.dtos;

import com.peters.myapp.models.User;

import java.io.Serializable;

public record AddUserResponse(User user) implements Serializable {
}
