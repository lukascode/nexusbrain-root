package com.nexusbrain.app.api.error;

import com.nexusbrain.app.config.AppConstants;
import com.nexusbrain.app.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String API_ERROR = "Api error";

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException ex) {
        ApiErrorDetails errorDetails = ex.getErrorDetails();
        return handleAndBuildResponse(ex, errorDetails, errorDetails.getStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleInternalServerError(Exception ex) {
        ApiException apiException = ApiException.internalServerError(ex);
        ApiErrorDetails errorDetails = apiException.getErrorDetails();
        return handleAndBuildResponse(apiException, errorDetails, errorDetails.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < ex.getBindingResult().getFieldErrors().size(); ++i) {
            FieldError error = ex.getBindingResult().getFieldErrors().get(i);
            if (i > 0) {
                message.append(", ");
            }
            message.append(error.getField() + ": " + error.getDefaultMessage());
        }
        for (int i = 0; i < ex.getBindingResult().getGlobalErrors().size(); ++i) {
            ObjectError error = ex.getBindingResult().getGlobalErrors().get(i);
            if (i > 0) {
                message.append(", ");
            }
            message.append(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), message.toString(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
        ApiErrorDetails errorDetails = new ApiErrorDetails(status.name(), ex.getMessage(), status);
        return handleAndBuildResponse(ex, errorDetails, status);
    }

    private ResponseEntity<Object> handleAndBuildResponse(Exception ex, ApiErrorDetails errorDetails, HttpStatus status) {
        LOG.error(API_ERROR, ex);
        errorDetails.setEventId(MDC.get(AppConstants.EVENT_ID));
        return new ResponseEntity<>(errorDetails, status);
    }
}
