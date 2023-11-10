package com.example.numo.configs;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIncludeProperties({"field", "validationErrorInfo"})
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 8840966802614690464L;

    @JsonProperty
    private String field;

    @JsonProperty
    private ValidationErrorInfo validationErrorInfo;


    public ValidationException(String message) {
        super(message);
        this.validationErrorInfo = new ValidationErrorInfo(message);
    }

    public ValidationException(String field, String message, Object... objects) {
        this.field = field;
        this.validationErrorInfo = new ValidationErrorInfo(String.format(message, objects));
    }

    public ValidationException(String field, String message, Object rejectedValue) {
        this.field = field;
        this.validationErrorInfo = new ValidationErrorInfo(String.format(message, rejectedValue), rejectedValue);
    }

    @Override
    public String toString() {
        return String.format("Field %s: message: %s, rejected value: %s", field, validationErrorInfo.getMessage(), validationErrorInfo.getRejectedValue());
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIncludeProperties({"message", "rejectedValue"})
    public class ValidationErrorInfo implements Serializable {
        @JsonProperty
        private String message;
        @JsonProperty
        private Object rejectedValue;

        public ValidationErrorInfo(String message) {
            this.message = message;
        }


        @Override
        public String toString() {
            return "ValidationErrorInfo{" +
                "message='" + message + '\'' +
                ", rejectedValue=" + rejectedValue +
                '}';
        }
    }

}