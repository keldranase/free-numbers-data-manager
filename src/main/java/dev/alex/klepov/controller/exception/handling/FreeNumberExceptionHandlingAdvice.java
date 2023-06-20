package dev.alex.klepov.controller.exception.handling;

import java.sql.SQLException;
import java.util.Arrays;

import dev.alex.klepov.client.exception.OnlinesimClientException;
import dev.alex.klepov.controller.exception.BadParamException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


// Is better than ExceptionHandler, bc it's global, so you can have a centralized strategy for exception handling
// But you can also configure it to work on specific scopes
//
// Exception handling as it done in this file doesn't really make much sense,
// because none of the exceptions carry any special info, and neither of them is handled in its own specific way
// (except HTTP statuses)
// These are done only to fulfil the requirement of exception handling in assignment description
@ControllerAdvice
public class FreeNumberExceptionHandlingAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleDefault(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Something went wrong ¯\\_( ͡° ͜ʖ ͡°)_/¯"
                        + e.getMessage()
                        + Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler
    public ResponseEntity<String> handleOnlinesimClientException(OnlinesimClientException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error during executing request to onlinesim API: " + e.getMessage());
    }

    // it might (or might not) be better to process db level exceptions on other level
    @ExceptionHandler({
            DataAccessException.class,
            InvalidResultSetAccessException.class,
            SQLException.class})
    public ResponseEntity<String> handleDbLevelException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error encountered while making request to persistence storage: "
                        + e.getMessage()
                        + Arrays.toString(e.getStackTrace()));
    }

    @ExceptionHandler()
    public ResponseEntity<String> handleBadParamException(BadParamException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Bad parameter found: " + e.getMessage());
    }
}
