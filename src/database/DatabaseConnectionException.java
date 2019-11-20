package database;

public class DatabaseConnectionException extends Exception {
    public String getMessage(){
        return new String("Database connection failed!");
    }
}
