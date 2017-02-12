/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis;

import POJO.Investigador;
import com.google.gson.Gson;
import java.util.Iterator;
import java.util.Vector;
import java.util.function.Function;
import org.jenetics.BitGene;
import org.jenetics.Chromosome;
import org.jenetics.Genotype;
import org.jenetics.IntegerGene;
import org.jenetics.LongGene;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

/**
 *
 * @author Edu
 */
class FuncionFitness implements Function<Genotype<IntegerGene>, Double>{
	private final Vector<Investigador>investigadores;
        private Driver driver;
        private String filtro;
        private Integer diametro;
      
    
	

	public FuncionFitness(final Vector<Investigador> investigadores,Driver driver,String filtro,Integer diametro) {
		this.investigadores = investigadores;
                this.driver=driver;
                this.filtro=filtro;
                this.diametro=diametro;
	}
@Override
public Double apply(final Genotype<IntegerGene> gt) {


                //Aca tendria que calcular la distancia, por ahora devuelvo el id nomas
                Session session = driver.session();
                Gson gson = new Gson();
                int distanciaMinima=diametro;
                Double sum=0.0;
                int iteraciones=0;
                int invAnterior=-1;
                Iterator<Chromosome<IntegerGene>> ite1=gt.iterator();
                int index1=0;
                int divisor=0;
                while(ite1.hasNext()){
                    Integer val2=-1;
                    Iterator<Chromosome<IntegerGene>> ite2=gt.iterator();
                       int index2=0;
                       Integer val=ite1.next().getGene().intValue();
                       
                       while(index2<=index1 && ite2.hasNext()){
                           val2=ite2.next().getGene().intValue();
                           index2++;
                       }
                        if(index2>index1){
                            while(ite2.hasNext()){
                                int distancia=10;
                                val2=ite2.next().getGene().intValue();
                                StatementResult result= session.run("MATCH (from:investigador), (to:investigador) , path = shortestPath((from)-["+filtro+"*0..]-(to)) WHERE id(from) = "+investigadores.get(val).getId()+" AND id(to) ="+investigadores.get(val2).getId()+"  RETURN length(path) AS distancia ");
                                if (result.hasNext() ){
                                 Record record=result.next();
                                 distancia=Integer.valueOf(record.asMap().get("distancia").toString());
                                }
                                sum=sum+ distancia;
                                divisor=divisor+1;
                                if(distancia<distanciaMinima)
                                     distanciaMinima=distancia;
                            }
                           
                            
                        }
                            index1++;  
                    } 
                
                //System.out.println("resultado: "+sum+". ");
                session.close();
                return (sum/divisor+distanciaMinima)/(2*diametro);
	}

    
    
}
