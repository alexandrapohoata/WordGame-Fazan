package ServerAction;


//import request.Request;
//import server.SocketServer;

import ConectionBd.DbConection;
import Request.*;
import Tabels.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class ClientThread extends Thread {
    private int scorPlayer;
    private int scorServer;
    private String wordServer;
    private Socket socket = null;
    private final SocketServer server;


    ClientThread(Socket socket, SocketServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {

        try {
            while (true) {// Get the request from the input stream: client → server
                DbConection dbConection = null;
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String requestCommand = in.readLine();//citesc requestul
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                System.out.println(requestCommand);
//                if (!socket.getInetAddress().isReachable(100)) {
//                    System.out.println("sunt aici");
//                    break;
//                }
                //if the command is register I read the name and password then I validate the name then I send the answer
                if (requestCommand.equals("register")) {
                    String requestNikNameR = in.readLine();
                    System.out.println(requestNikNameR);
                    String requestPasswordR = in.readLine();
                    System.out.println(requestPasswordR);

                    User user = new User();
                    user.setNickName(requestNikNameR);
                    user.setPassword(requestPasswordR);
                    RegisterRequest registerRequest = new RegisterRequest(user, dbConection.getConnection());

                    out.println(registerRequest.getRequest());
                    out.flush();
                    requestCommand = in.readLine();
                }//citesc urmatoare comanda cand ies din cea curenta
                System.out.println("inainte de LOGIN" + requestCommand);
                //if the command is login I read nick the name and send to the server the validation of the log in the database
                if (requestCommand.equals("login")) {
                    String requestNikNameL = in.readLine();
                    System.out.println("nume LOGIN: " + requestNikNameL);
                    String requestPasswordL = in.readLine();
                    System.out.println("parola LOGIN: " + requestPasswordL);

                    LoginRequest loginRequest = new LoginRequest(requestNikNameL, requestPasswordL, dbConection.getConnection());
                    System.out.println("trimit: " + loginRequest.getRequest());
                    out.println(loginRequest.getRequest());
                    out.flush();
                    requestCommand = in.readLine();//citesc urmatoarea comanda cand ies din cea curenta
                }
                if (requestCommand.equals("play"))//primesc play
                {
                    scorServer = 3;
                    scorPlayer = 3;
                    Random r = new Random();
                    char c = (char) (r.nextInt(26) + 'a');
                    String letter = "";
                    letter += c;
                    WordRequest wordRequest = new WordRequest(letter, dbConection.getConnection());//trimit primul cuvant
                    System.out.println(wordRequest.getWordToSend());
                    out.println(wordRequest.getWordToSend());
                    out.flush();
                    wordServer = wordRequest.getWordToSend();
                    requestCommand = in.readLine();
                    while (true) {
                        //if the command is translate I read the sequence of letters then extract words that start with those letters and send them to the server one by one

                        if (requestCommand.equals("help")) {
                            String requestLetter = in.readLine();
                            HelpRequest helpRequest = new HelpRequest(requestLetter, dbConection.getConnection());
                            List<String> words = helpRequest.getWords();
                            for (String word : words) {
                                if (word != null)
                                    out.println(word);
                                out.flush();
                            }
                            requestCommand = in.readLine();//recer comanda
                        } else {//joaca jocul
                            if (requestCommand.equals("game")) {
                                System.out.println(wordServer);
                                String playerWord = in.readLine();//citesc cuvantul de la server
                                GameRequest gameRequest = new GameRequest(wordServer, playerWord, dbConection.getConnection(), scorPlayer, scorPlayer);
                                scorPlayer = gameRequest.getScorPlayer();
                                scorServer = gameRequest.getScorServer();
                                String validare = gameRequest.getRequest();
                                wordServer = validare;

                                out.println(validare);
                                out.flush();
//                                if(validare.equals("pierdut") || validare.equals("castigat"))//daca am castigat sau am pierdut ies si inchid clientul
//                                { requestCommand = "stop";
//                                    try {
//                                        sleep(100000);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                    break;}
//                                else
//
//                                requestCommand = in.readLine();//recer comanda
                            }


                            requestCommand = in.readLine();

                        }
                    }
                }
                // I close the client
                if (requestCommand.equals("exit")) {

                    in.close();
                    out.close();
                    break;

                }
                //I close the client and the server
                if (requestCommand.equals("stop")) {
                    in.close();
                    out.close();

                    System.exit(0);
                }
//
                // Send the response to the oputput stream: server → client
//                String raspuns = request1.getRequest();
//                String raspuns = "Hello " + requestCommand + "!";
//                out.println(raspuns);
//                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

}

