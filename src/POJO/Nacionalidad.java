/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.util.HashSet;
import java.util.Set;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotations.EntityFactory;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 *
 * @author Edu
 */
@RelationshipEntity(type="nacionalidad")
public class Nacionalidad {
       
    private Long id;

    @StartNode
    private Investigador origen;

    @EndNode
    private Pais destino ;
  
    
    public Nacionalidad() {}    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Investigador getOrigen() {
        return origen;
    }

    public void setOrigen(Investigador origen) {
        this.origen = origen;
    }

    public Pais getDestino() {
        return destino;
    }

    public void setDestino(Pais destino) {
        this.destino = destino;
    }
    
    
}
