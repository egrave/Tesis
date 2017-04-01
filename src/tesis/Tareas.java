/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tesis;

import java.util.ArrayList;

/**
 *
 * @author Leo
 */
public class Tareas {

    private ArrayList<Tarea> TareasPendientes = new ArrayList<Tarea>();
    private Tarea tarea;

    public Tareas() {
        this.tarea = null;
    }
    
    public synchronized Tarea Get(int NumeroThread) {
        Tarea tareaaux;
        while (TareasPendientes.isEmpty()) {
            try {
                System.out.println("Thread" + NumeroThread + " DORMIDO");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        tareaaux = TareasPendientes.remove(0);
        System.out.println("TAREAS PENDIENTES: " + TareasPendientes.size());
        return tareaaux;
    }

    public synchronized void Put(Tarea t) {
        TareasPendientes.add(t);
        notify(); 
    }
}
