<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <link rel="stylesheet" href="css/main.css">
        <title>ToDoList</title>
    </head>
    <body>
        <ul class = "tasks">
            <li class = "tasks__header">
                <div class = "task-name">Name</div>
                <div class = "task-description">Description</div>
                <div class = "task-status">Status</div>
            </li>
            <c:forEach items="${tasks}" var="task" varStatus = "loop">
                <li class = "tasks__item">
                    <div class = "task-name"><c:out value="${task.getName()}"/></div>
                    <div class = "task-description"><c:out value="${task.getDescription()}"/></div>
                    <div class = "task-status"><c:out value="${task.getStatus()}"/></div>
                    <div class = "task-edit">
                      <form action = "action" method = "GET">
                        <input type = "hidden" name = "doAction" value = "doEdit">
                        <input type = "hidden" name = "taskIndex" value = "${loop.index}">
                        <input type = "submit" class = "btn-edit" name = "doEdit" value = "edit"></submit>
                      </form>
                      <form action = "action" method = "GET">
                        <input type = "hidden" name = "doAction" value = "doDelete">
                        <input type = "hidden" name = "taskName" value = "${task.getName()}">
                        <input type = "submit" class = "btn-delete" name = "doEdit" value = "delete"></input>
                      </form>
                    </div>
                </li>
            </c:forEach>
        </ul>
        <form class = "add_task" action = "action" method = "GET">
            <fieldset>
                <legend>Fields for adding or changing tasks</legend>
                <p class = add_task__status>Status of last action:
                    <span><c:out value = "${message.text}" default = "..."/></span>
                </p>
                <input type = "hidden" name = "doAction" value = "doAdd">
                <input type = "hidden" name = "index" value = "${index}">
                <p><input class = "add_task__title" type = "text" name = "taskName" placeholder = "title" value = "${taskName}" required></p>
                <p><textarea name="taskDescription" placeholder="description ...">${taskDescription}</textarea></p>
                <p><select name = "taskStatus">
                    <c:forEach var="item" items="${statuses}">
                        <option value="${item}" ${item == selectedStatus ? 'selected' : ''}>${item}</option>
                    </c:forEach>
                </select></p>
                <p><input type = "submit" class = "btn-ok" name = "doAdd" value = "OK"></input></p>
            </fieldset>
        </form>
    </body>
</html>
