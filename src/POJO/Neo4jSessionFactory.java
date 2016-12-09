/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

/**
 *
 * @author Edu
 */
public class Neo4jSessionFactory {

    private final static SessionFactory sessionFactory = new SessionFactory("POJO");
    
    private static Neo4jSessionFactory factory = new Neo4jSessionFactory();

    public static Neo4jSessionFactory getInstance() {
        
        return factory;
    }

    private Neo4jSessionFactory() {
    
    }

    public Session getNeo4jSession() {
        return sessionFactory.openSession(); 
    }
}
