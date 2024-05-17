package dev.imrob.carrental.exceptions.handler;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {
    private int status;
    private String message;
    private String timestamp;
    private String path;
}
