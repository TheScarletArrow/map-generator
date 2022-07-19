package ru.scarletarrow.diplomv1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({
            BadRequestException.class,
            NotAuthorizedException.class})
    ResponseEntity<ErrorResponse> parse1(Exception e) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(badRequest)
                .body(
                        new ErrorResponse(
                                LocalDateTime.now(),
                                badRequest.value(),
                                badRequest.getReasonPhrase(),
                                e.getMessage()
                        )
                );
    }

    @ExceptionHandler({UserNotFoundException.class,
            NotFoundException.class,})
    ResponseEntity<ErrorResponse> parse2(Exception e) {
        final HttpStatus notFound = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(notFound)
                .body(
                        new ErrorResponse(
                                LocalDateTime.now(),
                                notFound.value(),
                                notFound.getReasonPhrase(),
                                e.getMessage()
                        )
                );
    }
}
