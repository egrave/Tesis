/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.util.HashSet;
import java.util.Set;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 *
 * @author Edu
 */
@NodeEntity(label="investigador")
public class Investigador extends Entity{
    String Nombre;
    String TipoInstitucionTrabajo;
    String InstitucionDeTrabajo;
    String CuitOPasaporte;
    String Sexo;
    String DisciplinaActuacion;
    String ProvinciaResidencia;
    String RangoEtario;
    String MaximoNivelEducativo;
    String RangoDeActualizacionCV;
    String Nacionalidad;
    String ProvinciaLugarDeTrabajo;
    String Apellido;
    String Identificador;
    String GranAreaActuacion;

    public Investigador(String Nombre, String TipoInstitucionTrabajo, String InstitucionDeTrabajo, String CuitOPasaporte, String Sexo, String DisciplinaActuacion, String ProvinciaResidencia, String RangoEtario, String MaximoNivelEducativo, String RangoDeActualizacionCV, String Nacionalidad, String ProvinciaLugarDeTrabajo, String Apellido, String Identificador, String GranAreaActuacion) {
        this.Nombre = Nombre;
        this.TipoInstitucionTrabajo = TipoInstitucionTrabajo;
        this.InstitucionDeTrabajo = InstitucionDeTrabajo;
        this.CuitOPasaporte = CuitOPasaporte;
        this.Sexo = Sexo;
        this.DisciplinaActuacion = DisciplinaActuacion;
        this.ProvinciaResidencia = ProvinciaResidencia;
        this.RangoEtario = RangoEtario;
        this.MaximoNivelEducativo = MaximoNivelEducativo;
        this.RangoDeActualizacionCV = RangoDeActualizacionCV;
        this.Nacionalidad = Nacionalidad;
        this.ProvinciaLugarDeTrabajo = ProvinciaLugarDeTrabajo;
        this.Apellido = Apellido;
        this.Identificador = Identificador;
        this.GranAreaActuacion = GranAreaActuacion;
    }

    public Investigador() {
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getTipoInstitucionTrabajo() {
        return TipoInstitucionTrabajo;
    }

    public void setTipoInstitucionTrabajo(String TipoInstitucionTrabajo) {
        this.TipoInstitucionTrabajo = TipoInstitucionTrabajo;
    }

    public String getInstitucionDeTrabajo() {
        return InstitucionDeTrabajo;
    }

    public void setInstitucionDeTrabajo(String InstitucionDeTrabajo) {
        this.InstitucionDeTrabajo = InstitucionDeTrabajo;
    }

    public String getCuitOPasaporte() {
        return CuitOPasaporte;
    }

    public void setCuitOPasaporte(String CuitOPasaporte) {
        this.CuitOPasaporte = CuitOPasaporte;
    }

    public String getSexo() {
        return Sexo;
    }

    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    public String getDisciplinaActuacion() {
        return DisciplinaActuacion;
    }

    public void setDisciplinaActuacion(String DisciplinaActuacion) {
        this.DisciplinaActuacion = DisciplinaActuacion;
    }

    public String getProvinciaResidencia() {
        return ProvinciaResidencia;
    }

    public void setProvinciaResidencia(String ProvinciaResidencia) {
        this.ProvinciaResidencia = ProvinciaResidencia;
    }

    public String getRangoEtario() {
        return RangoEtario;
    }

    public void setRangoEtario(String RangoEtario) {
        this.RangoEtario = RangoEtario;
    }

    public String getMaximoNivelEducativo() {
        return MaximoNivelEducativo;
    }

    public void setMaximoNivelEducativo(String MaximoNivelEducativo) {
        this.MaximoNivelEducativo = MaximoNivelEducativo;
    }

    public String getRangoDeActualizacionCV() {
        return RangoDeActualizacionCV;
    }

    public void setRangoDeActualizacionCV(String RangoDeActualizacionCV) {
        this.RangoDeActualizacionCV = RangoDeActualizacionCV;
    }

    public String getNacionalidad() {
        return Nacionalidad;
    }

    public void setNacionalidad(String Nacionalidad) {
        this.Nacionalidad = Nacionalidad;
    }

    public String getProvinciaLugarDeTrabajo() {
        return ProvinciaLugarDeTrabajo;
    }

    public void setProvinciaLugarDeTrabajo(String ProvinciaLugarDeTrabajo) {
        this.ProvinciaLugarDeTrabajo = ProvinciaLugarDeTrabajo;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(String Identificador) {
        this.Identificador = Identificador;
    }

    public String getGranAreaActuacion() {
        return GranAreaActuacion;
    }

    public void setGranAreaActuacion(String GranAreaActuacion) {
        this.GranAreaActuacion = GranAreaActuacion;
    }

    
    public String getInstitucionDeTrabajoEscape() {
        if (InstitucionDeTrabajo != null) {
        String aux = InstitucionDeTrabajo;
        char [] auxchar = aux.toCharArray();
        for(int i = 0; i<aux.length(); i++)
            if (auxchar[i] == '\"'){
            auxchar[i] = '\'';
            }
        aux = String.valueOf(auxchar);
        return aux;
    }
        return null;
    }
    
}
