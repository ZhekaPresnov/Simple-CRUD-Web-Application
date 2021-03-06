package ru.home.webapp.servlets;

import org.apache.log4j.Logger;
import ru.home.webapp.dao.BookDAO;
import ru.home.webapp.dao.DAOException;
import ru.home.webapp.dao.IBookDAO;
import ru.home.webapp.domain.Book;
import ru.home.webapp.utils.ConnectionDBException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * This class implements the logic of going to a page when a hyperlink 'bookList' is clicked.
 * The class provides a list of books as a table on a web page
 *
 * @author Evgeniy Presnov
 */
@WebServlet("/bookList")
public class BookList extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(BookList.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> listBooks = null;
        IBookDAO bookDAO = new BookDAO();
        try {
            listBooks = bookDAO.getListBooks();
        } catch (DAOException | ConnectionDBException | SQLException e) {
            e.printStackTrace();
            logger.error(e);
        }

        req.setAttribute("listBooks", listBooks);
        req.getRequestDispatcher("/WEB-INF/view/bookList.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
