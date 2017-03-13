package vocabularies;
 
import org.apache.jena.rdf.model.*;
 
/**
 * Vocabulary definitions
 */
public class TUVIENNA {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static final Model M_MODEL = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://ifs.tuwien.ac.at/tulid/group03/";
    
    /** <p>The namespace of the vocabulary as a string</p>
     * @return namespace as String
     * @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = M_MODEL.createResource( NS );
    
    public static final Property courseType = M_MODEL.createProperty( "http://ifs.tuwien.ac.at/tulid/group03/courseType" );
    
    public static final Property roomName = M_MODEL.createProperty( "http://ifs.tuwien.ac.at/tulid/group03/roomName" );
    
    public static final Property roomCapacity = M_MODEL.createProperty( "http://ifs.tuwien.ac.at/tulid/group03/roomCapacity" );
    
    public static final Property roomAddress = M_MODEL.createProperty( "http://ifs.tuwien.ac.at/tulid/group03/roomAddress" );
    
    public static final Property roomNumber = M_MODEL.createProperty( "http://ifs.tuwien.ac.at/tulid/group03/roomNumber" );
    
}
