package Interface;

import ClientComunication.ClientComunication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

public class RegistePage extends JFrame {
    //    ReadGame request;//use for set request
    private JPanel Register;
    private JTextField nickName;
    private JPasswordField password;
    private JButton buttonRegister;
    private JButton buttonExit;
    private Map<String, String> played = new HashMap<>();
    private List<String> nameValidation = new ArrayList<>();

    private int validationName(String name) {
        for (String nameList : nameValidation) {
            if (nameList.equals(name)) ;
            return 1;
        }
        return 0;

    }

    public List<String> getNameValidation() {
        return nameValidation;
    }

    //    ClientComunication clientComunication = new ClientComunication();
    ClientComunication clientComunication;

    public void setComunication(ClientComunication clientComunication) {
        this.clientComunication = clientComunication;
    }

    public RegistePage() {
        super("WordGames");
//        request.setCommand("register");//set the command
        this.setContentPane(Register);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        //action for register button : close curent page and open register page
        //I send the name and password to the server then I model the answer
        buttonRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    clientComunication.sendRequest("register");
                    if (validationName(nickName.getText()) == 1)
                        JOptionPane.showMessageDialog(null, "EROARE Incearca din nou:nick name existent!");
                    else {
                        nameValidation.add(nickName.getText());
                        clientComunication.sendRequest(nickName.getText());
                        clientComunication.sendRequest(password.getText());
                        String answer = clientComunication.readRequest();
                        System.out.println(answer);
                        if (answer.equals("register")) {
                            played.put(nickName.getText(), password.getText());
                            JOptionPane.showMessageDialog(null, "Cont creat!");
                            dispose();
                            LoginPage loginPage = new LoginPage(clientComunication);
                            loginPage.setVisible(true);
//                        loginPage.setComunication(clientComunication);
//                        StartPage startPage = new StartPage();
//                        startPage.setVisible(true);

                        }
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

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
    }
}
