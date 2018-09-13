package demo.exceptions;

import demo.model.CustomResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    public static final String DEFAULT_ERROR = "error";

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    CustomResponseObject defaultHandleException(Exception ex, HttpServletRequest req) throws Exception {

        // default exception message
        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());

        System.out.println(" - - - - - - - - - - - - - - - - - - - ");
        System.out.println("Stack Trace for Error : ");
        System.out.println(ex.getStackTrace());

        response.setData("Exception Found : " +ex);
        response.setData("Bad URL : " +req);
        response.setError(error);
        response.setStatusCode(500);

        return response;
    }


    @ExceptionHandler({NullPointerException.class, Exception.class})
    public @ResponseBody
    CustomResponseObject handleExceptionMain(Exception ex, HttpServletRequest req) {

        // if null pointer exception is hit...
        if (ex instanceof NullPointerException) {
            NullPointerException exc = (NullPointerException) ex;
            return handleNoIDExist(exc);
        }

        // default message
        else {
            CustomResponseObject response = new CustomResponseObject();
            ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());

            System.out.println(" - - - - - - - - - - - - - - - - - - - ");
            System.out.println("Stack Trace for Error : ");
            System.out.println(ex.getStackTrace());

            response.setData("Default Exception - not yet handled.");
            response.setError(error);
            response.setStatusCode(500);

            return response;
        }
    }

    @ExceptionHandler(DatabaseException.class)
    public @ResponseBody
    CustomResponseObject handleDatabaseError(HttpServletRequest req, DatabaseException ex) {

        // default exception message
        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());

        System.out.println(" - - - - - - - - - - - - - - - - - - - ");
        System.out.println("Stack Trace for Error : ");
        System.out.println(ex.getStackTrace());

        response.setData("Exception Found : " +ex);
        response.setData("Bad URL : " +req);
        response.setError(error);
        response.setStatusCode(500);

        return response;
    }

    protected CustomResponseObject handleNoIDExist(Exception ex) {

        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());

        System.out.println(" - - - - - - - - - - - - - - - - - - - ");
        System.out.println("Stack Trace for ID Doesn't Exist Error : ");
        System.out.println(ex.getStackTrace());

        response.setData(null);
        response.setError(error);
        response.setError("ID doesn't exist. Please enter correct ID number or create new account.");
        response.setStatusCode(204);

        return response;
    }

    protected CustomResponseObject handleMethodNotSupported(HttpServletRequest req, Exception ex) {

        CustomResponseObject response = new CustomResponseObject();
        ExceptionsPojo error = new ExceptionsPojo(ex.getMessage());

        System.out.println(" - - - - - - - - - - - - - - - - - - - ");
        System.out.println("Stack Trace for Method Not Supported Error : ");
        System.out.println(ex.getStackTrace());

        response.setData("URL : Bad HTML Request");
        response.setError(error);
        response.setError(405);

        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GlobalControllerExceptionHandler.class)
    @ResponseBody ErrorInfo handleBadRequest(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURL(), ex);
    }

}
