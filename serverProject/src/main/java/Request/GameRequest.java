package Request;

import java.sql.Connection;
import java.util.Random;

public class GameRequest {
    private String request;
    private int scorPlayer;
    private int scorServer;
    WordRequest wordRequest;

    public GameRequest(String wordServer, String wordPlayer, Connection connection, int scorPlayer, int scorServer) {
        this.scorPlayer = scorPlayer;
        this.scorServer = scorServer;
        //        if( wordServer.equals(null))
//        {
//            wordRequest = new WordRequest("qq", connection);
//            request = wordRequest.getWordToSend();
//
//        }
//        else
//        {
        int n = wordPlayer.length();
        int m = wordPlayer.length() - 2;
        int i = wordServer.length();
        int j = wordServer.length() - 2;
        String letterEndPlayer = wordPlayer.substring(m, n);//cu ce litere termina jucatorul
        String letterEndServer = wordServer.substring(j, i);//cu ce a terminat serverul
        String letterStartPlayer = wordPlayer.substring(0, 2);//cu ce litere incepe jucatorul
        String letterStartServer = wordServer.substring(0, 2);//cu ce litere incepe serverul

        if (letterStartPlayer.equals(letterEndServer)) {
            if (letterEndPlayer.equals("nt") || letterEndPlayer.equals("rt") || letterEndPlayer.equals("rn") || letterEndPlayer.equals("ct")) {
                Random r = new Random();
                char c = (char) (r.nextInt(26) + 'a');
                String letter = "";
                letter += c;
                wordRequest = new WordRequest(letter, connection);
                request = wordRequest.getWordToSend();//daca e inchis da cuvant clientul
                this.scorServer--;
            } else {
                wordRequest = new WordRequest(letterEndPlayer, connection);
                request = wordRequest.getWordToSend();
            }
        } else {

            wordRequest = new WordRequest(letterEndPlayer, connection);
            request = wordRequest.getWordToSend();
            this.scorPlayer--;
        }
        if (scorPlayer == 0)
            request = "pierdut";
        if (scorServer == 0)
            request = "castigat";
        if (request == null) {
            Random r = new Random();
            char c = (char) (r.nextInt(26) + 'a');
            String letter = "";
            letter += c;
            wordRequest = new WordRequest(letter, connection);
            request = wordRequest.getWordToSend();
        }


    }
//        wordRequest = new WordRequest(letterEndPlayer, connection);
//        request = wordRequest.getWordToSend();
    //}


    public int getScorPlayer() {
        System.out.println(scorPlayer);
        return scorPlayer;
    }

    public int getScorServer() {
        System.out.println(scorPlayer);
        return scorServer;
    }

    public String getRequest() {

        return request;
    }
}
