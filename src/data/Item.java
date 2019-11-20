package data;/*
classe astratta che modella un generico item (coppia attributo-valore, per esempio Outlook=”Sunny”).
 */

import data.Attribute;

abstract class Item {

    //Attributi

    /**
     * attributo coinvolto nell'item
     */
    Attribute attribute;

    /**
     * valore assegnato all'attributo
     */
    Object value;

    //Metodi

    /**
     * inizializza i valori dei membri attributi
     * @param attribute attributo coinvolto nell'item
     * @param value valore assegnato all'attributo
     */
    Item(Attribute attribute, Object value){
        this.attribute = attribute;
        this.value = value;
    }

    /**
     *restituisce attribute
     * @return attribute
     */
    Attribute getAttribute(){
        return attribute;
    }

    /**
     * restituisce value
     * @return value
     */
    Object getValue(){
        return value;
    }

    /**
     * restituisce value
     * @return value
     */
    @Override
    public String toString() {
        return ""+value;
    }

    abstract double distance(Object a);
}
