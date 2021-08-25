package Request;

import Tabels.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HelpRequest {
    private List<String> words = new ArrayList<>();

    public HelpRequest(String letter, Connection connection) {
        int i = 0;
        letter = letter + "%";
        PreparedStatement statement = null;
        String sql = "SELECT * FROM autocomplete WHERE formNoAccent like ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, letter);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                i++;
                if (i < 5)
                    if (rs.getString(1).length() >= 3)
                        words.add(rs.getString(1));
            }

            words.add("stop");

        } catch (
                SQLException throwables) {
            System.out.println("NULLL");
            throwables.printStackTrace();
        }

    }

    public List<String> getWords() {
        return words;
    }
}
