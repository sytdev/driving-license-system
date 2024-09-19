package org.licesys.common.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private Integer httpCode;
    private String httpError;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(Integer httpCode, String httpError, String message){
        this.httpCode = httpCode;
        this.httpError = httpError;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}
