package demo.exceptions;

import demo.model.CustomResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandling {


    @ExceptionHandler({Exception.class, NullPointerException.class})
    public @ResponseBody
    CustomResponseObject handleException(Exception ex) {

        // if null pointer exception is hit...
        if (ex instanceof NullPointerException) {
            NullPointerException exc = (NullPointerException) ex;
            return handleNoIDExist(exc);
        }

        // default message
        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());
        response.setError(error);
        response.setStatusCode(400);

        return response;
    }

    @ExceptionHandler(DatabaseException.class)
    public @ResponseBody
    CustomResponseObject handleDatabaseError(HttpServletRequest req, DatabaseException ex) {

        // default message
        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());
        response.setError(error);
        response.setStatusCode(400);

        return response;
    }

    protected CustomResponseObject handleNoIDExist(Exception ex) {

        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());

        response.setData(error);
        response.setData(null);
        response.setError("ID doesn't exist. Please enter correct ID number or create new account.");
        response.setStatusCode(204);

        return response;
    }

}
