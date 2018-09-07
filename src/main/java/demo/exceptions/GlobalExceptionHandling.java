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

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    CustomResponseObject handleBadHTMLRequest(HttpServletRequest req, DatabaseException ex) {

        CustomResponseObject response = new CustomResponseObject();
        response.setError("Invalid HTML Entered.  Please use either of the following menu: " +
                "                                       " +
                " - - - - - - - PLAID API MAIN MENU - - - - - - - - - - " +
                "                                                       " +
                "   /users - user information " +
                "   /merchants - merchant information " +
                "   /loyalty_points - loyalty points ** user_id required ** " +
                "   /deals - deal information ## merchant_id recommended ** " +
                "                                       " +
                "                                       " +
                " USER MENU - /users - - - - - - - - - - - - - - - - - - " +
                "                                        " +
                "   /all - list all information " +
                "   /id={id} - enter ID number " +
                "   /user_id={user_id}/rewards - check for rewards" +
                "   /update/transactions - updates transactions " +
                "                                       " +
                " MERCHANT MENU - /merchants - - - - - - - - - - - - - - " +
                "                                       " +
                "   /all - list all merchants             " +
                "   /user_id={user_id} - enter user ID  " +
                "   /merchant_id={merchant_id} - merchant ID " +
                "                                           " +
                "                                           " +
                " DEALS MENU - /deals - - - - - - - - - - - - - - " +
                "                                       " +
                "   /all - list all merchants             " +
                "   /id={id} - enter ID number  " +
                "   /merchant_id={merchant_id} - merchant ID " +
                "   /merchant_id={merchant_id}/id={id} - update by merchant ID and deal ID " +
                "                                               " +
                " LOYALTY MENU - /loyalty_points - - - - - - - - - - - - - - " +
                "                                       " +
                "   /all - list all merchants             " +
                "   /user_id={user_id}/rewards - check for rewards  " +
                "   /user_id={user_id}/available_rewards - check active rewards " +
                "                                                   " +
                "                                                    " +
                " - - - - - - - - - - - - FIN - - - - - - - - - - - ");
        response.setStatusCode(404);

        return response;
    }

}
