package dev.imrob.carrental.exceptions.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomError {
    private int status;
    private String message;
    private String timestamp;
    private String path;
}
