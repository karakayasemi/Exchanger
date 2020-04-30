package com.openpayd.exchanger.advice;

import com.openpayd.exchanger.http.ErrorResponse;
import com.sun.jdi.request.InvalidRequestStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Advice for argument errors
     *
     * @param e caught exception
     * @return ErrorResponse with exception message with related HTTP status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentExceptions(IllegalArgumentException e) {
        logger.error("Illegal request arguments: ", e);
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Advice for the exceptions
     *
     * @param e caught exception
     * @return ErrorResponse with exception message with related HTTP status
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleExceptions(BindException e) {
        logger.error("Unexpected error occurred: ", e);
        StringBuilder errorMessages = new StringBuilder();
        for (ObjectError error : e.getAllErrors()) {
            errorMessages.append(error.getDefaultMessage()).append(System.getProperty("line.separator"));
        }
        return new ErrorResponse(errorMessages.toString());
    }

    /**
     * Advice for rest client exceptions
     *
     * @param e caught exception
     * @return ErrorResponse with exception message with related HTTP status
     */
    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRestClientExceptions(RestClientException e) {
        logger.error("RestClient threw exception: ", e);
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Advice for invalid request state exceptions
     *
     * @param e caught exception
     * @return ErrorResponse with exception message with related HTTP status
     */
    @ExceptionHandler(InvalidRequestStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInvalidRequestStateExceptions(InvalidRequestStateException e) {
        logger.error("A request resulted in an unexpected state: ", e);
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Advice for the exceptions
     *
     * @param e caught exception
     * @return ErrorResponse with exception message with related HTTP status
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleExceptions(Exception e) {
        logger.error("Unexpected error occurred: ", e);
        return new ErrorResponse(e.getMessage());
    }
}
