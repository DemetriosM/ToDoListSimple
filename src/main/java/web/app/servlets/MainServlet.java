package web.app.servlets;

import web.app.model.Message;
import web.app.model.Status;
import web.app.model.Task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Task> tasks;
        if (session.getAttribute("tasks") == null) {
            tasks = new ArrayList<>();
            tasks.add(new Task("task1", "to do task1", Status.IN_PROGRESS));
            tasks.add(new Task("task2", "to do task2", Status.IN_PROGRESS));
            session.setAttribute("tasks", tasks);
            session.setAttribute("message", new Message("..."));
        }
        session.setAttribute("statuses", Status.values());
        session.setAttribute("selectedStatus", Status.PLANNED.name());
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }
}
