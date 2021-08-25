package Interface;

import ClientComunication.ClientComunication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginPage extends JFrame {
    //    ReadGame request;//use for set request
    private JPanel Login;
    private JTextField nickName;
    private JButton buttonLogin;
    private JButton buttonResgister;
    private JPasswordField password;
    private JButton butttonExit;
    private int validationLogin = 0;//preiau date de la server

    ClientComunication clientComunication;

    public int getValidationLogin() {
        return validationLogin;
    }

//    public void setComunication(ClientComunication clientComunication){
//        this.clientComunication = clientComunication;
//    }

    public LoginPage(ClientComunication clientComunication) {
        super("WordGames");
        this.setContentPane(Login);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.clientComunication = clientComunication;
        butttonExit.addActionListener(new ActionListener() {
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
        buttonResgister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistePage registePage = new RegistePage();
                registePage.setVisible(true);
                registePage.setComunication(clientComunication);


            }
        });
        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    clientComunication.sendRequest("login");
                    clientComunication.sendRequest(nickName.getText());
                    clientComunication.sendRequest(password.getText());
                    String answer = clientComunication.readRequest();
                    System.out.println("RASPUNS:" + answer);
                    if (answer.equals("login")) {
                        validationLogin = 1;
                        JOptionPane.showMessageDialog(null, "Logare cu succes!");
                        dispose();
                        GamePage gamePage = new GamePage(clientComunication);
                        gamePage.setVisible(true);
                        //gamePage.setComunication(clientComunication);
                    } else

                        JOptionPane.showMessageDialog(null, "EROARE: Cont inexistent!");


                } catch (IOException exception) {
                    exception.printStackTrace();
                }


            }
        });
    }
}
