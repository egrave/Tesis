/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.util.HashSet;
import java.util.Set;
import org.neo4j.ogm.annotations.EntityFactory;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 *
 * @author Edu
 */
@NodeEntity(label="rangoetario")
public class Rangoetario extends Entity{
   
    private String nombre;
  
   
   
    public Rangoetario() {}
   
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
