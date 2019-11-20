package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Multiserver {

    //membri attributi

    /**
     * numero di porta
     */
    private int PORT = 8080;

    //membri metodi

    /**
     * costruttore della classe.
     * Inizializza la porta ed invoca run()
     * @param PORT numero di porta
     */
    public Multiserver(int PORT) {
        this.PORT = PORT;
        run();
    }

    /**
     * istanzia un oggetto di tipo MultiServer
     */
    public static void main(String[] args){
        //int port = new Integer(args[0]).intValue();
        int port = 8080;
        Multiserver ms = new Multiserver(port);

    }

    /**
     * Istanzia un oggetto istanza della classe ServerSocket che pone in
     * attesa di richiesta di connessioni da parte del client.
     * Ad ogni nuova richiesta connessione si istanzia ServerOneClient.
     */
    private void run(){
        try {
            ServerSocket s = new ServerSocket(PORT);
            try {
                while (true){
                    // Si blocca finchè non si verifica una connessione:
                    Socket socket = s.accept();
                    try {
                        new ServerOneClient(socket);
                    }catch (IOException e){
                        //se fallisce chiude il socket, altrimenti lo chiuderà il thread
                        socket.close();
                    }

                }
            }finally {
                s.close();
            }

        }catch (IOException e1){
            e1.printStackTrace();
        }

    }


}
