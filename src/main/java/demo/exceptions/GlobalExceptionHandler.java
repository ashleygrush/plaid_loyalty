package demo.exceptions;


import demo.model.CustomResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;


@ControllerAdvice
public class GlobalExceptionHandler {

    /** Provides handling for exceptions throughout this service. */
    @ExceptionHandler({NullPointerException.class, Exception.class})
    @ResponseBody CustomResponseObject handleException(Exception ex, HttpServletRequest req) {

        // null ID number
        if (ex instanceof NullPointerException) {
            return handleNullPointerException(ex);
        }

        else if (ex instanceof InvocationTargetException) {
            return handleInvocationTargetException(ex);
        }

        else {

            CustomResponseObject response = new CustomResponseObject();
            ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());

            response.setData("Default Exception Handling. FIX ME! ");
            response.setError("HTTP Info : " + req);
            response.setError("Exception Info : " + ex);
            response.setError(error);
            response.setStatusCode(500);

            return response;
        }
    }

    private CustomResponseObject handleInvocationTargetException(Exception ex) {

        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());

        response.setData("ID does not exist. Please enter correct ID or create new account.");
        response.setError("Invocation Target Exception : " +error);
        response.setStatusCode(204);

        return response;

    }

    /** Customize the response for ID not found exception. */
    // re-route to create user/merchant
    protected CustomResponseObject handleNullPointerException(Exception ex) {

        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());

        response.setData("ID does not exist. Please enter correct ID or create new account.");
        response.setError("Null Pointer Exception : " +error);
        response.setStatusCode(204);

        return response;
    }


}
