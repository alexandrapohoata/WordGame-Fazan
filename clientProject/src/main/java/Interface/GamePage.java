package Interface;

import ClientComunication.ClientComunication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GamePage extends JFrame {

    private JPanel Game;
    private JButton buttonExit;
    private JButton buttonHome;
    private JTextArea textWelcome;
    private JTextField wordRecived;
    private JTextField youWord;
    private JButton buttonHelp;
    private JButton buttonSend;
    private JTextField letter;
    private JComboBox helpBox;
    private JTextField nrHelp;
    private JLabel nbHelp;
    private List<String> words = new ArrayList<>();//preiau cuvinte de la server
    private static int number = 3;


    ClientComunication clientComunication;

//    public void setComunication(ClientComunication clientComunication) {
//        this.clientComunication = clientComunication;
//    }

    public GamePage(ClientComunication clientComunication) {

        super("WordGames");
//        request.setCommand("game");
        this.setContentPane(Game);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.clientComunication = clientComunication;
        //if user is logg print nick name
        this.textWelcome.append("BINE AI VENIT ");//setez welcome nickname
        this.textWelcome.append("!!");
        this.nbHelp.setText("Mai ai " + number + " incercari!");

        try {
            clientComunication.sendRequest("play");
            String play = clientComunication.readRequest();
            //setez play ca fiinf incepe si termina
            wordRecived.setText(play);//primul cuvant care apare

        } catch (IOException exception) {
            exception.printStackTrace();
        }


        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    clientComunication.sendRequest("exit");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        buttonHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                StartPage startPage = new StartPage(clientComunication);
                startPage.setVisible(true);

            }
        });
        buttonSend.addActionListener(new ActionListener() {//buton pentru trimitere cuvant
            @Override
            public void actionPerformed(ActionEvent e) {

                //trimit la server iau se la server validare
                try {
                    clientComunication.sendRequest("game");//trimit status
                    String sendWord = youWord.getText();//extrag cuvantul jucatorului
                    System.out.println("youWord=" + youWord);
                    clientComunication.sendRequest(sendWord);//trimit la server cuvantul jucatorului
                    String validation = clientComunication.readRequest();//primesc primul cuvant sau validarea
                    System.out.println(validation);//il afisez pentru verificare
                    if (validation.equals("pierdut")) {
                        JOptionPane.showMessageDialog(null, "Ai pierdut!");
                    } else if (validation.equals("castigat")) {
                        JOptionPane.showMessageDialog(null, "Ai castigat!");
                    }
//                    if (validation.equals("gresit"))//daca cuvantul este gresit sterg vietile la 3 vieti pierdute jucatorul pierde
//                        { JOptionPane.showMessageDialog(null, "Ai pierdut o viata!");
//                            wordRecived.setText(validation);//setez cuvantul primit spre afisare
//                             }
//                    else
//                        if(validation.equals("inchisServer"))
//                        {
//                            JOptionPane.showMessageDialog(null, "Adversarul a pierdut o viata!");
//                            wordRecived.setText("cuvantul tau");
//                        }
                    else
                        wordRecived.setText(validation);


                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
        });
        buttonHelp.addActionListener(new ActionListener() {//buton pentru cerere ajutor
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    helpBox.removeAllItems();
                    clientComunication.sendRequest("help");

                    if (number != 0) {

                        clientComunication.sendRequest(letter.getText());
                        String word = clientComunication.readRequest();
                        if (word.equals("stop"))
                            helpBox.addItem("nu exista!");
                        else {
                            System.out.println(word);
                            words.add(word);

                            int nr = 0;
                            while (true) {

                                System.out.println(word);
                                word = clientComunication.readRequest();
                                if (word.equals("stop")) {
                                    break;
                                }
                                words.add(word);
                            }
                        }

                        for (String word1 : words) {
                            helpBox.addItem(word1);

                        }

                        number--;
                        nbHelp.setText("Mai ai " + number + " incercari!");
                    }


                } catch (IOException exception) {
                    exception.printStackTrace();
                }


            }
        });
    }
}
