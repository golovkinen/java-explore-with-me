package ru.practicum.explore.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.practicum.explore.apierror.ApiError;
import ru.practicum.explore.datetimeformatter.DateTimeFormatter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";

        ApiError apiError =
                new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "Запрос составлен с ошибкой", HttpStatus.BAD_REQUEST.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ApiError apiError =
                new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "Запрос составлен с ошибкой", HttpStatus.BAD_REQUEST.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));

        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(
            Exception ex) {
        log.info("404 {}", ex.getMessage(), ex);
        return new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "The required object was not found.", HttpStatus.NOT_FOUND.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));
    }

    @ExceptionHandler({BadRequestException.class})
    public ApiError handleBadRequestException(
            Exception ex) {
        log.info("400 {}", ex.getMessage(), ex);
        return new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "Запрос составлен с ошибкой", HttpStatus.BAD_REQUEST.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));
    }

    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbiddenException(
            Exception ex) {
        log.info("403 {}", ex.getMessage(), ex);
        return new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "Не выполнены условия для совершения операции", HttpStatus.FORBIDDEN.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));
    }

    @ExceptionHandler({WrongStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleWrongStateException(
            Exception ex) {
        log.info("400 {}", ex.getMessage(), ex);
        return new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "Запрос составлен с ошибкой", HttpStatus.BAD_REQUEST.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));
    }

    @ExceptionHandler({ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(
            Exception ex) {
        log.info("409 {}", ex.getMessage(), ex);
        return new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "Integrity constraint has been violated", HttpStatus.CONFLICT.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));
    }

    @ExceptionHandler({org.hibernate.exception.ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleSQLConflictException(
            Exception ex) {
        log.info("409 {}", ex.getMessage(), ex);
        return new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "Integrity constraint has been violated", HttpStatus.CONFLICT.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));
    }

    @ExceptionHandler({NumberFormatException.class})
    public ApiError handleNumberFormatException(
            NumberFormatException ex, WebRequest request) {

        log.info("400 {}", ex.getMessage(), ex);
        return new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "Запрос составлен с ошибкой", HttpStatus.BAD_REQUEST.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError =
                new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), String.join(", ", errors), HttpStatus.BAD_REQUEST.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(
            Exception ex) {
        log.info("409 {}", ex.getMessage(), ex);
        return new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "Integrity constraint has been violated", HttpStatus.CONFLICT.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(
            Exception ex) {
        log.info("500 {}", ex.getMessage(), ex);
        return new ApiError(Collections.singletonList(ExceptionUtils.getStackTrace(ex)), ex.getLocalizedMessage(), "Error occurred", HttpStatus.INTERNAL_SERVER_ERROR.toString(), DateTimeFormatter.dateTimeToString(LocalDateTime.now()));
    }
}
