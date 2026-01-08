/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.infrastructure.web.controller;

import ch.admin.bj.swiyu.registry.status.authoring.api.ApiErrorDto;
import ch.admin.bj.swiyu.registry.status.authoring.common.exception.ResourceNotFoundException;
import ch.admin.bj.swiyu.registry.status.authoring.common.exception.ResourceNotReadyException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(
        final ResourceNotFoundException exception,
        final WebRequest request
    ) {
        final ApiErrorDto apiError = new ApiErrorDto(HttpStatus.NOT_FOUND, exception.getMessage());

        return new ResponseEntity<>(apiError, apiError.status());
    }

    @ExceptionHandler(ResourceNotReadyException.class)
    protected ResponseEntity<Object> handleResourceNotReadyException(
        final ResourceNotReadyException exception,
        final WebRequest request
    ) {
        final ApiErrorDto apiError = new ApiErrorDto(HttpStatus.TOO_EARLY, exception.getMessage());

        return new ResponseEntity<>(apiError, apiError.status());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(final Exception exception, final WebRequest request) {
        final ApiErrorDto apiError = new ApiErrorDto(HttpStatus.INTERNAL_SERVER_ERROR);
        this.logger.error("Detected unhandled exception.", exception);
        return new ResponseEntity<>(apiError, apiError.status());
    }
}
