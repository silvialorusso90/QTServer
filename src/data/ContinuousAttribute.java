package data;

import data.Attribute;

//classe concreta che modella un attributo continuo (numerico)
public class ContinuousAttribute extends Attribute {

    /**
     * max: massimo valore che può assumere l'attributo
     */
    double max;

    /**
     * min: minimo valore che può assumere l'attributo
     */
    double min ;

    /**
     * invoca il costruttore della classe madre e inizializza i membri
     * aggiunti per estensione
     * @param name nome dell'attributo
     * @param index indice numerico dell'attributo
     * @param max massimo valore che l'attributo può assumere
     * @param min minimo valore che l'attributo può assumere
     */
    public ContinuousAttribute(String name, int index, double max, double min) {
        super(name, index);
        this.max = max;
        this.min = min;
    }

    /**
     * Calcola e restituisce il valore normalizzato del parametro passato in input.
     * La normalizzazione ha come codominio lo intervallo [0,1]. La normalizzazione
     * di v è quindi calcolata come segue:
     * v'=(v-min)/(max-min)
     * @param v valore dell'attributo da normalizzare
     * @return v1: valore normalizzato
     */
    double getScaledValue(double v){
        double v1 = (v - min)/(max - min);
        return v1;
    }
}
