package data;

import java.util.Iterator;
import java.util.Set;

//classe concreta che modella un attributo discreto (numerico)
public class DiscreteAttribute extends Attribute implements Iterable<String>{

    /**
     * array di oggetti String, uno per ciascun valore del dominio discreto.
     * I valori del dominio sono memorizzati in values seguendo un ordine lessicografico
     */
    Set<String> values;

    /**
     * Invoca il costruttore della classe madre e inizializza il membro values con il parametro in input
     * @param name nome dell'attributo
     * @param index indice numerico dell'attributo
     * @param values array di stringhe rappresentanti il dominio dell'attributo
     */
    public DiscreteAttribute(String name, int index, Set<String> values) {
        super(name, index);
        this.values = values;
    }

    /**
     * restituisce la dimensione di values
     * @return numero di valori discreti nel dominio dell'attributo
     */
    int getNumberOfDistinctValues(){
        return values.size();
    }

    public Iterator<String> iterator(){
        return values.iterator();
    }
}
