package com.peters.myapp.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class SystemInstructionsLoader {
    public String loadSystemInstructions() {
        try {
            return Files.readString(Paths.get("src/main/resources/system-instruction.txt"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load system instructions", e);
        }
    }
}
