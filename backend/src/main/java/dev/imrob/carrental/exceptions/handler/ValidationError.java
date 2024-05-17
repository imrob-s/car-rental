package dev.imrob.carrental.exceptions.handler;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError {
    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String message, String timestamp, String path) {
        super(status, message, timestamp, path);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));
    }
}
