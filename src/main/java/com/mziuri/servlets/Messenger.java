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

@WebServlet("/messenger")
public class Messenger extends HttpServlet {
    Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/messenger","root","password");


    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            Statement st= conn.createStatement();
            st.executeUpdate("CREATE SCHEMA IF NOT EXISTS `messenger`;\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS `messenger`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `username` VARCHAR(45) NOT NULL,\n" +
                    "  `password` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS `messenger`.`messages` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `user` VARCHAR(45) NOT NULL,\n" +
                    "  `message` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`));\n" +
                    "\n");
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Messenger() throws SQLException {
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String password=req.getParameter("password");
        try {
            User user=new User(username,password);
            if (!user.exists()){
                resp.getWriter().write("\nUser doesn't exists");
            }else {
                StringBuilder messages=new StringBuilder();
                for(Message message:user.getMessages()){
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
        String user=req.getParameter("user");
        String password=req.getParameter("password");
        if (!Objects.equals(password, "")){
            try {
                User user1=new User(user,password);
                if (user1.exists()){
                    resp.getWriter().write("\nUser Already exists");
                }else {
                    user1.saveUser();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        String message=req.getParameter("message");
        if (!Objects.equals(message,"")){
            if (message.contains("\n")||message.contains("\\n")){
                resp.getWriter().write("\nmessage can't contain new line or \\n");
            }else {
                try {
                    Message message1=new Message(user,message);
                    message1.store();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
