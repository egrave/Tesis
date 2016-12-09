/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

/**
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class Escritor
{

public static void escribir(String texto, String ruta){    
{


try
{
//Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
File archivo=new File(ruta);


//Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
//FileWriter escribir=new FileWriter(archivo,true);
 Writer output = new BufferedWriter(new FileWriter(archivo));
    try {
      output.write( texto );
    }
    finally {
      output.close();
    }

//Escribimos en el archivo con el metodo write 
//escribir.write(texto);

//Cerramos la conexion
//escribir.close();
}

//Si existe un problema al escribir cae aqui
catch(Exception e)
{
  System.out.println("Error al escribir");
}
}
}
}