package com.mziuri.servlets;

import com.mziuri.classes.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        try {
            User user1=new User(username,password);
            if (user1.exists()){
                resp.sendError(403);
            }else {
                user1.saveUser();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
