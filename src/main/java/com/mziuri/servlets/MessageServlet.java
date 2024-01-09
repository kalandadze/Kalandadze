package com.mziuri.servlets;

import com.mziuri.classes.Message;
import com.mziuri.classes.User;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

@WebServlet(urlPatterns = "/message")
public class MessageServlet extends HttpServlet {
    public MessageServlet() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        try {
            User user=new User(username,password);
            if (!user.exists()){
                resp.sendError(403);
            }else {
                StringBuilder messages=new StringBuilder();
                for(Message message:user.findMessages()){
                    messages.append(message.getMessage()).append("\n");
                }
                resp.getWriter().write(String.valueOf(messages));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user=new User(req.getParameter("username"),"");
            String message=req.getParameter("message");
            if (message.contains("\n")||message.contains("\\n")){
                resp.sendError(403,"new line");
            } else if (!user.exists()) {
                resp.sendError(403, "user");
            } else {
                try {
                    Message message1=new Message(user,message);
                    message1.store();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
