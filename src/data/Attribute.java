package data;

//classe astratta che modella l'entità attributo
public abstract class Attribute {

    /**
     * name: nome simbolico dell'attributo
     */
    String name;

    /**
     * index: identificativo numerico dell'attributo
     */
    int index;

    /**
     * inizializza i valori di name e index
     * @param name nome dell'attributo
     * @param index indice dell'attributo
     */
    public Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * restituisce name, nome dell'attributo
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * restituisce index, indice numerico dell'attributo
     * @return index
     */
    public int getIndex() {
        return index;
    }

    /**
     * restituisce una stringa rappresentante il nome dell'oggetto
     * @return name
     */
    @Override
    public String toString() {
        return "data.Attribute {" +
                "name = '" + name + '\'' +
                '}';
    }
}
