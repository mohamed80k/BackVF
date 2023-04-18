package com.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.exception.ExceptionResponse;
import com.exception.ResourceAlreadyExistException;
import com.exception.ResourceConflictException;
import com.exception.ResourceNotFoundException;
import com.util.Utils;

@ControllerAdvice
public class ExceptionHandlingController {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> ResourceNotFoundException(ResourceNotFoundException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Introuvable");
		response.setErrorMessage(ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<ExceptionResponse> ResourceAlreadyExistException(ResourceAlreadyExistException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Existe déjà");
		response.setErrorMessage(ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.FOUND);
	}

	@ExceptionHandler(ResourceConflictException.class)
	public ResponseEntity<ExceptionResponse> ResourceConflictException(ResourceConflictException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Conflit");
		response.setErrorMessage(ex.getMessage());

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionResponse> MethodArgumentNotValid(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Erreur de validation");
		response.setErrorMessage("Entrées non valides !");
		response.setErrorDetails(Utils.fromBindingErrors(result));

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionResponse> ResourceNotReadable(HttpMessageNotReadableException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Illisible");
		response.setErrorMessage("Les données saisies ne sont pas compatibles !");

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ExceptionResponse> MethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Type incompatible");
		response.setErrorMessage(ex.getMessage());
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> InternalServerError(Exception ex) {
		ex.printStackTrace();
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("Erreur");
		response.setErrorMessage("Veuillez contacter votre administrateur !");
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
