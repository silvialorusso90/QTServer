package data;

import java.io.Serializable;

// classe che modella una coppia <Attributo continuo - valore numerico> (e.g., Temperature=30.5)

public class ContinuousItem extends Item implements Serializable {

    //Metodi

    /**
     * inizializza i valori dei membri attributi
     *
     * @param attribute attributo coinvolto nell'item
     * @param value     valore assegnato all'attributo
     */
    ContinuousItem(Attribute attribute, Double value) {
        super(attribute, value);
    }

    @Override
    double distance(Object a) {
        return Math.abs(((ContinuousAttribute)this.getAttribute()).getScaledValue((Double)
                this.getValue())-(((ContinuousAttribute)((ContinuousItem)a).getAttribute()).
                getScaledValue((Double)((ContinuousItem)a).getValue())));
    }
}
