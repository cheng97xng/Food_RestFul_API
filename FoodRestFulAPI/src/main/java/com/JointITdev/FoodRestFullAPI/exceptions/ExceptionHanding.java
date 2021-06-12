package com.JointITdev.FoodRestFullAPI.exceptions;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.JointITdev.FoodRestFullAPI.domain.HttpResponse;
import com.JointITdev.FoodRestFullAPI.exceptions.domain.UsernameExistException;
//import com.auth0.jwt.exceptions.TokenExpiredException;
import com.JointITdev.FoodRestFullAPI.exceptions.domain.UsernameNotFoundException;

@RestControllerAdvice
public class ExceptionHanding implements ErrorController{

	private Logger LOGGER=LoggerFactory.getLogger(getClass());
	private static final String ACCOUNT_LOCKED="Your account has been locked. Please contact administration";
	private static final String METHOD_IS_NOT_ALLOWED="This  request method is not allowed on this endpoint. Please send a '%s' request";
	private static final String INTERNAL_SERVER_ERROR_MSG="An error occurred while processing the request";
	private static final String INCORRECT_CREDENTIALS="Username / Password incorrect. Please try again";
	private static final String ACCOUNT_DISABLED="Your account has been disabled. If this is an error, please contact administration";
	private static final String ERROR_PROCESSING_FILE="Error occurred while processing file";
	private static final String NOT_ENOUGH_PERMISSION="You do not have enough permission";
	private static final String ERROR_PATH="/error";
	
	
//	/*Exception the Disabled Exception*/
//	@ExceptionHandler(DisabledException.class)
//	public ResponseEntity<HttpResponse> AccountDisabledException(){
//		return createHttpResponse(HttpStatus.BAD_REQUEST, ACCOUNT_DISABLED);
//	}
//	
//	/*Exception the Bad Credentials Exception*/
//	@ExceptionHandler(BadCredentialsException.class)
//	public ResponseEntity<HttpResponse> badCredentialsException(){
//		return createHttpResponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIALS);
//	}
//	
//	/*Exception the Access Denied Exception*/
//	@ExceptionHandler(AccessDeniedException.class)
//	public ResponseEntity<HttpResponse> accessDeniedException(){
//		return createHttpResponse(HttpStatus.FORBIDDEN, NOT_ENOUGH_PERMISSION);
//	}
//	
//	/*Exception the user locked*/
//	@ExceptionHandler(LockedException.class)
//	public ResponseEntity<HttpResponse> lockedException(){
//		return createHttpResponse(HttpStatus.UNAUTHORIZED, ACCOUNT_LOCKED);
//	}
//	
//	/*Exception the token expired*/
//	@ExceptionHandler(TokenExpiredException.class)
//	public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception){
//		return createHttpResponse(HttpStatus.UNAUTHORIZED, exception.getMessage());
//	}
	
	/*Exception the username exist*/
	@ExceptionHandler(UsernameExistException.class)
	public ResponseEntity<HttpResponse> usernameExistException(UsernameExistException exception){
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	/*Exception the username not found*/
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<HttpResponse> usernameNotFoundException(UsernameNotFoundException exception){
		return createHttpResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
	}
	
	/*Exception of Http Request Method Not Supported Exception*/
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception){
		HttpMethod supportedMethod=Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
		return createHttpResponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
	}
	
	/*Exception of Exception*/
	@ExceptionHandler(Exception.class)
	public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception){
		LOGGER.error(exception.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
	}
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<HttpResponse> notFoundException(NoResultException exception){
		LOGGER.error(exception.getMessage());
		return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
	}
	
	/*Exception of IOException*/
	@ExceptionHandler(IOException.class)
	public ResponseEntity<HttpResponse> ioException(IOException exception){
		LOGGER.error(exception.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
	}

	/*HttpResponse*/
	private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus,String message){
		HttpResponse httpResponse=new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase()
				.toUpperCase(),message.toUpperCase());
		return new ResponseEntity<>(httpResponse,httpStatus);
		
	}
	
	/*@RequestMapping notFound404*/
	@RequestMapping(ERROR_PATH)
	public ResponseEntity<HttpResponse> notFound404(){
		return createHttpResponse(HttpStatus.NOT_FOUND, "There is no mapping for this URL.");
	}

	@Override
	public String getErrorPath() {
		return null;
	}

}
