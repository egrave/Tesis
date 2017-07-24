/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis;

import POJO.Investigador;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jenetics.Alterer;
import org.jenetics.Chromosome;
import org.jenetics.Crossover;
import org.jenetics.Genotype;
import org.jenetics.IntegerChromosome;
import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;
import org.jenetics.Recombinator;
import org.jenetics.SinglePointCrossover;
import org.jenetics.engine.Engine;
import org.jenetics.engine.Engine.Builder;
import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import org.jenetics.engine.EvolutionStatistics;
import xml.Escritor;

/**
 *
 * @author Leo
 */
public class AlgoritmoGenetico extends Thread {

    //Variable estatica para enumerar los threads
    private static int NumThreads = 0;

    //Variables locales
    private Tareas tareas;
    private int NumeroThread;
    private String gexf;

    public AlgoritmoGenetico(Tareas tareas,String gexf) {
        this.tareas = tareas;
        //Asigna numero de thread
        NumThreads++;
        NumeroThread = NumThreads;
        this.gexf=gexf;
    }

    public int getNumeroThread() {
        return NumeroThread;
    }

    public void run() {
        while (true) {
            Tarea tarea = tareas.Get(this.NumeroThread);
            System.out.println("Thread" + NumeroThread + " EN EJECUCION");

            final FuncionFitness ff = new FuncionFitness(tarea.getInvestigadores(), tarea.getDriver(), tarea.getFiltroRelacion(), tarea.getDiametroRed());
            //Crea 10 threads
            final ExecutorService executor = Executors.newFixedThreadPool(20);

            Builder<IntegerGene, Double> builder = Engine
                    .builder(ff, Genotype.of(IntegerChromosome.of(0, tarea.getInvestigadores().size() - 1,tarea.getCantidad())))
                    .populationSize(tarea.getPopulation())
                    .survivorsSelector(tarea.getSurvivorsSelector())
                    .offspringSelector(tarea.getSurvivorsSelector());

            //Setea los alteradores al builder
            Alterer<IntegerGene, Double>[] alteradoresAdicionales = tarea.GetAlteradoresAdicionales();
            if (alteradoresAdicionales.length == 0) {   //un alterador
                builder.alterers(tarea.getAlterador());
            } else {                                    //varios alteradores
                builder.alterers(tarea.getAlterador(), alteradoresAdicionales);
            }
            //Usa threads para evolucionar
            builder.executor(executor);

            //Finalmete crea el engine configurado
            final Engine<IntegerGene, Double> engine;
            engine = builder.build();

            //Ejecucion
            final EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

            final Phenotype<IntegerGene, Double> best = engine.stream()
                    // Truncate the evolution stream after 7 "steady"
                    // generations.
                    .limit(tarea.getPredicate())
                    // The evolution will stop after maximal 100
                    // generations.
                    .limit(tarea.getMaxCorridas())
                    // Update the evaluation statistics after
                    // each generation
                    .peek(statistics)
                    //Muestra el mejor phenotipo de cada generacion durante la iteracion del algoritmo
                    .peek(r -> System.out.println("Thread" + NumeroThread + ": " + r.getTotalGenerations() + "\t" + r.getBestPhenotype().getFitness() + "\t" + r.getBestPhenotype().getGenotype()))
                    // Collect (reduce) the evolution stream to
                    // its best phenotype.
                    .collect(toBestPhenotype());

           System.out.println("statics:" + "\n" + statistics);
            System.out.println("Best:" + best);
            Chromosome c=best.getGenotype().getChromosome(0);
            Vector<Investigador> investigadores=tarea.getInvestigadores();
            Vector<Investigador> comite= new Vector<>();
            for(int i=0;i<c.length();i++){
                comite.add(investigadores.get(((IntegerGene)c.getGene(i)).intValue()));
                Long id=investigadores.get(((IntegerGene)c.getGene(i)).intValue()).getId();
                gexf=gexf.replace("id=\""+id+"\"><attvalues><attvalue for=\"tipo\" value=\"Investigador\"></attvalue></attvalues><viz:color r=\"0\" g=\"0\" b=\"255\"", "id=\""+id+"\"><attvalues><attvalue for=\"tipo\" value=\"Investigador\"></attvalue></attvalues><viz:color r=\"255\" g=\"0\" b=\"0\"");
        
            }
               
                ManagerDB.getInstance().guardarResultado(tarea.getId(), best.getFitness(), best.getGenotype().toString(), tarea.getIdAlteradores(), tarea.getCantidad(), tarea.getFiltroRelacion(), statistics.getEvolveDuration().getSum() / statistics.getEvolveDuration().getMean(), statistics.getEvolveDuration().getMean());
                Escritor.escribir(gexf, "C:\\Users\\Edu\\Desktop\\Tesis\\nodos.gexf");
                VentanaResultados vr= new VentanaResultados(comite, String.valueOf(best.getFitness()));
                vr.setVisible(true);
            //idconfiguracion,fitness,vector,idConfiguracionAlteradores,comite,filtro,generaciones,tiempoPaso
            //rs.getString("id")+","+best.getFitness()+",'"+best.getGenotype()+"',"+idAlteradores+","+cantidad+",'"+filtro+"',"+statistics.getEvolveDuration().getSum()/statistics.getEvolveDuration().getMean()+","+statistics.getEvolveDuration().getMean()+")");
        }
    }
}
