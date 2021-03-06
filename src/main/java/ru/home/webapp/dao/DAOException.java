package ru.home.webapp.dao;

/**
 * This class implements the exception logic on Data layer
 *
 * @author Evgeniy Presnov
 */
public class DAOException extends Exception {
    private static final long serialVersionUID = 1L;

    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

}
