/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis;

import POJO.Investigador;
import java.util.Vector;
import java.util.function.Predicate;
import org.jenetics.Alterer;
import org.jenetics.BoltzmannSelector;
import org.jenetics.ExponentialRankSelector;
import org.jenetics.GaussianMutator;
import org.jenetics.IntegerGene;
import org.jenetics.LinearRankSelector;
import org.jenetics.MeanAlterer;
import org.jenetics.MonteCarloSelector;
import org.jenetics.MultiPointCrossover;
import org.jenetics.Mutator;
import org.jenetics.PartiallyMatchedCrossover;
import org.jenetics.RouletteWheelSelector;
import org.jenetics.Selector;
import org.jenetics.SinglePointCrossover;
import org.jenetics.StochasticUniversalSelector;
import org.jenetics.SwapMutator;
import org.jenetics.TournamentSelector;
import org.jenetics.TruncationSelector;
import org.jenetics.engine.EvolutionResult;
import static org.jenetics.engine.limit.byFixedGeneration;
import static org.jenetics.engine.limit.bySteadyFitness;
import org.neo4j.driver.v1.Driver;

/**
 *
 * @author Leo
 */
public class Tarea {

    private final int diametroRed = 8;
    private String filtroRelacion;
    private int cantidad;
    private Driver driver;
    private Vector<Investigador> investigadores;

//Configuraciones
    private int population;
    private String survivorselector;
    private String survivorparam;
    private String offspringselector;
    private String offspringparam;
    private long maxCorridas;
    private String limitador;
    private String paramLimitador;

    //Alteradores
    private String tipoAlterador;
    private Double probabilidad;
    private Integer orden;

    public Tarea(String filtroRelacion, int cantidad, Driver driver, Vector<Investigador> investigadores, int population, String survivorselector, String survivorparam, String offspringselector, String offspringparam, long maxCorridas, String limitador, String paramLimitador, String tipoAlterador, Double probabilidad, Integer orden) {
        this.filtroRelacion = filtroRelacion;
        this.cantidad = cantidad;
        this.driver = driver;
        this.investigadores = investigadores;
        this.population = population;
        this.survivorselector = survivorselector;
        this.survivorparam = survivorparam;
        this.offspringselector = offspringselector;
        this.offspringparam = offspringparam;
        this.maxCorridas = maxCorridas;
        this.limitador = limitador;
        this.paramLimitador = paramLimitador;
        this.tipoAlterador = tipoAlterador;
        this.probabilidad = probabilidad;
        this.orden = orden;
    }

    public Vector<Investigador> getInvestigadores() {
        return investigadores;
    }

    public int getDiametroRed() {
        return diametroRed;
    }

    public String getFiltroRelacion() {
        return filtroRelacion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getPopulation() {
        return population;
    }

    public String getSurvivorselector() {
        return survivorselector;
    }

    public String getSurvivorparam() {
        return survivorparam;
    }

    public String getOffspringselector() {
        return offspringselector;
    }

    public String getOffspringparam() {
        return offspringparam;
    }

    public long getMaxCorridas() {
        return maxCorridas;
    }

    public String getLimitador() {
        return limitador;
    }

    public String getParamLimitador() {
        return paramLimitador;
    }

    public String getTipoAlterador() {
        return tipoAlterador;
    }

    public Double getProbabilidad() {
        return probabilidad;
    }

    public Integer getOrden() {
        return orden;
    }

    public Selector<IntegerGene, Double> getSurvivorsSelector() {
        return this.getSelector(survivorselector, survivorparam);
    }

    public Selector<IntegerGene, Double> getOffspringSelector() {
        return this.getSelector(offspringselector, offspringparam);
    }

    private Selector<IntegerGene, Double> getSelector(String tipo, String parametro) {
        if (parametro != null && !parametro.equals("")) {
            if (tipo.equalsIgnoreCase("BoltzmannSelector")) {
                return new BoltzmannSelector(Double.valueOf(parametro));
            } else if (tipo.equalsIgnoreCase("ExponentialRankSelector")) {
                return new ExponentialRankSelector(Double.valueOf(parametro));
            } else if (tipo.equalsIgnoreCase("LinearRankSelector")) {
                return new LinearRankSelector(Double.valueOf(parametro));
            } else if (tipo.equalsIgnoreCase("TournamentSelector")) {
                return new TournamentSelector(Integer.valueOf(parametro));
            }
        } else {
            if (tipo.equalsIgnoreCase("BoltzmannSelector")) {
                return new BoltzmannSelector();
            } else if (tipo.equalsIgnoreCase("ExponentialRankSelector")) {
                return new ExponentialRankSelector();
            } else if (tipo.equalsIgnoreCase("LinearRankSelector")) {
                return new LinearRankSelector();
            } else if (tipo.equalsIgnoreCase("MonteCarloSelector")) {
                return new MonteCarloSelector();
            } else if (tipo.equalsIgnoreCase("RouletteWheelSelector")) {
                return new RouletteWheelSelector();
            } else if (tipo.equalsIgnoreCase("StochasticUniversalSelector")) {
                return new StochasticUniversalSelector();
            } else if (tipo.equalsIgnoreCase("TournamentSelector")) {
                return new TournamentSelector();
            } else if (tipo.equalsIgnoreCase("TruncationSelector")) {
                return new TruncationSelector();
            }
        }
        return null;
    }

    private Alterer<IntegerGene, Double> getAlterer(String tipo, Double probabilidad, Integer n) {
        if (n != null && n != 0) {
            if (tipo.equalsIgnoreCase("MultiPointCrossover")) {
                return new MultiPointCrossover(probabilidad, n);
            }
        } else if (probabilidad != null) {
            if (tipo.equalsIgnoreCase("GaussianMutator")) {
                return new GaussianMutator(probabilidad);
            } else if (tipo.equalsIgnoreCase("MeanAlterer")) {
                return new MeanAlterer(probabilidad);
            } else if (tipo.equalsIgnoreCase("MultiPointCrossover")) {
                return new MultiPointCrossover(probabilidad);
            } else if (tipo.equalsIgnoreCase("Mutator")) {
                return new Mutator(probabilidad);
            } else if (tipo.equalsIgnoreCase("PartiallyMatchedCrossover")) {
                return new PartiallyMatchedCrossover(probabilidad);
            } else if (tipo.equalsIgnoreCase("SinglePointCrossover")) {
                return new SinglePointCrossover(probabilidad);
            } else if (tipo.equalsIgnoreCase("SinglePointCrossover")) {
                return new SwapMutator(probabilidad);
            }

        } else {
            if (tipo.equalsIgnoreCase("GaussianMutator")) {
                return new GaussianMutator();
            } else if (tipo.equalsIgnoreCase("MeanAlterer")) {
                return new MeanAlterer();
            } else if (tipo.equalsIgnoreCase("MultiPointCrossover")) {
                return new MultiPointCrossover();
            } else if (tipo.equalsIgnoreCase("Mutator")) {
                return new Mutator();
            } else if (tipo.equalsIgnoreCase("SinglePointCrossover")) {
                return new SinglePointCrossover();
            } else if (tipo.equalsIgnoreCase("SinglePointCrossover")) {
                return new SwapMutator();
            }
        }
        return null;
    }

    public Alterer<IntegerGene, Double> getAlterador() {
        return getAlterer(tipoAlterador, probabilidad, orden);
    }

    public Predicate<? super EvolutionResult<IntegerGene, Double>> getPredicate() {
        if (limitador.equalsIgnoreCase("bySteadyFitness")) {
            return bySteadyFitness(Integer.valueOf(paramLimitador));
        } else if (limitador.equalsIgnoreCase("byFixedGeneration")) {
            return byFixedGeneration(Long.valueOf(paramLimitador));
        } else if (limitador.equalsIgnoreCase("byExecutionTime")) {
            return byExecutionTime(Integer.valueOf(paramLimitador));
        } else {
            return null;
        }
    }

    private Predicate<? super EvolutionResult<IntegerGene, Double>> byExecutionTime(Integer valueOf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
