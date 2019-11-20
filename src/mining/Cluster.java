package mining;
/*
classe che modella un cluster
 */

import data.Data;
import data.Tuple;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


class Cluster implements Iterable<Integer>, Comparable<Cluster>, Serializable {

    //Attributi

    /**
     * tupla centroide del cluster
     */
    private Tuple centroid;

    /**
     * array di tuple clusterizzate, l'elemento i-esimo dell'array
     * vale true se se l'i-esima tupla appartiene al cluster
     */
    private Set<Integer> clusteredData;


    //Metodi

    /**
     * inizializza il centroide del cluster e crea un array vuoto di tuple
     * @param centroid centroide del cluster
     */
    Cluster(Tuple centroid){
        this.centroid = centroid;
        clusteredData = new HashSet<Integer>();
    };

    /**
     * restituisce il centroide del cluster
     * @return centroide del cluster
     */
    Tuple getCentroid(){
        return centroid;
    }

    /**
     * restituisce true se la tupla sta cambiando cluster, false altrimenti
     * @param id identificatore numerico della tupla
     * @return true se la tupla ha cambiato cluster
     */
    boolean addData(int id){
        return clusteredData.add(id);

    }

    /**
     * verifica se una transazione è clusterizzata nell'array corrente
     * @param id identificatore numerico della tupla
     * @return true se la tupla appartiene all'array corrente, false altrimenti
     */
    boolean contain(int id){
        return clusteredData.contains(id);
    }

    /**
     * elimina la tupla che ha cambiato cluster
     * @param id identificatore numerico della tupla
     */
    void removeTuple(int id){
        clusteredData.remove(id);

    }

    /**
     * restitusice clusteredData.size()
     * @return dimensione del cluster
     */
    int  getSize(){
        return clusteredData.size();
    }

    /**
     *
     * @return
     */
    public Iterator<Integer> iterator(){
        return clusteredData.iterator();
    }

    /**
     * restituisce una stringa composta dagli item del cluster
     * @return stringa rappresentante il centroide del cluster
     */
    public String toString(){
        String str = "Centroid = (";
        for(int i=0; i < centroid.getLength(); i++)
            str += centroid.get(i);
        str += ")";
        return str;
    }

    /**
     * restitusice una stringa fatta dal centroide del cluster, da tutte le tuple
     * appartenenti al cluster e la distanza media delle tuple dal centroide
     * @return stringa rappresentante lo stato del cluster
     */
    String toString(Data data){
        String str="Centroid=(";
        for(int i=0;i<centroid.getLength();i++)
            str += (i!=0 ? " " : "") + centroid.get(i);
        str+=")\nExamples:\n";
        for(Integer i : clusteredData){
            str+="[";
            for(int j=0;j<data.getNumberOfExplanatoryAttributes();j++)
                str += (j!=0 ? " " : "") + data.getValue(i, j);
            str+="] dist="+getCentroid().getDistance(data.getItemSet(i))+"\n";
        }
        return str+"\nAvgDistance="+getCentroid().avgDistance(data, clusteredData)+"\n";
    }

    @Override
    public int compareTo(Cluster c) {
        if(this.clusteredData.size()>c.clusteredData.size())
            return 1;
        return -1;
    }
}
