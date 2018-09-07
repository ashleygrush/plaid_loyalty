package demo.exceptions;

import demo.model.CustomResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    CustomResponseObject handleException(HttpServletRequest req, Exception ex) {

        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());
        response.setError(error);
        response.setStatusCode(400);

        return response;
    }
    @ExceptionHandler(DatabaseException.class)
    public @ResponseBody
    CustomResponseObject handleDatabaseError(HttpServletRequest req, DatabaseException ex) {

        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());
        response.setError(error);
        response.setStatusCode(400);

        return response;
    }
}