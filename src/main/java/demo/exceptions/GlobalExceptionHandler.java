package demo.exceptions;

import demo.model.CustomResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

    /** Provides handling for exceptions throughout this service. */
    @ExceptionHandler(Exception.class)
    @ResponseBody CustomResponseObject handleException(Exception ex, HttpServletRequest req) {
            return defaultErrorMessage(ex);
    }

    // Default Exception Handling Message
    protected CustomResponseObject defaultErrorMessage(Exception ex) {

        CustomResponseObject response = new CustomResponseObject();

        response.setData("Default Error Message.");
        response.setMessage(ex.getLocalizedMessage());
        response.setStatusCode(500);

        System.out.println(" - - - - - - - - - - - - ");
        System.out.println("Print Exception: ");
        System.out.println(ex.getMessage());

        return response;
    }
}
