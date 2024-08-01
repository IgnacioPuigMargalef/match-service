package com.footballscore.matchservice.Controller;

import com.footballscore.matchservice.Controller.Response.CustomErrorResponse;
import com.footballscore.matchservice.Exception.GettingMatchException;
import com.footballscore.matchservice.Exception.NotFoundMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    /* MATCH CODES */
    private static final String NOT_FOUND_MATCH_CODE = "NOT_FOUND_MATCH";
    private static final String ERROR_MATCH_CODE = "ERROR_MATCH";

    /** Match handler */
    @ExceptionHandler(GettingMatchException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorResponse handleGettingMatchException(GettingMatchException e) {
        return new CustomErrorResponse(ERROR_MATCH_CODE);
    }

    @ExceptionHandler(NotFoundMatchException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomErrorResponse handleNotFoundMatchException(NotFoundMatchException e) {
        return new CustomErrorResponse(NOT_FOUND_MATCH_CODE);
    }


}
