/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis;

/**
 *
 * @author Edu
 */
import POJO.Investigador;
import baseDeDatos.ConexionMySQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import static org.jenetics.engine.EvolutionResult.toBestPhenotype;
import static org.jenetics.engine.limit.bySteadyFitness;

import java.util.Random;
import java.util.Vector;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.jenetics.Alterer;

import org.jenetics.BitChromosome;
import org.jenetics.BitGene;
import org.jenetics.BoltzmannSelector;
import org.jenetics.ExponentialRankSelector;
import org.jenetics.GaussianMutator;
import org.jenetics.Genotype;
import org.jenetics.IntegerChromosome;
import org.jenetics.IntegerGene;
import org.jenetics.LinearRankSelector;
import org.jenetics.LongChromosome;
import org.jenetics.LongGene;
import org.jenetics.MeanAlterer;
import org.jenetics.MonteCarloSelector;
import org.jenetics.MultiPointCrossover;
import org.jenetics.Mutator;
import org.jenetics.NumericGene;
import org.jenetics.PartiallyMatchedCrossover;
import org.jenetics.Phenotype;
import org.jenetics.Population;
import org.jenetics.ProbabilitySelector;
import org.jenetics.RouletteWheelSelector;
import org.jenetics.Selector;
import org.jenetics.SinglePointCrossover;
import org.jenetics.StochasticUniversalSelector;
import org.jenetics.SwapMutator;
import org.jenetics.TournamentSelector;
import org.jenetics.TruncationSelector;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.EvolutionStatistics;
import static org.jenetics.engine.limit.byFitnessThreshold;
import static org.jenetics.engine.limit.byFixedGeneration;
import org.jenetics.util.RandomRegistry;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Session;

// This class represents a knapsack item, with a specific
// "size" and "value".
/*final class Miembro {
	public final Long id;
        public static  Vector<Investigador> investigadores;
	

	Miembro(final Long id) {
		this.id=id;
                
	}
        
        public Long getId(){
            return id;
        }
        public static void setInvestigadores(Vector<Investigador> investigadores){
            Miembro.investigadores=investigadores;
        }
	
	static Miembro random() {
		final Random r = RandomRegistry.getRandom();
                System.out.println(investigadores.size());
                Iterator<Integer> ie=r.ints(0,investigadores.size()).iterator();
                Investigador inv=investigadores.get(ie.next());
                System.out.println(inv.getId());
		return new Miembro(inv.getId());
        }
             

	
}*/

// The knapsack fitness function class, which is parametrized with
// the available items and the size of the knapsack.
 
// The main class.
public class Algoritmo {
        
	public Algoritmo(Vector<Investigador> investigadores,Driver driver,String filtro,int cantidad) throws SQLException{
		//llamadoEstatico(investigadores,driver,filtro,cantidad);
                ConexionMySQL con= new ConexionMySQL();
                con.crearConexion("root","");
                final FuncionFitness ff = new FuncionFitness(investigadores,driver,filtro);
		ResultSet rs=con.ejecutarSQLSelect("select * from configuraciones");
                while(rs.next()){
                // Configure and build the evolution engine.
		final Engine<IntegerGene, Double> engine;
                    engine = Engine
                            .builder(ff, Genotype.of(IntegerChromosome.of(0, investigadores.size()-1),cantidad))
                            .populationSize(rs.getInt("population"))
                            .survivorsSelector(getSelector(rs.getString("survivorselector"),rs.getString("survivorparam")))
                            .offspringSelector(getSelector(rs.getString("offspringselector"),rs.getString("offspringparam")))
                            .alterers(
                                    getAlterer1(rs.getInt("id"),con), getListOfAlterers(rs.getInt("id"), con))
                            .build();

		// Create evolution statistics consumer.
		final EvolutionStatistics<Double, ?>
			statistics = EvolutionStatistics.ofNumber();

		final Phenotype<IntegerGene, Double> best = engine.stream()
			// Truncate the evolution stream after 7 "steady"
			// generations.
			.limit(getPredicate(rs.getString("limitador"),rs.getString("paramLimitador")))
			// The evolution will stop after maximal 100
			// generations.
			.limit(rs.getLong("maxCorridas"))
			// Update the evaluation statistics after
			// each generation
			.peek(statistics)
			// Collect (reduce) the evolution stream to
			// its best phenotype.
			.collect(toBestPhenotype());
                System.out.println("Best: "+best);
                con.ejecutarSQL("insert into tesis.salida (idconfiguracion,fitness,vector) values ("+rs.getString("id")+","+best.getFitness()+",'"+best.getGenotype()+"')");
                }
	}
        
        public void llamadoEstatico(Vector<Investigador> investigadores,Driver driver,String filtro,int cantidad){
                            //final int nitems = 15;
		//final double kssize = nitems*100.0/3.0;
                //Miembro.setInvestigadores(investigadores);

		//final FuncionFitness ff = new FuncionFitness(Stream.generate(Miembro::random).limit(nitems).toArray(Miembro[]::new));
                final FuncionFitness ff = new FuncionFitness(investigadores,driver,filtro);

		// Configure and build the evolution engine.
		final Engine<IntegerGene, Double> engine = Engine
			.builder(ff, Genotype.of(IntegerChromosome.of(0, investigadores.size()-1),cantidad))
			.populationSize(10)
			.survivorsSelector(new TournamentSelector<>(5))
			.offspringSelector(new RouletteWheelSelector<>())
			.alterers(
				new Mutator<>(0.115),
				new SinglePointCrossover<>(0.16))
			.build();

		// Create evolution statistics consumer.
		final EvolutionStatistics<Double, ?>
			statistics = EvolutionStatistics.ofNumber();

		final Phenotype<IntegerGene, Double> best = engine.stream()
			// Truncate the evolution stream after 7 "steady"
			// generations.
			.limit(bySteadyFitness(100))
			// The evolution will stop after maximal 100
			// generations.
			.limit(1000)
			// Update the evaluation statistics after
			// each generation
			.peek(statistics)
			// Collect (reduce) the evolution stream to
			// its best phenotype.
			.collect(toBestPhenotype());

		System.out.println(statistics);
		System.out.println("Best: "+best);
        }

        
    private Alterer<IntegerGene,Double> getAlterer1(int idConfig, ConexionMySQL con){
       String sql="select * from alteradores where idConfiguracion="+idConfig+" order by id";
        System.out.println(sql);
        ResultSet rs=con.ejecutarSQLSelect(sql);
            try {
                if(rs.next()){
                   System.out.println(rs.getString("tipo")+","+rs.getDouble("probabilidad")+","+rs.getInt("orden"));
                    return getAlterer(rs.getString("tipo"),rs.getDouble("probabilidad"),rs.getInt("orden"));
                } else return null;
            } catch (SQLException ex) {
                Logger.getLogger(Algoritmo.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
    };
    
    private Alterer<IntegerGene, Double>[] getListOfAlterers(int idConfig, ConexionMySQL con) {
        Alterer<IntegerGene, Double>[] resultado;
        resultado = new Alterer[getCantidadAlteradores(idConfig, con)];
        String sql="select * from alteradores where idConfiguracion="+idConfig+" order by id";
        System.out.println(sql);
        int i=0;
        ResultSet rs=con.ejecutarSQLSelect(sql);
            try {
                rs.next();
                if(rs.next()){
                    resultado[i]= getAlterer(rs.getString("tipo"),rs.getDouble("probabilidad"),rs.getInt("orden"));
                    i++;
                }
                   
            } catch (SQLException ex) {
                Logger.getLogger(Algoritmo.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            if(i==0)
                return null;
            else
                return (Alterer<IntegerGene, Double>[]) resultado;
    }    
    private Selector<IntegerGene, Double> getSelector(String tipo, String parametro) {
        if(parametro!=null){
        if(tipo.equalsIgnoreCase("BoltzmannSelector"))
            return new BoltzmannSelector(Double.valueOf(parametro));
        else if (tipo.equalsIgnoreCase("ExponentialRankSelector"))
            return new ExponentialRankSelector(Double.valueOf(parametro));
        else if (tipo.equalsIgnoreCase("LinearRankSelector"))
            return new LinearRankSelector(Double.valueOf(parametro));
        else if (tipo.equalsIgnoreCase("TournamentSelector"))
            return new TournamentSelector(Integer.valueOf(parametro));
        }else{
        if(tipo.equalsIgnoreCase("BoltzmannSelector"))
            return new BoltzmannSelector();
        else if (tipo.equalsIgnoreCase("ExponentialRankSelector"))
            return new ExponentialRankSelector();
        else if (tipo.equalsIgnoreCase("LinearRankSelector"))
            return new LinearRankSelector();
        else if (tipo.equalsIgnoreCase("MonteCarloSelector"))
            return new MonteCarloSelector();
        else if (tipo.equalsIgnoreCase("RouletteWheelSelector"))
            return new RouletteWheelSelector();
        else if (tipo.equalsIgnoreCase("StochasticUniversalSelector"))
            return new StochasticUniversalSelector();
         else if (tipo.equalsIgnoreCase("TournamentSelector"))
            return new TournamentSelector();
        else if (tipo.equalsIgnoreCase("TruncationSelector"))
            return new TruncationSelector();
        }
        return null;
    }

    private Alterer<IntegerGene, Double> getAlterer(String tipo, Double probabilidad,Integer n) {
        if(n!=null &&  n!=0){
            if(tipo.equalsIgnoreCase("MultiPointCrossover"))
                return new MultiPointCrossover(probabilidad,n);
        }    
        else if(probabilidad!=null){
            if(tipo.equalsIgnoreCase("GaussianMutator"))
                return new GaussianMutator(probabilidad);
            else if(tipo.equalsIgnoreCase("MeanAlterer"))
                return new MeanAlterer(probabilidad); 
            else if(tipo.equalsIgnoreCase("MultiPointCrossover"))
                return new MultiPointCrossover(probabilidad); 
            else if(tipo.equalsIgnoreCase("Mutator"))
                return new Mutator(probabilidad); 
            else if(tipo.equalsIgnoreCase("PartiallyMatchedCrossover"))
                return new PartiallyMatchedCrossover(probabilidad); 
            else if(tipo.equalsIgnoreCase("SinglePointCrossover"))
                return new SinglePointCrossover(probabilidad); 
            else if(tipo.equalsIgnoreCase("SinglePointCrossover"))
                return new SwapMutator(probabilidad); 
        }else{
            if(tipo.equalsIgnoreCase("GaussianMutator"))
                return new GaussianMutator();
            else if(tipo.equalsIgnoreCase("MeanAlterer"))
                return new MeanAlterer(); 
            else if(tipo.equalsIgnoreCase("MultiPointCrossover"))
                return new MultiPointCrossover(); 
            else if(tipo.equalsIgnoreCase("Mutator"))
                return new Mutator(); 
           else if(tipo.equalsIgnoreCase("SinglePointCrossover"))
                return new SinglePointCrossover(); 
            else if(tipo.equalsIgnoreCase("SinglePointCrossover"))
                return new SwapMutator(); 
        }
        return null;
    }

    private int getCantidadAlteradores(int idConfig, ConexionMySQL con) {
       ResultSet rs= con.ejecutarSQLSelect("select count(*) as cantidad from alteradores where idConfiguracion="+idConfig);
            try {
                if(rs.next())
                    return (rs.getInt("cantidad")-1);
                else return 0;
                        } catch (SQLException ex) {
                Logger.getLogger(Algoritmo.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
    }

    private Predicate<? super EvolutionResult<IntegerGene, Double>> getPredicate(String tipo, String parametro) {
        if(tipo.equalsIgnoreCase("bySteadyFitness"))
            return bySteadyFitness(Integer.valueOf(parametro));
        else if(tipo.equalsIgnoreCase("byFixedGeneration"))
            return byFixedGeneration(Long.valueOf(parametro));
        else if(tipo.equalsIgnoreCase("byExecutionTime"))
            return  byExecutionTime(Integer.valueOf(parametro));
        else return null;
    }

    private Predicate<? super EvolutionResult<IntegerGene, Double>> byExecutionTime(Integer valueOf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}