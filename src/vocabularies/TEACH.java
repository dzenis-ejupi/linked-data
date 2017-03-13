package vocabularies;
 
import org.apache.jena.rdf.model.*;
 
/**
 * Vocabulary definitions 
 */
public class TEACH {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static final Model M_MODEL = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://linkedscience.org/teach/ns/#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     * @return namespace as String
     * @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = M_MODEL.createResource( NS );
    
    public static final Property courseTitle = M_MODEL.createProperty( "http://linkedscience.org/teach/ns#courseTitle" );
    
    public static final Property weeklyHours = M_MODEL.createProperty( "http://linkedscience.org/teach/ns#weeklyHours" );
    
    public static final Property ects = M_MODEL.createProperty( "http://linkedscience.org/teach/ns#ects" );
    
    public static final Property teacher = M_MODEL.createProperty( "http://linkedscience.org/teach/ns#teacher" );
    
}
