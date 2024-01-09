package com.mziuri.classes;

import java.sql.*;
import java.util.List;

public class User {
    Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/messenger","root","password");

    private String username;
    private String password;
    private List<Message> messages;

    public User(String username, String password) throws SQLException {
        this.username = username;
        this.password = password;
        findMessages();
    }

    private void findMessages() throws SQLException {
        PreparedStatement ps= conn.prepareStatement("select message from messages where user=?");
        ps.setString(1,username);
        ResultSet rs=ps.executeQuery();
        ps.close();
        while (rs.next()){
            messages.add(new Message(this,rs.getString("message")));
        }
    }

    public void saveUser() throws SQLException {
        if (!exists()){
            PreparedStatement ps=conn.prepareStatement("insert into users(username,password) values(?,?)");
            ps.setString(1,username);
            ps.setString(2,password);
            ps.executeUpdate();
            ps.close();
        }
    }
    public boolean exists() throws SQLException {
        PreparedStatement ps= conn.prepareStatement("select*from messages where user=?");
        ps.setString(1,username);
        ResultSet rs=ps.executeQuery();
        ps.close();
        return rs.next();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
