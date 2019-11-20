package data;

import data.Attribute;
import database.*;

import java.sql.SQLException;
import java.util.*;

//modella l'insieme di transazioni (tuple)
public class Data {

    /**
     * matrice nXm di tipo Object dove ogni riga modella una transazioni
     */
    /*
    private Object data[][];
     */

    /**
     * lista di oggetti di tipo Example in cui ogni oggetto modella una transazioone
     */
    private List<Example> data=new ArrayList<Example>();

    /**
     * cardinalità dell’insieme di transazioni (numero di righe in data)
     */
    private int numberOfExamples;

    /**
     * vettore degli attributi in ciascuna tupla (schema della tabella di dati)
     */
    private List<Attribute> explanatorySet;

    /**
     * - Inizializza la matrice data [ ][ ] con transazioni di esempio
     * - Inizializza explanatorySet creando cinque oggetti di tipo
     *   data.DiscreteAttribute, uno per ciascun attributo
     * - Inizializza numberOfExamples
     */
    /*
    public Data()throws EmptyDatasetException{

        explanatorySet = new LinkedList<Attribute>();

        Set<String> outLookValues = new TreeSet<String>();
        outLookValues.add("overcast");
        outLookValues.add("rain");
        outLookValues.add("sunny");
        explanatorySet.add(new DiscreteAttribute("Outlook", 0, outLookValues));

        explanatorySet.add(new ContinuousAttribute("Temperature", 1, 3.2, 38.7));

        Set<String> humidityValues = new TreeSet<String>();
        humidityValues.add("high");
        humidityValues.add("normal");
        explanatorySet.add(new DiscreteAttribute("Humidity", 2, humidityValues));

        Set<String> windValues = new TreeSet<String>();
        windValues.add("weak");
        windValues.add("strong");
        explanatorySet.add(new DiscreteAttribute("Wind", 3, windValues));

        Set<String> playTennisValues = new TreeSet<String>();
        playTennisValues.add("yes");
        playTennisValues.add("no");
        explanatorySet.add(new DiscreteAttribute("PlayTennis", 4, playTennisValues));

        numberOfExamples = 14;
        data = new Object [14][5];

        data[0][0]="sunny";
        data[0][1]=new Double (30.3);
        data[0][2]="high";
        data[0][3]="weak";
        data[0][4]="no";
        //secondo esempio
        data[1][0]="sunny";
        data[1][1]=new Double (30.3);
        data[1][2]="high";
        data[1][3]="strong";
        data[1][4]="no";
        //terzo esempio
        data[2][0]="overcast";
        data[2][1]=new Double (30);
        data[2][2]="high";
        data[2][3]="weak";
        data[2][4]="yes";
        //quarto esempio
        data[3][0]="rain";
        data[3][1]=new Double (13);
        data[3][2]="high";
        data[3][3]="weak";
        data[3][4]="yes";
        //quinto esempio
        data[4][0]="rain";
        data[4][1]=new Double (0);
        data[4][2]="normal";
        data[4][3]="weak";
        data[4][4]="yes";
        //sesto esempio
        data[5][0]="rain";
        data[5][1]=new Double (0);
        data[5][2]="normal";
        data[5][3]="strong";
        data[5][4]="no";
        //settimo esempio
        data[6][0]="overcast";
        data[6][1]=new Double (0.1);
        data[6][2]="normal";
        data[6][3]="strong";
        data[6][4]="yes";
        //otavo esempio
        data[7][0]="sunny";
        data[7][1]=new Double (13);
        data[7][2]="high";
        data[7][3]="weak";
        data[7][4]="no";
        //nono esempio
        data[8][0]="sunny";
        data[8][1]=new Double (0.1);
        data[8][2]="normal";
        data[8][3]="weak";
        data[8][4]="yes";
        //decimo esempio
        data[9][0]="rain";
        data[9][1]=new Double (12);
        data[9][2]="normal";
        data[9][3]="weak";
        data[9][4]="yes";
        //undicesimo esempio
        data[10][0]="sunny";
        data[10][1]=new Double (12.5);
        data[10][2]="normal";
        data[10][3]="strong";
        data[10][4]="yes";
        //dodicesimo esempio
        data[11][0]="overcast";
        data[11][1]=new Double (12.5);
        data[11][2]="high";
        data[11][3]="strong";
        data[11][4]="yes";
        //tredicesimo esempio
        data[12][0]="overcast";
        data[12][1]=new Double(29.21);
        data[12][2]="normal";
        data[12][3]="weak";
        data[12][4]="yes";
        //quatordicesimo esempio
        data[13][0]="rain";
        data[13][1]=new Double (12.5);
        data[13][2]="high";
        data[13][3]="strong";
        data[13][4]="no";

    }
     */

    /**
     * si occupa di caricare i dati di addestramento da una tabella della base di dati.
     * @param table nome della tabella
     */
    public Data(String table) {
        DbAccess db = new DbAccess();
        try {
            db.initConnection();
            TableData td = new TableData(db);
            try {
                //data
                data = td.getDistinctTransazioni(table);

                //explanatoryset
                explanatorySet = new LinkedList<Attribute>();
                TableSchema ts = new TableSchema(db, table);
                int i = 0;
                for (TableSchema.Column c : ts){
                    if (c.isNumber()){
                        try {
                            double min = (float)td.getAggregateColumnValue(table, c, QUERY_TYPE.MIN);
                            double max = (float)td.getAggregateColumnValue(table, c, QUERY_TYPE.MAX);
                            explanatorySet.add(new ContinuousAttribute(c.getColumnName(), i, min, max));
                        }catch (NoValueException e){
                            e.getMessage();
                        }
                    }
                    else {
                        Set<String> s = new TreeSet<>();
                        Set<Object> dv = td.getDistinctColumnValues(table, c);
                        for (Object o : dv){
                            s.add((String) o);
                        }
                        explanatorySet.add(new DiscreteAttribute(c.getColumnName(), i, s));
                    }
                    i++;
                }
                //numberforexample
                numberOfExamples = data.size();

            }catch (SQLException e){
                e.printStackTrace();
            }
            catch (EmptySetException e){
                e.getMessage();
            }

        }catch (DatabaseConnectionException e){
            e.getMessage();
        }

    }

    /**
     * restituisce l'insieme di transazioni
     * @return numberOfExamples
     */
    public int getNumberOfExamples(){
        return numberOfExamples;
    }

    /**
     * restituisce la dimensione di explanatorySet
     * @return cardinalità dell'insieme degli attributi
     */
    public int getNumberOfExplanatoryAttributes(){
        return explanatorySet.size();
    }

    /**
     * restituisce explanatorySet
     * @return explanatorySet
     */
    List<Attribute> getAttributeSchema(){
        return explanatorySet;
    }

    /**
     * restituisce valore assunto in data dall'attributo in posizione attributeIndex,
     * nella riga in posizione exampleIndex
     * @param exampleIndex indice di riga della matrice memorizzata in data
     * @param attributeIndex indice di colonna della matrice memorizzata in data
     * @return data[exampleIndex][attributeIndex]
     */
    public Object getValue(int exampleIndex, int attributeIndex){
        //TO DO
        return data.get(exampleIndex).get(attributeIndex);
    }

    /**
     * crea una stringa in cui memorizza lo schema della tabella (vedi explanatorySet) e
     * le transazioni memorizzate in data, opportunamente enumerate
     * @return stringa che modella lo stato dell'oggetto
     */
    @Override
    public String toString() {
        String s = "";
        int n = 1;
        for (Attribute i :explanatorySet){
            s += ((n++) != 1 ? ", " : "") + i;
        }
        n = 1;
        for (Example i : data){
            s += "\n" + (n++) + ": ";
            int m = 0;
            for (Object j : i){
                s += (m++ >= 1 ? ", " : "") + j;
            }
        }
        return s + "\n";
    }

    /**
     * Crea e restituisce un oggetto di data.Tuple che modella come sequenza di coppie
     * Attributo-valore la i-esima riga in data
     * @param index indice di riga
     * @return tuple
     */
    public Tuple getItemSet(int index){
        Tuple tuple=new Tuple(explanatorySet.size());
        for(Attribute i : explanatorySet){
            if(i instanceof DiscreteAttribute)
                tuple.add(new DiscreteItem((DiscreteAttribute)i,(String)data.get(index).get(i.getIndex())),i.getIndex());
            else
                tuple.add(new ContinuousItem((ContinuousAttribute)i,(Double)data.get(index).get(i.getIndex())),i.getIndex());
        }
        return tuple;
    }

    /**
     * consenta il test delle classi implementate, in particolare
     * permette la stampa dell'insieme di transazioni.
     * @param args parametri del main
     */
	/*public static void main(String args[]){
		Data trainingSet=new Data();
		System.out.println(trainingSet);
	}*/
}