package data;
/*
classe che rappresenta una coppia <Attributo discreto- valore discreto>
(per esempio Outlook=”Sunny”
*/

import java.io.Serializable;

public class DiscreteItem extends Item implements Serializable {

    /**
     * Invoca il costruttore della classe madre
     * @param attribute attributo coinvolto nell'item
     * @param value valore assegnato all'attributo
     */
    public DiscreteItem(Attribute attribute, Object value) {
        super(attribute, value);
    }

    /**
     * Restituisce 0 se (getValue().equals(a)) , 1 altrimenti
     * @param a item discreto
     * @return 0 se (getValue().equals(a)) , 1 altrimenti
     */
    @Override
    double distance(Object a) {
        if(getValue().equals(((DiscreteItem)a).getValue()))
            return 0;
        return 1;
    }
}
