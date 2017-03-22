/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis;

import baseDeDatos.ConexionMySQL;
import java.sql.ResultSet;

/**
 *
 * @author Leo
 */
public final class ManagerDB {

    private static String user = "root";
    private static String password = "1234";

    private static ManagerDB instance = null;
    private static ConexionMySQL conexion;

    private ManagerDB() {
        conexion = new ConexionMySQL();
        conexion.crearConexion(user, password);
    }

    public static ManagerDB getInstance() {
        if (instance == null) {
            instance = new ManagerDB();
        }
        return instance;
    }

    public ResultSet getConfiguraciones(int idConfiguracion) {
        ResultSet rs = conexion.ejecutarSQLSelect("select * from configuraciones where id=" + idConfiguracion);
        return rs;
    }

    public ResultSet getTodosAteradores() {
        ResultSet rs = conexion.ejecutarSQLSelect("select * from alteradores order by id");
        return rs;
    }
//statistics.getEvolveDuration().getSum()/statistics.getEvolveDuration().getMean() , statistics.getEvolveDuration().getMean()

    public void guardarResultado(int id, double fitness, String vector, int idAlteradores, int comite, String filtro, double generaciones, double tiempo) {
        conexion.ejecutarSQL("insert into tesis.salida (idconfiguracion,fitness,vector,idConfiguracionAlteradores,comite,filtro,generaciones,tiempoPaso) values ("
                + id + "," + fitness + ",'" + vector + "'," + idAlteradores + "," + comite + ",'" + filtro + "'," + generaciones + "," + tiempo + ")");
    }
}
