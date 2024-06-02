package com.Reader.Books.Exception;

import java.net.http.HttpHeaders;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.Reader.Books.source.ReadernotFoundException;


@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(com.Reader.Books.source.ReadernotFoundException.class)
	public final ResponseEntity<Object> handleReadernotfoundException(ReadernotFoundException ex,WebRequest wr){
		ExceptionResponse excepres=new ExceptionResponse(new Date(), ex.getMessage(), wr.getDescription(false));
	    return new ResponseEntity<Object>(excepres,HttpStatus.NOT_FOUND);
	}

	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), "Validation Failed",
				ex.getBindingResult().toString());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
