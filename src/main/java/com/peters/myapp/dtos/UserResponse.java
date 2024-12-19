package com.peters.myapp.dtos;

import com.peters.myapp.models.User;

import java.io.Serializable;
import java.util.List;


public record UserResponse(List<User> user) implements Serializable {
}
