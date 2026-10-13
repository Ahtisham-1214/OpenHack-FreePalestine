package com.example.openhackFreePalestine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorPayload = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                ex.getClassName()
        );
        return new ResponseEntity<>(errorPayload, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(MaxPartyMemberException.class)
//    public ResponseEntity<ErrorResponse> handleMaxPartyMemberException(MaxPartyMemberException ex) {
//        ErrorResponse errorPayload = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                "Not Allowed",
//                ex.getMessage(),
//                ex.getClassName()
//        );
//        return new ResponseEntity<>(errorPayload, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(VoteTimeException.class)
//    public ResponseEntity<ErrorResponse> handleVoteTimeException(VoteTimeException ex) {
//        ErrorResponse errorPayload = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                "Not Allowed",
//                ex.getMessage(),
//                ex.getClassName()
//        );
//        return new ResponseEntity<>(errorPayload, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(IllegalEnumException.class)
//    public ResponseEntity<ErrorResponse> handleEnumNotFoundException(IllegalEnumException ex) {
//        ErrorResponse errorPayload = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                "Bad Argument",
//                ex.getMessage(),
//                ex.getClassName()
//        );
//        return new ResponseEntity<>(errorPayload, HttpStatus.BAD_REQUEST);
//    }
//
//
//    @ExceptionHandler(CSVFileException.class)
//    public ResponseEntity<ErrorResponse> CSVFileException(CSVFileException ex) {
//        ErrorResponse errorPayload = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                "Bad Argument",
//                ex.getMessage(),
//                ex.getClassName()
//        );
//        return new ResponseEntity<>(errorPayload, HttpStatus.BAD_REQUEST);
//    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid request parameter.";

        // Check if the mismatched parameter is an Enum
        if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            String enumName = ex.getRequiredType().getSimpleName();
            String allowedValues = java.util.Arrays.toString(ex.getRequiredType().getEnumConstants());

            message = String.format("Invalid value '%s' for %s. Allowed values are: %s",
                    ex.getValue(), enumName, allowedValues);
        }

        ErrorResponse errorPayload = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                message,
                ex.getRequiredType().getSimpleName()
        );

        return new ResponseEntity<>(errorPayload, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Invalid input request");

        ErrorResponse errorPayload = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), // "Bad Request"
                errorMessage,                            // e.g. "No special character or number allowed"
                ex.getClass().getSimpleName()            // "MethodArgumentNotValidException"
        );

        return new ResponseEntity<>(errorPayload, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorPayload = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Argument",
                ex.getMessage()

        );

        return new ResponseEntity<>(errorPayload, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        ErrorResponse errorPayload = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                ex.getMessage()
        );

        return new ResponseEntity<>(errorPayload, HttpStatus.BAD_REQUEST);
    }

//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
//        String errorMessage = ex.getConstraintViolations()
//                .stream()
//                .map(ConstraintViolation::getMessage)
//                .findFirst()
//                .orElse("Invalid request parameter");
//
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                HttpStatus.BAD_REQUEST.getReasonPhrase(), // "Bad Request"
//                errorMessage,                         // Exactly "Invalid Sap Id"
//                ex.getClass().getSimpleName()
//        );
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
//
//    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonErrors(Exception e) {
        System.err.println("JSON Deserialization Error: " + e.getMessage());
        return ResponseEntity.badRequest().body("Invalid JSON payload: " + e.getMessage());
    }
}
