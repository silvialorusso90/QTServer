package mining;

import data.Data;
import mining.Cluster;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/*
classe che rappresenta un insieme di cluster (determinati da QT)
 */
public class ClusterSet implements Iterable<Cluster>, Serializable {

    //Attributi

    /**
     * array di oggetti cluster
     */
    private Set<Cluster> C=new TreeSet<Cluster>();

    //Metodi

    /**
     * costruttore dell'insieme di cluster
     */
    ClusterSet() {
    }

    /**
     * aggiunge il cluster c all'insieme di cluster
     * @param c cluster da aggiungere all'insieme di cluster
     */
    void add(Cluster c){
        Set<Cluster> tempC = new TreeSet<Cluster>();
        for(Cluster i: C)
            tempC.add(i);
        tempC.add(c);
        C = tempC;
    }

    /**
     * Restituisce una stringa fatta da ciascun centroide dellíinsieme dei cluster.
     * @return stringa rappresentante i centroidi di ogni cluster appartenente all'insieme di cluster
     */
    public String toString(){
        String str="";
        int n = 1;
        for(Cluster i : C)
            str+=(n++)+": "+i.toString()+"\n";
        return str;
    }

    /**Restituisce una stringa che descriva lo stato di ciascun cluster in C.
     * @return stringa rappresentante lo stato di ogni cluster dell'insieme di cluster
     */
    public String toString(Data data){
        String str="";
        int n = 1;
        for(Cluster i : C){
            if (i!=null){
                str+=(n++)+": "+i.toString(data)+"\n";
            }
        }
        return str;
    }

    @Override
    public Iterator<Cluster> iterator() {
        return C.iterator();
    }
}
