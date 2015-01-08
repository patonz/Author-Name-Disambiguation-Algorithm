package remote;

import com.hp.hpl.jena.query.*;

/**
 * Created by Leonardo on 08/01/2015.
 */
public class RemoteManager {


    public static void executeQueryTry() {
        String ontology_service = "http://ambit.uni-plovdiv.bg:8080/ontology";
        String endpoint = "otee:Endpoints";
        String endpointsSparql =
                "PREFIX ot:<http://www.opentox.org/api/1.1#>\n" +
                        "	PREFIX ota:<http://www.opentox.org/algorithms.owl#>\n" +
                        "	PREFIX owl:<http://www.w3.org/2002/07/owl#>\n" +
                        "	PREFIX dc:<http://purl.org/dc/elements/1.1/>\n" +
                        "	PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                        "	PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                        "	PREFIX otee:<http://www.opentox.org/echaEndpoints.owl#>\n" +
                        "		select ?url ?title\n" +
                        "		where {\n" +
                        "		?url rdfs:subClassOf %s.\n" +
                        "		?url dc:title ?title.\n" +
                        "		}\n";

        QueryExecution x = QueryExecutionFactory.sparqlService(ontology_service, String.format(endpointsSparql, endpoint));
        ResultSet results = x.execSelect();
        ResultSetFormatter.out(System.out, results);
    }


    public static void executeQuery(String query){
        try {


            String ontology_service = "http://two.eelst.cs.unibo.it:8181/sparql";
            String endpoint = "otee:Endpoints";
            String endpointsSparql = query;

            Query myquery = QueryFactory.create(query);
            myquery.toString();

            QueryExecution x = QueryExecutionFactory.sparqlService(ontology_service, myquery);
            ResultSet results = x.execSelect();
            ResultSetFormatter.out(System.out, results);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
