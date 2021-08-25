package ConectionBd;
//package com.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class DbConection {


    private static Connection con;
    private static DbConection dbConection;

    private DbConection() {
        try {

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java?autoReconnect=true&useSSL=false", "root", "JAVA");

            System.out.println("m-am conecta");

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (con == null) {
            dbConection = new DbConection();
        }

        return con;
    }
}
