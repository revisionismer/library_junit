package com.library.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.library.util.Script;
import com.library.web.dto.CMRespDto;
import com.library.web.handler.exception.CustomValidationException;
import com.library.web.handler.exception.api.CustomValidationApiException;

@RestControllerAdvice  // 1-1. RestAPI + ControllerAdvice -> 둘다 사용 가능. 데이터를 반환하고 싶으면 데이터를 반환해도 되고 스크립트를 반환하고 싶으면 스크립트를 반환해주면됨.
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomValidationApiException.class) 
 	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
 		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);

 	}
	
	@ExceptionHandler(CustomValidationException.class) 
 	public String validationScriptException(CustomValidationException e) {
		return Script.back(e.getMessage());
 	}
}
