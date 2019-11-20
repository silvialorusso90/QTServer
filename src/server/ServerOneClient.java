package server;

import data.Data;
import data.EmptyDatasetException;
import mining.ClusteringRadiusException;
import mining.QTMiner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerOneClient  extends Thread{

    //membri attributi

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private QTMiner qt;

    //membri metodi

    /**Costruttore di classe. Inizializza gli attributi socket, in e out. Avvia il thread.*/
    public ServerOneClient(Socket s) throws IOException {
        socket = s;
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    /**Riscrive il metodo run della superclasse Thread al fine di gestire le richieste del client.*/
    public void run() {
        try {
            int menuAnswer;
            Data data = null;
            QTMiner qt = null;
            String tabName = "";
            int numC = 0;
            double r = 0;
            while(true) {
                menuAnswer = (int)in.readObject();
                if(menuAnswer!=-1) {
                    switch(menuAnswer)
                    {
                        case 0:
                            tabName = (String)in.readObject();
                            data = new Data(tabName);
                            out.writeObject("OK");
                            break;
                        case 1:
                            r = (double)in.readObject();
                            qt=new QTMiner(r);
                            try {
                                numC=qt.compute(data);
                                out.writeObject("OK");
                                out.writeObject(numC);
                                out.writeObject(qt.getC().toString(data));
                            } catch(EmptyDatasetException | ClusteringRadiusException e) {
                                out.writeObject(e.getMessage());
                            }
                            break;
                        case 2:
                            String fileName=tabName+"_"+r+".dmp";
                            try {
                                qt.salva(fileName);
                                out.writeObject("OK");
                            } catch (FileNotFoundException e) {
                                out.writeObject(e.getMessage());
                            } catch (IOException e) {
                                out.writeObject(e.getMessage());
                            }
                            break;
                        case 3:
                            tabName = (String)in.readObject();
                            r = (double)in.readObject();
                            try {
                                qt=new QTMiner(tabName+"_"+r+".dmp");
                                out.writeObject("OK");
                                out.writeObject(qt.getC().toString());
                            } catch (FileNotFoundException e1) {
                                out.writeObject(e1.getMessage());
                            } catch (IOException e1) {
                                out.writeObject(e1.getMessage());
                            } catch (ClassNotFoundException e1) {
                                out.writeObject(e1.getMessage());
                            }
                            break;
                    }
                }
                else
                    break;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch(IOException e) {
                System.err.println("Socket not closed");
            }
        }
    }


}
