package mining;
/**
classe che include l'implementazione dell'algoritmo QT
 */

import data.Data;
import data.EmptyDatasetException;
import mining.Cluster;
import mining.ClusterSet;

import java.io.*;
import java.util.Set;
import java.util.TreeSet;

public class QTMiner implements Serializable{

    //Attributi

    /**
     * insieme dei cluster
     */
    private ClusterSet C;

    /**
     * raggio dei cluster
     */
    private double radius;

    //Metodi

    /**
     * Crea l'oggetto array riferito da C e inizializza radius
     * con il parametro passato come input
     * @param radius raggio del cluster
     */
    public QTMiner(final double radius) {
        C = new ClusterSet();
        this.radius = radius;
    }

    /**
     * Apre il file identificato da fileName, legge l'oggetto memorizzato e lo assegna a C.
     * @param fileName percorso+nome file
     */
    public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        final FileInputStream inFile = new FileInputStream(fileName);
        final ObjectInputStream inStream = new ObjectInputStream(inFile);
        QTMiner qt = (QTMiner)inStream.readObject();
        inStream.close();
        C = qt.C;
        radius = qt.radius;


    }

    /**
     * restituisce c
     * @return insieme dei cluster c
     */
    public ClusterSet getC() {
        return C;
    }

    /**
     * Esegue l'algoritmo QT eseguendo i passi dello pseudo-codice:
     * 1. Costruisce un cluster per ciascuna tupla non ancora clusterizzata, includendo
     * nel cluster i punti (non ancora clusterizzati in alcun cluster) che ricodano nel
     * vicinato sferico della tupla avente raggio radius;
     * 2. Salva il candidato cluster più popoloso e rimuove tutti punti di tale
     * cluster dall'elenco delle tuple ancora da clusterizzare;
     * 3. Ritorna al passo 1 finchè ci sono ancora tuple da assegnare ad un cluster
     * @param data insieme di tuple
     * @return numero di cluster scoperti
     */
    public int compute(final Data data) throws ClusteringRadiusException, EmptyDatasetException {
        if(data.getNumberOfExamples()==0)
            throw new EmptyDatasetException("Empty dataset!");
        int numclusters=0;
        final boolean isClustered[]=new boolean[data.getNumberOfExamples()];
        for(int i=0;i<isClustered.length;i++)
            isClustered[i]=false;
        int countClustered=0;
        while(countClustered!=data.getNumberOfExamples()){
            //Ricerca cluster pi˘ popoloso
            final Cluster c=buildCandidateCluster(data, isClustered);
            C.add(c);
            numclusters++;
            //Rimuovo tuple clusterizzate da dataset
            for(final Integer i : c)
                isClustered[i]=true;
            countClustered+=c.getSize();
        }
        if(numclusters==1)
            throw new ClusteringRadiusException(data.getNumberOfExamples() + "tuples in one cluster!");
        return numclusters;
    }

    /**
     * costruisce un cluster per ciascuna tupla di data non ancora clusterizzata
     * in un cluster di C e restituisce il cluster candidato pi˘ popoloso
     * @param data insieme di tuple da raggruppare in cluster
     * @param isClustered informazione booleana sullo stato di clusterizzazione di una tupla
     * (per esempio isClustered[i]=false se la tupla i-esima di data non Ë ancora
     * assegnata ad alcun cluster di C, true altrimenti)
     * @return cluster candidato pi˘ popoloso
     */
    private Cluster buildCandidateCluster(final Data data, final boolean[] isClustered){
        final Set<Cluster> C = new TreeSet<Cluster>();
        for (int i = 0; i < data.getNumberOfExamples(); i++) {
            if (!isClustered[i]) {
                final Cluster candidato = new Cluster(data.getItemSet(i));
                for (int j = 0; j < data.getNumberOfExamples(); j++) {
                    if (!isClustered[j]) {
                        if (data.getItemSet(i).getDistance(data.getItemSet(j)) <= radius) {
                            candidato.addData(j);
                        }
                    }
                }
                C.add(candidato);
            }
        }
        return ((TreeSet<Cluster>) C).last();
    }

    /**
     * Apre il file identificato da fileName e salva l'oggetto riferito da C in tale file.
     * @param fileName percorso+nome file
     */
    public void salva(final String fileName) throws FileNotFoundException, IOException {
        final FileOutputStream outFile = new FileOutputStream(fileName);
        final ObjectOutputStream outStream = new ObjectOutputStream(outFile);
        outStream.writeObject(this);
        outStream.close();
    }

    @Override
    public String toString() {
        return C.toString();
    }

    public String toString(final Data data) {
        return C.toString(data);
    }

}