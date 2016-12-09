/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xml;

/**
 *
 * @author Eduardo
 */
import java.io.FileReader;
import java.io.BufferedReader;
import javax.swing.JTextPane;

public class Lector{
    
public static void leer(String ruta,JTextPane panel){

try
{
//Creamos un archivo FileReader que obtiene lo que tenga el archivo
 panel.setText("");
FileReader lector=new FileReader(ruta);
String texto="";
String buffer="";
//El contenido de lector se guarda en un BufferedReader
BufferedReader contenido=new BufferedReader(lector);

//Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos

while((texto=contenido.readLine())!=null)
{
    buffer= buffer+"\n"+texto;
}
    panel.setText(buffer);

}

//Si se causa un error al leer cae aqui
catch(Exception e)
{
System.out.println("Error al leer");
}
}
}