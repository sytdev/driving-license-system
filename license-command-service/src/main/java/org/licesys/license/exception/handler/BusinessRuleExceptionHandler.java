package org.licesys.license.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.licesys.common.model.ErrorResponse;
import org.licesys.license.exception.BusinessRuleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class BusinessRuleExceptionHandler {

    @ExceptionHandler(BusinessRuleException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleBusinessRuleException(final BusinessRuleException e) {

        log.error("[error.business-violation]", e);

        ErrorResponse bodyResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), e.getMessage());

        return new ResponseEntity<ErrorResponse>(bodyResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
