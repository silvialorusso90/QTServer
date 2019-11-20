package mining;/*
classe che include l'implementazione dell'algoritmo QT
 */

import data.Data;
import data.EmptyDatasetException;
import mining.Cluster;
import mining.ClusterSet;

import java.io.*;


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
    public QTMiner(double radius) {
        C = new ClusterSet();
        this.radius = radius;
    }

    /**
     * Apre il file identificato da fileName, legge l'oggetto memorizzato e lo assegna a C.
     * @param fileName percorso+nome file
     */
    public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream inFile = new FileInputStream(fileName);
        ObjectInputStream inStream = new ObjectInputStream(inFile);
        QTMiner qt = (QTMiner)inStream.readObject();
        inStream.close();
        C = qt.C;
        radius = qt.radius;
    }

    /**
     * Apre il file identificato da fileName e salva l'oggetto riferito da C in tale file.
     * @param fileName percorso+nome file
     */
    public void salva(String fileName) throws FileNotFoundException, IOException {
        FileOutputStream outFile = new FileOutputStream(fileName);
        ObjectOutputStream outStream = new ObjectOutputStream(outFile);
        outStream.writeObject(this);
        outStream.close();
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
    public int compute(Data data) throws ClusteringRadiusException, EmptyDatasetException {
        if(data.getNumberOfExamples()==0)
            throw new EmptyDatasetException();
        int numclusters=0;
        boolean isClustered[]=new boolean[data.getNumberOfExamples()];
        for(int i=0;i<isClustered.length;i++)
            isClustered[i]=false;
        int countClustered=0;
        while(countClustered!=data.getNumberOfExamples()){
            //Ricerca cluster pi˘ popoloso
            Cluster c=buildCandidateCluster(data, isClustered);
            C.add(c);
            numclusters++;
            //Rimuovo tuple clusterizzate da dataset
            //int clusteredTupleId[]=c.iterator();
            for(Integer i : c)
                isClustered[i]=true;
            countClustered+=c.getSize();
        }
        if(numclusters==1)
            throw new ClusteringRadiusException();
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
    private Cluster buildCandidateCluster(Data data, boolean isClustered[]){
        Cluster ClusterMax = new Cluster(data.getItemSet(0));
        for(int i=0;i<isClustered.length;i++){
            if(!isClustered[i]){
                Cluster c = new Cluster(data.getItemSet(i));
                for(int j=0; j<isClustered.length; j++){
                    if(!isClustered[j]){
                        if(data.getItemSet(i).getDistance(data.getItemSet(j))<=radius)
                            c.addData(j);
                    }
                }
                if(c.getSize()>ClusterMax.getSize())
                    ClusterMax = c;
            }
        }
        return ClusterMax;
    }
}
