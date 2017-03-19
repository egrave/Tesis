/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jenetics.Genotype;
import org.jenetics.IntegerChromosome;
import org.jenetics.IntegerGene;
import org.jenetics.Phenotype;
import org.jenetics.Recombinator;
import org.jenetics.SinglePointCrossover;
import org.jenetics.engine.Engine;
import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import org.jenetics.engine.EvolutionStatistics;

/**
 *
 * @author Leo
 */
public class AlgoritmoGenetico extends Thread {
    //Variable estatica apra enumerar los threads
    private static int NumThreads = 0;
    
    //Variables locales
    private Tareas tareas;
    private int NumeroThread;

    public AlgoritmoGenetico(Tareas tareas) {
        this.tareas = tareas;
        //Asigna numero de thread
        NumThreads++;
        NumeroThread = NumThreads;
    }

    public int getNumeroThread() {
        return  NumeroThread;
    }

    
    public void run() {
        while (true) {
            Tarea tarea = tareas.Get(this.NumeroThread);
            System.out.println("Thread" + NumeroThread + " EN EJECUCION");
            final FuncionFitness ff = new FuncionFitness(tarea.getInvestigadores(), tarea.getDriver(), tarea.getFiltroRelacion(), tarea.getDiametroRed());
            final ExecutorService executor = Executors.newFixedThreadPool(10);
            final Engine<IntegerGene, Double> engine;
            engine = Engine
                    .builder(ff, Genotype.of(IntegerChromosome.of(0, tarea.getInvestigadores().size() - 1), tarea.getCantidad()))
                    .populationSize(tarea.getPopulation())
                    .survivorsSelector(tarea.getSurvivorsSelector())
                    .offspringSelector(tarea.getSurvivorsSelector())
                    //.alterers(tarea.getAlterador(), getListOfAlterers(idAlteradores, con))
                    .alterers(tarea.getAlterador())
                    // Using 10 threads for evolving .
                    .executor(executor)
                    .build();

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
                    //.peek(r -> System.out.println("Thread"+ NumeroThread+ ": " + r.getTotalGenerations() + "\t" + r.getBestPhenotype().getFitness() + "\t" + r.getBestPhenotype().getGenotype()))
                    // Collect (reduce) the evolution stream to
                    // its best phenotype.
                    .collect(toBestPhenotype());

            //System.out.println("statics:" + "\n" + statistics);
            System.out.println("Best:" + best);

        }
    }
}
