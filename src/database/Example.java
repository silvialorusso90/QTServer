package database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * classe che modella la transazione letta dalla base di dati
 */
public class Example implements Comparable<Example>, Iterable<Object>{

    //membri attributi

    /**
     * lista di transazioni
     */
    private List<Object> example=new ArrayList<Object>();

    //membri metodi

    /**
     * aggiunge una transazione alla lista
     * @param o oggetto transazione da aggiungere alla lista
     */
    public void add(Object o){
        example.add(o);
    }

    /**
     * restituisce la transazione in posizione i
     * @param i posizione della transazione
     * @return transazione alla posizione i
     */
    public Object get(int i){
        return example.get(i);
    }

    /**
     * restituisce 0, -1, 1 sulla base del risultato del confronto,
     * 0 se i due esempi includono gli stessi valori,
     * altrimenti il risultato del compareTo(...) invocato sulla prima coppia di valori in disaccordo
     * @param ex esempio da confrontare
     * @return 0 se i due esempi sono uguali, -1 o 1 altrimenti
     */
    public int compareTo(Example ex) {
        int i=0;
        for(Object o:ex.example){
            if(!o.equals(this.example.get(i)))
                return ((Comparable)o).compareTo(example.get(i));
            i++;
        }
        return 0;
    }

    /**
     * restituisce la stringa rappresentante l'esempio
     * @return stringa rappresentante l'esempio
     */
    public String toString(){
        String str="";
        for(Object o:example)
            str+=o.toString()+ " ";
        return str;
    }

    @Override
    public Iterator<Object> iterator() {
        return example.iterator();
    }

}
