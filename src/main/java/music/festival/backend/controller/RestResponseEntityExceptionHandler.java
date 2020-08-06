package music.festival.backend.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import music.festival.backend.exception.BackendDataException;

@ControllerAdvice
@RestController
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { BackendDataException.class })
	public ResponseEntity<Object> handleException(RuntimeException exception, WebRequest request) {
		logger.error(exception);
		return handleExceptionInternal(exception, exception.getLocalizedMessage(), new HttpHeaders(),
				HttpStatus.NOT_FOUND, request);
	}
}
