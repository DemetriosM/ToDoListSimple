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

public class ActionWithTaskServlet extends HttpServlet {

    Boolean editStatus = false;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Task> tasks;
        Message message = new Message("...");
        if (session.getAttribute("tasks") == null) {
            tasks = new ArrayList<>();
            tasks.add(new Task("task1", "to do task1", Status.IN_PROGRESS));
            tasks.add(new Task("task2", "to do task2", Status.IN_PROGRESS));
            session.setAttribute("tasks", tasks);
        } else {
            tasks = (ArrayList<Task>)session.getAttribute("tasks");
        }
        if (session.getAttribute("task") == null) {
            Task task = new Task("", "");
            session.setAttribute("task", task);
        }
        if (request.getParameter("doAction") != null) {
            switch (request.getParameter("doAction")) {
                case "doAdd":
                    addAction(request, tasks, message);
                    break;
                case "doDelete":
                    deleteAction(request, tasks, message);
                    break;
                case "doEdit":
                    editAction(request, session, tasks);
                    break;
            }
        }
        session.setAttribute("tasks", tasks);
        session.setAttribute("message", message);
        session.setAttribute("statuses", Status.values());
        session.setAttribute("selectedStatus", Status.PLANNED.name());
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }

    private void deleteAction(HttpServletRequest request, List<Task> tasks, Message message) {
        int taskIndex = Integer.parseInt(request.getParameter("taskIndex"));
        if (tasks.get(taskIndex).getStatus().equals(Status.DONE)) {
            tasks.remove(taskIndex);
            message.setText("The task was deleted.");
        } else {
            message.setText("The task can be deleted only if its status is DONE (or you reloaded the page :) ).");
        }
    }

    private void addAction(HttpServletRequest request, List<Task> tasks, Message message) {
        String name = request.getParameter("taskName");
        if (name.trim().length() != 0) {
            Task task = new Task(name, request.getParameter("taskDescription"));
            task.setStatus(Status.valueOf(request.getParameter("taskStatus")));
            if (editStatus) {
                String taskIndex = request.getParameter("index");
                tasks.set(Integer.parseInt(taskIndex), task);
                message.setText("Task was edited.");
                editStatus = false;
            } else if (tasks.stream().anyMatch(t -> t.getName().trim().toLowerCase().equals(name.trim().toLowerCase()))) {
                message.setText("A task with this name already exists (or you reloaded the page :) ).");
            } else {
                tasks.add(task);
                message.setText("Task was added.");
            }
        }
    }

    private void editAction(HttpServletRequest request, HttpSession session, List<Task> tasks) {
        editStatus = true;
        String taskIndex = request.getParameter("taskIndex");
        Task task = tasks.get(Integer.parseInt(taskIndex));
        session.setAttribute("task", task);
        request.setAttribute("index", taskIndex);
        request.setAttribute("taskName", task.getName());
        request.setAttribute("taskDescription", task.getDescription());
        request.setAttribute("selectedStatus", task.getStatus().toString());
    }
}
