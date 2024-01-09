package com.mziuri;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {

    public static void main(String[] args) throws LifecycleException, SQLException {

        Tomcat tomcat = new Tomcat();

        tomcat.enableNaming();
        tomcat.setPort(8989);
        tomcat.getConnector();

        String ctxPath = "/messenger";
        String webappDir = new File("src/main/webapp").getAbsolutePath();

        StandardContext ctx = (StandardContext) tomcat.addWebapp(ctxPath, webappDir);

        //declare an alternate location for your "WEB-INF/classes" dir:
        File additionWebInfClasses = new File("build/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();

        Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/messenger","root","password");

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

}
