/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
//import jersey.repackaged.com.google.common.collect.Iterables;
import org.glassfish.jersey.client.ClientResponse;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jaxb.internal.*;
//import org.neo4j.driver.internal.util.Iterables;
import org.neo4j.helpers.collection.Iterables; 
//import org.neo4j.driver.internal.util.Iterables;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

//import org.neo4j.driver.v1.types.Path;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.jdbc.Connection;
import org.neo4j.jdbc.PreparedStatement;
import org.neo4j.jdbc.ResultSet;

/**
 *
 * @author Edu
 */
public class Tesis {

    
    public static void mainviejo() {
       
    /*try (Connection con = DriverManager.getConnection("jdbc:neo4j:bolt://localhost")) {

    // Querying
    String query = "MATCH (u:User)-[:FRIEND]-(f:User) WHERE u.name = {1} RETURN f.name, f.age";
    try (PreparedStatement stmt = con.prepareStatement(query)) {
        stmt.setString(1,"John");

        try (ResultSet rs = stmt.execute()) {
            while (rs.next()) {
                System.out.println("Friend: "+rs.getString("f.name")+" is "+rs.getInt("f.age"));
            }
        }
    }
}       catch (SQLException ex) {
            Logger.getLogger(Tesis.class.getName()).log(Level.SEVERE, null, ex);
        }*/
         Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "123456" ) );
Session session = driver.session();

//session.run( "CREATE (a:Person {name:'Arthur', title:'King'})" );
Gson gson = new Gson();

//StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title" );
//StatementResult result = session.run( "MATCH (n:investigador) RETURN n.Nombre as Nombre, n.TipoInstitucionTrabajo as TipoInstitucionTrabajo, n.InstitucionDeTrabajo as InstitucionDeTrabajo, n.CuitOPasaporte as CuitOPasaporte, n.DisciplinaActuacion as DisciplinaActuacion, n.ProvinciaResidencia as ProvinciaResidencia,n.RangoEtario as RangoEtario,n.MaximoNivelEducativo as MaximoNivelEducativo,n.RangoDeActualizacionCV as RangoDeActualizacionCV, n.Nacionalidad as Nacionalidad, n.ProvinciaLugarDeTrabajo as ProvinciaLugarDeTrabajo,n.Apellido as Apellido, n.Identificador as Identificador,n.GranAreaActuacion as GranAreaActuacion LIMIT 25" );
//StatementResult result = session.run( "MATCH p=(n:pais)-[r:nacionalidad]->() RETURN p");
/*StatementResult resultOrigen = session.run("MATCH (n:investigador) RETURN n" );
 int n=0;
while ( resultOrigen.hasNext() ){
 org.neo4j.driver.v1.types.Node origen=resultOrigen.next().get(0).asNode();
 StatementResult resultDestino = session.run("MATCH (n:investigador) where ID(n)>"+origen.id()+"  RETURN n" );   
while(resultDestino.hasNext()){
   org.neo4j.driver.v1.types.Node destino=resultDestino.next().get(0).asNode();
  
 StatementResult result= session.run("MATCH (from:investigador{CuitOPasaporte:\""+origen.get("CuitOPasaporte").asString()+"\"}), (to:investigador{ CuitOPasaporte:\""+destino.get("CuitOPasaporte").asString()+"\"}) , path = shortestPath((from)-[*0..]-(to))\n" +
"RETURN path AS shortestPath,\n" +
"reduce(distance = 0, r in relationships(path) | distance+1) AS totalDistance \n" +
"   ORDER BY totalDistance ASC\n" +
" LIMIT 1 ");

while ( result.hasNext() )
{   n=n+1;
    Record record = result.next();
    
    //System.out.println(gson.toJsonTree(record.asMap()));
    if(n%10==0)
    System.out.println(n);
    //Investigador inv = gson.fromJson(gson.toJson(record.asMap()), Investigador.class); 
   
    
   //System.out.println( record.get( "f" ).asNode().id() + " " + record.get( "f" ).asNode().get("CuitOPasaporte"));
}

}
}*/
 StatementResult result= session.run("MATCH (from:investigador), (to:investigador) , path = shortestPath((from)-[*0..]-(to)) RETURN path AS shortestPath ");
long n=0;
System.out.println("consulta lista");
 while ( result.hasNext() ){
     Record record=result.next();
     if(n%10000==0)
         System.out.println(gson.toJsonTree(record.asMap()));
    n++;
}
        System.out.println(n);
session.close();
driver.close();
        /* HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("neo4j", "123456");
        Client client = ClientBuilder.newClient();
        client.register(feature);
        Form form = new Form();
        form.param("x", "foo");
        form.param("y", "bar");
        MyJAXBBean bean =
        target.request(MediaType.APPLICATION_JSON_TYPE)
        .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
        MyJAXBBean.class);
        */
/*        Response response = client.target("http://localhost:7474").path("db/data/cypher").request(MediaType.APPLICATION_JSON_TYPE).header("Content-Type", "application/json;charset=UTF-8").post("{\n" +
"  \"query\" : \"CREATE (n:Person { name : {name} }) RETURN n\",\n" +
"  \"params\" : {\n" +
"    \"name\" : \"Andres\"\n" +
"  }\n" +
"}");*/
       /* MongoClient mongo; 
        mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase db;
        db = mongo.getDatabase("test");
        
        MongoCollection<org.bson.Document> investigadores = db.getCollection("investigadores");
        MongoCollection<org.bson.Document> enlaces = db.getCollection("enlaces");
       for (org.bson.Document document : investigadores.find())
           System.out.println(document.getInteger("Identificador"));*/
        
     /*  for (org.bson.Document document : investigadores.find()) {
            for (org.bson.Document document2 : investigadores.find()){
             if(document.getString("DisciplinaActuacion").equals(document2.getString("DisciplinaActuacion"))){
                    org.bson.Document nuevo= new org.bson.Document("id1",document.getString("identificador"));
                    nuevo.append("id2", document2.getString("identificador"));
                    enlaces.insertOne(nuevo);
                }
            }
        }*/
       
        
    }

    
    private List<Path> findPathsSortedByLength(PathFinderInput input) {
    List<Path> result = new LinkedList<Path>();
   
    //first attempt: classic shortest path
    result.addAll(Iterables.toList(GraphAlgoFactory.shortestPath(input.getExpander(), input.getMaxDepth()).findAllPaths(input.getStart(), input.getEnd())));

    //If there are no results, there will never be any. If there are enough, then we just return them:
    if (result.isEmpty() || result.size() >= input.getMaxResults()) {
        return result;
    }

    //Now, we have some results, but not enough. All the resulting paths so far must have the same length (they are
    //the shortest paths after all). We try with longer path length until we have enough:
    for (int depth = result.get(0).length() + 1; depth <= input.getMaxDepth() && result.size() < input.getMaxResults(); depth++) {
        result.addAll(Iterables.toList(GraphAlgoFactory.pathsWithLength(input.getExpander(), depth).findAllPaths(input.getStart(), input.getEnd())));
    }
    for(int i=0;i<result.size();i++)
     System.out.println(result.get(0).length());
    return result;
}
 
}
