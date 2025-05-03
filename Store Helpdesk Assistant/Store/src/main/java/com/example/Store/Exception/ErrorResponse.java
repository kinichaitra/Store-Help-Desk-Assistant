package com.example.Store.Exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String message) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
    }

    /**
     * Used only for input validation errors
     */

//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private List<String> fieldErrors;
}
