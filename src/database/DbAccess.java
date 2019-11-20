package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Realizza l'accesso alla base di dati
 */
public class DbAccess {

    //membri attributi


    /**
     * contiene il nome della classe Driver
     */
    //private String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    /**
     * contiene l'identificativo del Database Management System
     */
    private final String DBMS = "jdbc:mysql";

    /**
     * contiene l'identificativo del server che contiene il database
     */
    private final String SERVER = "localhost";

    /**
     * nome della base di dati
     */
    private final String DATABASE = "MapDB";

    /**
     * porta su cui il DBMS MySQL accetta le connessioni
     */
    private final String PORT = "3306";

    /**
     * nome dell’utente per l’accesso alla base di dati
     */
    private final String USER_ID = "Map";

    /**
     * password di autenticazione per l’utente identificato da USER_ID
     */
    private final String PASSWORD = "map";

    /**
     * gestisce una connessione
     */
    private Connection conn;

    //membri metodi

    /**
     * impartisce al class loader l’ordine di caricare il driver mysql,
     * inizializza la connessione riferita da conn.
     * Il metodo solleva e propaga una eccezione di tipo DatabaseConnectionException in caso di
     * fallimento nella connessione al database.
     * @throws DatabaseConnectionException in caso di fallimento nella connessione al database
     */
    public void initConnection() throws DatabaseConnectionException{
        /*
        try {
            Class.forName(DRIVER_CLASS_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }

         */
        try {
            conn = DriverManager.getConnection(DBMS+"://" +SERVER + ":" + PORT + "/" + DATABASE, USER_ID, PASSWORD);
        }catch (SQLException e){
            e.printStackTrace();
            throw new DatabaseConnectionException();
        }
    }

    /**
     * restituisce conn
     * @return connessione conn
     */
    public Connection getConnection(){
        return conn;
    }

    public void closeConnection()  {
        try {
            conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
