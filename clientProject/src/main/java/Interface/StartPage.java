package Interface;

import ClientComunication.ClientComunication;
import com.sun.deploy.util.SessionState;
import com.sun.security.ntlm.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class StartPage extends JFrame {
    private JPanel MainPanel;
    private JButton buttonRegister;
    private JButton buttonExit;
    private JButton buttonLogin;
    private JButton buttonPlay;
    PrintWriter out;
    BufferedReader in;
    ClientComunication clientComunication;
//    public void setComunication(ClientComunication clientComunication){
//        this.clientComunication = clientComunication;
//    }

    public StartPage(ClientComunication clientComunication) {
        super("WordGames");
        this.setContentPane(MainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.clientComunication = clientComunication;
        //action for login button : close curent page and open login page
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginPage loginPage = new LoginPage(clientComunication);
                loginPage.setVisible(true);
//                loginPage.setComunication(clientComunication);

            }
        });
        //action for register button : close curent page and open register page
        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistePage registePage = new RegistePage();
                registePage.setVisible(true);
                registePage.setComunication(clientComunication);
            }
        });
        //action for exit button: close the curent page
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientComunication.sendRequest("exit");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                dispose();
            }
        });
        //action for play button: close the curent page and open the game page
        buttonPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GamePage gamePage = new GamePage(clientComunication);
                gamePage.setVisible(true);
                //gamePage.setComunication(clientComunication);
            }
        });
    }


}
