package ru.home.webapp.servlets;


import org.apache.log4j.Logger;
import ru.home.webapp.dao.DAOException;
import ru.home.webapp.dao.IUserDAO;
import ru.home.webapp.dao.UserDAO;
import ru.home.webapp.domain.User;
import ru.home.webapp.utils.ConnectionDBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * This class implements the logic of user registration in the database
 *
 * @author Evgeniy Presnov
 */
@WebServlet("/registration")
public class Registration extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(Registration.class.getName());

    /*
     Display the page of registration
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(req, resp);
    }

    /*
     After the user has filled information about themselves and clicked on button.
     This method will be executed
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IUserDAO userDAO = new UserDAO();
        User user = new User();

        String userName = req.getParameter("name");
        String password = req.getParameter("password");
        String submitType = req.getParameter("submit");
        String repeatPassword = req.getParameter("repeatPassword");

        /*
         Checking for filling in the fields on the form.

         If all fields are not empty
         */
        if (submitType.equals("Registration") && !userName.equals("") && !password.equals("") && !repeatPassword.equals("")) {
            /*
             Checking whether the password matches and the repeated password.
             */
            if (password.equals(repeatPassword)) {
                user.setName(userName);
                user.setPassword(password);
                try {
                    userDAO.addUser(user);
                } catch (DAOException | ConnectionDBException | SQLException e) {
                    e.printStackTrace();
                }

                HttpSession session = req.getSession();
                session.setAttribute("loginedUser", user.getName());
                logger.info("User " + user.getName() + " was registered");
                /*
                User will be added to the database
                */
                req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
            }
            /*
             If the fields password and repeated password are not equal. User will see the warning message
             */
            else {
                logger.warn("Password and Re-Password are not equal");
                PrintWriter out = resp.getWriter();
                out.println(getHTMLPage("Password and Re-Password are not equal. Please, try it again"));
                out.close();
            }
        }
        /*
         User will see the warning message
         */
        else if (submitType.equals("register") || userName.equals("") ||
                password.equals("") || repeatPassword.equals("")) {
            logger.warn("The filed User Name or Password or Re-Password is empty");
            PrintWriter out = resp.getWriter();
            out.println(getHTMLPage("The filed User Name or Password or Re-Password is empty. Please, try it again"));
            out.close();

            req.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(req, resp);
        }
    }

    /**
     *
     * @param message on the page
     * @return HTML page
     */
    private String getHTMLPage(String message) {
        return
                "<html>" +
                    "<head>" +
                    "<title>" +
                        "Info Page" +
                    "</title>" +
                    "</head>" +
                    "<body>" +
                        "<h3>" +
                            message +
                        "</h3>" +
                    "</body>" +
                 "</html>";
    }
}
