package Request;

import ConectionBd.DbConection;
import Tabels.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterRequest {
    //    private DbConection dbConection;
    private String request;

    public RegisterRequest(User user, Connection connection) {
        PreparedStatement statement = null;
        String insertUser = "Insert into user (nickName,password)" +
                " values (?,?)";


        try {
            statement = connection.prepareStatement(insertUser);

            statement.setString(1, user.getNickName());
            statement.setString(2, user.getPassword());


            statement.executeUpdate();
            request = "register";


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        try {
            connection.close();
//            dbConection.getConnection().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getRequest() {
        return request;


    }
}

