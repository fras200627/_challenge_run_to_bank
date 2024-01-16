package com.challenge.authentication.handler.exception;

import com.challenge.authentication.handler.domain.BadResponseTemplate;
import com.challenge.authentication.handler.domain.GenericErrorResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.management.RuntimeErrorException;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class RestExceptionHandler {

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(BadJwtException.class)
    public ResponseEntity handleException(BadJwtException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity handleException(JwtException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(JwtValidationException.class)
    public ResponseEntity handleException(JwtValidationException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity handleException(OAuth2AuthenticationException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(InvalidBearerTokenException.class)
    public ResponseEntity handleException(InvalidBearerTokenException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleException(AccessDeniedException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(AuthorizationServiceException.class)
    public ResponseEntity handleException(AuthorizationServiceException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */
    
    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity handleException(UsernameNotFoundException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */
    
    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(LockedException.class)
    public ResponseEntity handleException(LockedException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity handleException(AccountExpiredException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity handleException(InternalAuthenticationServiceException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity handleException(AuthenticationCredentialsNotFoundException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity handleException(AccountStatusException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity handleException(BadCredentialsException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */
    
    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity handleException(CredentialsExpiredException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity handleException(DisabledException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */
    
    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity handleException(InsufficientAuthenticationException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */
    
    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity handleException(AuthenticationServiceException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */
    
    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleException(MethodArgumentTypeMismatchException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(MethodArgumentConversionNotSupportedException.class)
    public ResponseEntity handleException(MethodArgumentConversionNotSupportedException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleException(HttpMessageNotReadableException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity handleException(HttpMessageConversionException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity handleException(HttpMessageNotWritableException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity handleException(MissingPathVariableException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(MissingRequestValueException.class)
    public ResponseEntity handleException(MissingRequestValueException ex){
        String[] message = ex.getLocalizedMessage().split(":");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.name(),
                message[0]),
                HttpStatus.BAD_REQUEST);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleException(MethodArgumentNotValidException ex) {
        var errorList = ex.getFieldErrors();
        int random = (int)(Math.random() * 999999999 + 1);

        return ResponseEntity.badRequest().body(
                new BadResponseTemplate(
                        HttpStatus.BAD_REQUEST,
                        "The payload is invalid. Check the error(s) displayed in the 'message' field.",
                        errorList.stream().map(buildBobyResponse::new).toList()));
    }
    private record buildBobyResponse(String field, String message) {
        public buildBobyResponse(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity handleException(BadRequestException ex) {
        int random = (int)(Math.random() * 999999999 + 1);

        return ResponseEntity.badRequest().body(new BadResponseTemplate(
                HttpStatus.BAD_REQUEST,
                "The payload or request is invalid. Check the error(s) displayed in the 'message' field.",
                ex.getMessage()));
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity handleException(SQLException ex){
        String[] message = ex.getMessage().split(";");

        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.UNPROCESSABLE_ENTITY,
                HttpStatus.UNPROCESSABLE_ENTITY.name(),
                "SQL Error. Database Code return [" + ex.getErrorCode() + "] " +
                        "Error details [" + message[0] + "]"),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(ServletException.class)
    public ResponseEntity handleException(ServletException ex){
        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                ex.getLocalizedMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(IOException.class)
    public ResponseEntity handleException(IOException ex) {
        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                ex.getLocalizedMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /* ------------------------------------------------------------------------ */

    /* ------------------------------------------------------------------------ */
    @ExceptionHandler(RuntimeErrorException.class)
    public ResponseEntity handleException(RuntimeException ex) {
        return new ResponseEntity(new GenericErrorResponseTemplate(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                ex.getLocalizedMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /* ------------------------------------------------------------------------ */

}
