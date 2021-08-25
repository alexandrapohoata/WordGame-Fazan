import ClientComunication.ClientComunication;
import Interface.StartPage;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClientComunication clientComunication = new ClientComunication();
        StartPage startPage = new StartPage(clientComunication);
        startPage.setVisible(true);

    }
}
