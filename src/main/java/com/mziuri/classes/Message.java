package com.mziuri.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Message {
    Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/messenger","root","password");
    private User user;
    private String message;

    public Message(User user, String message) throws SQLException {
        this.user = user;
        this.message = message;
    }
    public Message(String user, String message) throws SQLException {
        this.user = new User(user,"");
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public void store() throws SQLException {
        PreparedStatement ps= conn.prepareStatement("insert into messages(username,password) values(?,?)");
        ps.setString(1,user.getUsername());
        ps.setString(2,message);
        ps.executeUpdate();
        ps.close();
    }
}
