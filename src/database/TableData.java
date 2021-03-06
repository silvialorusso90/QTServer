package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import database.TableSchema.Column;


public class TableData {

    //membri attributi

    /**
     * oggetto che permette la connessione al database relazionale
     */
    DbAccess db;

    //membri metodi

    /**
     * inizializza db
     * @param db oggetto che permette la conessione al database relazionale
     */
    public TableData(DbAccess db) {
        this.db = db;
    }

    /**
     * Ricava lo schema della tabella con nome table. Esegue un'interrogazione per estrarre
     * le tuple distinte da tale tabella. Per ogni tupla del resultset, si crea un oggetto,
     * istanza della classe Example, il cui riferimento va incluso nella lista da restituire.
     * In particolare, per la tupla corrente nel resultset, si estraggono i valori dei singoli
     * campi (usando getFloat() o getString()), e li si aggiungono all’oggetto istanza della
     * classe Example che si sta costruendo.
     * @param table nome della tabella nel database
     * @return lista di transazioni distinte memorizzate nella tabella
     * @throws SQLException se ci sono errori nell'esecuzione della query
     * @throws EmptySetException se il resultset è vuoto
     */
    public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException{
        LinkedList<Example> transSet = new LinkedList<Example>();
        Statement statement;
        TableSchema tSchema=new TableSchema(db,table);
        String query="select distinct ";
        for(int i=0;i<tSchema.getNumberOfAttributes();i++){
            Column c=tSchema.getColumn(i);
            if(i>0)
                query+=",";
            query += c.getColumnName();
        }
        if(tSchema.getNumberOfAttributes()==0)
            throw new SQLException();
        query += (" FROM "+table);
        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        boolean empty=true;
        while (rs.next()) {
            empty=false;
            Example currentTuple=new Example();
            for(int i=0;i<tSchema.getNumberOfAttributes();i++)
                if(tSchema.getColumn(i).isNumber())
                    currentTuple.add(rs.getDouble(i+1));
                else
                    currentTuple.add(rs.getString(i+1));
            transSet.add(currentTuple);
        }
        rs.close();
        statement.close();
        if(empty) throw new EmptySetException();
        return transSet;
    }

    /**
     * Formula ed esegue una interrogazione SQL per estrarre i valori distinti ordinati di column e
     * popolare un insieme da restituire (scegliere opportunamente il Set da utilizzare).
     * @param table nome della tabella
     * @param column nome della colonna nella tabella
     * @return Insieme di valori distinti ordinati in modalità ascendente
     * che l'attributo identificato da nome column assume nella tabella identificata dal nome table.
     * @throws SQLException se ci sono errori nella esecuzione della query
     */
    public  Set<Object>getDistinctColumnValues(String table,Column column) throws SQLException{
        Set<Object> valueSet = new TreeSet<Object>();
        Statement statement;
        TableSchema tSchema=new TableSchema(db,table);
        String query="select distinct ";
        query+= column.getColumnName();
        query += (" FROM "+table);
        query += (" ORDER BY " +column.getColumnName());
        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            if(column.isNumber())
                valueSet.add(rs.getDouble(1));
            else
                valueSet.add(rs.getString(1));
        }
        rs.close();
        statement.close();
        return valueSet;
    }

    /**
     * Formula ed esegue una interrogazione SQL per estrarre il valore aggregato (valore minimo o valore massimo)
     * cercato nella colonna di nome column della tabella di nome table.
     * Il metodo solleva e propaga una NoValueException se il resultset è vuoto o il valore calcolato è pari a null
     * @param table nome della tabella
     * @param column nome della colonna
     * @param aggregate operatore SQL di aggregazione (min,max)
     * @return valore aggregato cercato
     * @throws SQLException se ci sono errori nella esecuzione della query
     * @throws NoValueException se non ci sono valori all'interno del resultSet
     */
    public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,
            NoValueException{
        Statement statement;
        TableSchema tSchema=new TableSchema(db,table);
        Object value=null;
        String aggregateOp="";
        String query="select ";
        if(aggregate==QUERY_TYPE.MAX)
            aggregateOp+="max";
        else
            aggregateOp+="min";
        query+=aggregateOp+"("+column.getColumnName()+ ") FROM "+table;
        statement = db.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            if(column.isNumber())
                value=rs.getFloat(1);
            else
                value=rs.getString(1);
        }
        rs.close();
        statement.close();
        if(value==null)
            throw new NoValueException("No " + aggregateOp+ " on "+ column.getColumnName());
        return value;
    }
}

