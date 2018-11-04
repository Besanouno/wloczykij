package pl.basistam.wloczykij.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.basistam.wloczykij.json.ErrorsJson;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CredentialsTakenException.class)
    public ErrorsJson handleCredentialsTakenException(CredentialsTakenException e) {
        return new ErrorsJson(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErrorsJson handleUserNotFoundException(BadCredentialsException e) {
        return new ErrorsJson(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorsJson handleBindException(BindException e) {
        return new ErrorsJson(e.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorsJson handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorsJson(e.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorsJson handleUserNotFoundException(ResourceNotFoundException e) {
        return new ErrorsJson(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UsersRelationException.class)
    public ErrorsJson handleUsersRelationException(UsersRelationException e) {
        return new ErrorsJson(e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EventAccessException.class)
    public ErrorsJson handleEventAccessException(EventAccessException e) {
        return new ErrorsJson(e.getMessage());
    }


}


