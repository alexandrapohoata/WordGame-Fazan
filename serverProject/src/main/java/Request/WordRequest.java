package Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WordRequest {

    private String wordToSend;

    public WordRequest(String letter, Connection connection) {

        letter = letter + "%";
        PreparedStatement statement = null;
        String sql = "SELECT * FROM cuvinte WHERE cuvant like ?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, letter);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                wordToSend = rs.getString(1);
                if (wordToSend.equals(null)) {
                    wordToSend = "albina";
                    break;
                }
                if (wordToSend.length() > 3)
                    break;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getWordToSend() {
        return wordToSend;
    }
}