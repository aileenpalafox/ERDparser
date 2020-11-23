/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package erd.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author rnavarro
 */
public class ERDParser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        FileReader fp = new FileReader("university-erd.json");

        // Crear el tokenizador del documento JSON  
        JSONTokener tokenizer = new JSONTokener(fp);

        // Objeto principal que contiene todos los componentes
        // del diagrama ERD
        JSONObject JSONDoc = new JSONObject(tokenizer);

        //Obtenet los nombres de los objetos 
        JSONArray names = JSONDoc.names();
        System.out.println(names);

        // Solo recuperar los objetos que describen entidades
        JSONArray entidades = JSONDoc.getJSONArray("entidades");
        System.out.println(entidades);
        
        Iterator it = entidades.iterator();

        // Procesar cada una de las entidades
        while (it.hasNext()) {

            JSONObject entidad = (JSONObject) it.next();

            //names = entidad.names();
            // Para cada entidad, mostrar su nombre
            String entityName = entidad.getString("nombre");
            System.out.println(entityName);

            // Para cada entidad, mostrar los atributos
            JSONArray atributos = entidad.getJSONArray("atributos");
            Iterator attribIt = atributos.iterator();

            while (attribIt.hasNext()) {
                JSONObject atributo = (JSONObject) attribIt.next();
                System.out.print("\t");
                System.out.print(atributo.getString("nombre"));

                if (atributo.getInt("tipo") == 1) {
                    System.out.println(" *");
                } else {
                    System.out.println();
                }

            }

        }

        // Solo recuperar los objetos que describen entidades
        JSONArray relations = JSONDoc.getJSONArray("relaciones");

        System.out.println("**** RELACIONES ****");

        it = relations.iterator();

        while (it.hasNext()) {
            JSONObject rel = (JSONObject) it.next();

            //System.out.println( rel );
            //System.out.println( rel.names());
            System.out.println( rel.getString("nombre") );

            JSONArray cards = rel.getJSONArray("cardinalidades");

            int n = cards.length();

            for (int i = 0; i < n; i++) {
                JSONObject e1 = cards.getJSONObject(i);

                System.out.printf("\t%s (%s,%s)\n", e1.getString("entidad"),
                        e1.getString("min"),
                        e1.getString("max"));

            }

        }

    }

}
