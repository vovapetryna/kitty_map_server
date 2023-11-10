package com.example.numo.configs;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class KittyExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> process(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.error("Validation Error in request {}", request.getRequestURL());
        BindingResult result = ex.getBindingResult();
        Map<String, Object> validationErrors = processFieldErrors(result.getFieldErrors()
                .stream()
                .distinct()
                .toList());
        validationErrors.putAll(processFieldErrors(result.getGlobalErrors()
                .stream()
                .map(e -> {
                    FieldError fieldError;
                    fieldError = new FieldError(e.getObjectName(), Objects.requireNonNull(e
                            .getArguments())[2].toString(), Objects.requireNonNull(e.getDefaultMessage()));
                    return fieldError;
                })
                .distinct()
                .toList()));
        log.error("Validation Errors: {}", validationErrors);
        return validationErrors;
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationException process(IllegalArgumentException ex, HttpServletRequest request) {
        log.error("IllegalArgument Exception in request {} with error {}", request.getRequestURL(), ex.getMessage());
        log.error("", ex);
        return new ValidationException(ex.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationException process(InvalidFormatException ex, HttpServletRequest request) {
        log.error("Invalid Format Exception in request {} with error {}", request.getRequestURL(), ex.getMessage());
        return new ValidationException(ex.getMessage());
    }


    private Map<String, Object> processFieldErrors(List<FieldError> fieldErrors) {
        Map<String, Object> root = new HashMap<>();

        for (FieldError fieldError : fieldErrors) {
            Map<String, Object> current = root;
            List<String> paths = getPathListFromFieldPath(fieldError.getField());

            for (int i = 0; i < paths.size(); i++) {
                if (i == paths.size() - 1) {
                    current.put(paths.get(i), resolveValidationError(fieldError).getValidationErrorInfo());
                } else {
                    if (!current.containsKey(paths.get(i))) {
                        current.put(paths.get(i), new HashMap<>());
                    }
                    current = (Map<String, Object>) current.get(paths.get(i));
                }
            }
        }
        return root;
    }

    private ValidationException resolveValidationError(FieldError fieldError) {
        return new ValidationException(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
    }

    private List<String> getPathListFromFieldPath(String path) {
        String[] fieldPath = path.split("\\.");
        List<String> paths = new ArrayList<>();
        for (String s : fieldPath) {
            if (s.contains("[")) {
                String[] temp = s.split("\\[");
                paths.add(temp[0]);
                paths.add(temp[1].replace("]", ""));
            } else {
                paths.add(s);
            }
        }
        return paths;
    }
}
