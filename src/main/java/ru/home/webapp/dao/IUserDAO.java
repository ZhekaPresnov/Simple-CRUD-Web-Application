package ru.home.webapp.dao;

import ru.home.webapp.domain.User;
import ru.home.webapp.utils.ConnectionDBException;

import java.sql.SQLException;

/**
 * The interface defines an abstract API that performs CRUD operations
 *
 * @author Evgeniy Presnov
 */
public interface IUserDAO {
    void addUser(User user) throws DAOException, ConnectionDBException, SQLException;
    User getUser(String userName, String password) throws DAOException, ConnectionDBException, SQLException;
    void deleteUser(String userName) throws DAOException, ConnectionDBException, SQLException;
}
