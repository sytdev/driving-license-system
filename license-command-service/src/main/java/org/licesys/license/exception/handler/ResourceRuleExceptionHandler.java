package org.licesys.license.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.licesys.common.model.ErrorResponse;
import org.licesys.license.exception.BusinessRuleException;
import org.licesys.license.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ResourceRuleExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundRuleException(final ResourceNotFoundException e) {

        log.error("[error.resource-violation]", e);

        ErrorResponse bodyResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());

        return new ResponseEntity<ErrorResponse>(bodyResponse, HttpStatus.NOT_FOUND);
    }
}
