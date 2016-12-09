/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis;

import com.google.gson.Gson;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

/**
 *
 * @author Edu
 */
public class consultasDB {

    public consultasDB() {
         Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "123456" ) );
Session session = driver.session();

session.run( "CREATE (a:Person {name:'Arthur', title:'King'})" );
Gson gson = new Gson();
StatementResult result = session.run( "MATCH (a:Person) WHERE a.name = 'Arthur' RETURN a.name AS name, a.title AS title" );
while ( result.hasNext() )
{
    Record record = result.next();
    gson.toJson(record.asMap());
    System.out.println( record.get( "title" ).asString() + " " + record.get("name").asString() );
}

session.close();
driver.close();
    }
    
}
