package demo.exceptions;

/**
 * Created by ashleyalmeida
 */

public class DatabaseException extends Exception {

    /**
     * Constructor that returns custom database message
     *
     * @param message returns custom message
     */
    public DatabaseException(String message) {
        super(message);
    }
}
