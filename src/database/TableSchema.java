package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * classe che modella lo schema di una tabella nel database relazionale
 */
public class TableSchema implements Iterable<database.TableSchema.Column>{

    //membri attributi

    /**
     * oggetto che accede alla base di dati
     */
    DbAccess db;

    /**
     * colonna del database
     */
    public class Column{

        //membri attributi

        /**
         * nome della colonna
         */
        private String name;

        /**
         * tipo di dato memorizzato nella colonna
         */
        private String type;

        //membri metodi

        /**
         * inizializza gli attributi della colonna
         * @param name nome della colonna
         * @param type tipo di dato memorizzato nella colonna
         */
        Column(String name,String type){
            this.name=name;
            this.type=type;
        }

        /**
         * restituisce il nome della colonna
         * @return stringa rappresentante il nome della colonna
         */
        public String getColumnName(){
            return name;
        }

        /**
         * controlla se il tipo di dato contenuto nella colonna è un valore numerico
         * @return true se il tipo di dato è numerico, false altrimenti
         */
        public boolean isNumber(){
            return type.equals("number");
        }

        /**
         * restituisce la stringa rappresentante la colonna
         * @return stringa rappresentante la colonna
         */
        public String toString(){
            return name+":"+type;
        }
    }

    /**
     * lista di colonne
     */
    List<Column> tableSchema=new ArrayList<Column>();

    //membri metodi

    /**
     * inizializza il db, crea lo schema della tabella identificata da tableName nel database relazionale
     * @param db oggetto che permette la connessione al database
     * @param tableName nome della tabella
     */
    public TableSchema(DbAccess db, String tableName) throws SQLException{
        this.db=db;
        HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
        //http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
        mapSQL_JAVATypes.put("CHAR","string");
        mapSQL_JAVATypes.put("VARCHAR","string");
        mapSQL_JAVATypes.put("LONGVARCHAR","string");
        mapSQL_JAVATypes.put("BIT","string");
        mapSQL_JAVATypes.put("SHORT","number");
        mapSQL_JAVATypes.put("INT","number");
        mapSQL_JAVATypes.put("LONG","number");
        mapSQL_JAVATypes.put("FLOAT","number");
        mapSQL_JAVATypes.put("DOUBLE","number");

        Connection con=db.getConnection();
        DatabaseMetaData meta = con.getMetaData();
        ResultSet res = meta.getColumns(null, null, tableName, null);

        while (res.next()) {
            if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
                tableSchema.add(new Column(
                        res.getString("COLUMN_NAME"),
                        mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
                );
        }
        res.close();
    }

    /**
     * restituisce la dimensione dello schema
     * @return cardinalità dello schema
     */
    public int getNumberOfAttributes(){
        return tableSchema.size();
    }

    /**
     * restituisce la colonna identificata da index
     * @param index identificativo numerico della colonna
     * @return colonna identificata da index
     */
    public Column getColumn(int index){
        return tableSchema.get(index);
    }

    @Override
    public Iterator<Column> iterator(){
        return tableSchema.iterator();
    }

}





