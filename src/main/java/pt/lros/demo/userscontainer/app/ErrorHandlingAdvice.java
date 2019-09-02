/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.lros.demo.userscontainer.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.lros.demo.userscontainer.errors.ValidationException;

/**
 *
 * @author risca
 */
@ControllerAdvice
public class ErrorHandlingAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class})
    protected ResponseEntity<BadRequestValidationResponse> handleValidationException(ValidationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(new BadRequestValidationResponse(ex.getErrors()));
    }

}
