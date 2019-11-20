package database;

public class EmptySetException extends Exception {
    public String getmessage(){
        return new String("ResultSet is empty!");
    }
}
