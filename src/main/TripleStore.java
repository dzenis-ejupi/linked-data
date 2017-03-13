package main;
 
import org.apache.jena.query.Dataset;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.tdb.TDBLoader;
import org.apache.jena.tdb.sys.TDBInternal;


public class TripleStore {
	public static final String GRAPH_LOCATION = "resources/university/university.ttl";
	public static final String TRIPPLE_STORE_DIR = "resources/university/";
	
    public static Dataset getDataset(){
    	Dataset dataset = loadGraphIntoTripleStore();
    	
		return dataset;
    }
    
    private static Dataset loadGraphIntoTripleStore(){
    	Dataset dataset = TDBFactory.createDataset(TRIPPLE_STORE_DIR);
		TDBLoader.load(TDBInternal.getBaseDatasetGraphTDB(dataset.asDatasetGraph()), GRAPH_LOCATION);
		
		return dataset;
    }
}
