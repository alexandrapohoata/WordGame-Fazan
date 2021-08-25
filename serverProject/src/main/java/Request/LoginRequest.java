package Request;


import Tabels.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRequest {
    private int request = 0;


    public LoginRequest(String name, String password, Connection connection) {
        PreparedStatement statement = null;
        String sql = "SELECT * FROM user WHERE nickName='" + name + "' AND password = '" + password + "'";


        try {
            statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                request++;

            }


        } catch (SQLException throwables) {
            System.out.println("NULLL");
            throwables.printStackTrace();
        }


    }

    public String getRequest() {
        if (request != 0)
            return "login";
        else
            return "nu";
    }
}