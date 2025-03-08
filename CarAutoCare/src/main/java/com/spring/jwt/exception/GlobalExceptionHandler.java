package com.spring.jwt.exception;


import com.spring.jwt.Appointment.ResponseDto;
import com.spring.jwt.VehicleReg.BadRequestException;
import com.spring.jwt.utils.BaseResponseDTO;
import com.spring.jwt.utils.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponseDTO> handleBaseException(BaseException e){
        BaseResponseDTO response = BaseResponseDTO.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(response, HttpStatus.valueOf(Integer.parseInt(e.getCode())));
    }

    @ExceptionHandler (PageNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePageNotFoundException(PageNotFoundException exception, WebRequest webRequest){
       ErrorResponseDto errorResponseDto = new ErrorResponseDto(
               webRequest.getDescription(false),
               HttpStatus.NOT_FOUND,
               exception.getMessage(),
               LocalDateTime.now()
       );
       return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmptyFieldException.class, UserAlreadyExistException.class})
    public ResponseEntity<ErrorResponseDto> handleCommonExceptions(RuntimeException exception, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidOtpException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidOtpException(InvalidOtpException exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OtpExpiredException.class)
    public ResponseEntity<ErrorResponseDto>handleOtpExpiredException(OtpExpiredException exception, WebRequest webRequest){
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(
            webRequest.getDescription(false),
            HttpStatus.BAD_REQUEST,
            exception.getMessage(),
            LocalDateTime.now()
    );
    return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotVerifiedException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailNotVerifiedException(EmailNotVerifiedException exception, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolation(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        violation -> violation.getMessage(),
                        (existingMessage, newMessage) -> existingMessage + "; " + newMessage
                ));
    }

    @ExceptionHandler(UserNotFoundExceptions.class)
    public ResponseEntity<ResponseDto> handleUserNotFoundException(UserNotFoundExceptions ex) {
        ResponseDto response = new ResponseDto();
        response.setMessage("Error");
        response.setException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto<Object>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDto.error("Unexpected error", ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResponseDto> handleUserNotFoundException(BadRequestException ex) {
        ResponseDto response = new ResponseDto();
        response.setMessage("Error");
        response.setException(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(SparePartNotFoundException.class)
    public ResponseEntity<BaseResponseDTO> handleSparePartNotFoundException(SparePartNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseResponseDTO.builder()
                        .code("NOT_FOUND")
                        .message(ex.getMessage())
                        .build());
    }

}
