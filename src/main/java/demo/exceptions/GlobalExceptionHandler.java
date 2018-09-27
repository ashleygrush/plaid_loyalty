package demo.exceptions;

/**
 * Created by ashleyalmeida
 */

import demo.model.CustomResponseObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Provides custom exception handling responses
     *
     * @param ex Exception parameter
     * @param req HTTP parameter
     * @return method below to handle exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody CustomResponseObject handleException(Exception ex, HttpServletRequest req) {

        if (ex instanceof NullPointerException) {
            return handleNullPointer(ex);
        } else {
            return defaultErrorMessage(ex);
        }
    }

    /**
     * Custom exception handles Null Pointer Exception
     *
     * @param ex Exception parameter
     * @return String message to user on how to handle error and prints stacktrace to log
     */
    protected CustomResponseObject handleNullPointer(Exception ex) {

        CustomResponseObject response = new CustomResponseObject();

        response.setData("Null Pointer Exception");
        response.setMessage(ex.getLocalizedMessage());
        response.setStatusCode(200);

        System.out.println(" - - - - - - - - - - - - ");
        System.out.println("Print Exception: ");
        System.out.println(ex.getMessage());

        return response;
    }


    /**
     * Default Exception Handling Message
     *
     * @param ex Exception parameter
     * @return String message to user with default message and prints stacktrace to log
     */
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
