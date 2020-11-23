package erd.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Parser {

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

        System.out.println("**** ENTIDADES FUERTES ****");
        Iterator it = entity(JSONDoc, "entidades");
        System.out.println("**** ENTIDADES DEBILES ****");
        entity(JSONDoc, "debiles");

        // Solo recuperar los objetos que describen entidades
        JSONArray relations = JSONDoc.getJSONArray("relaciones");

        System.out.println("**** RELACIONES ****");
        relation(it,relations);

        
        
    }

    static Iterator entity(JSONObject JSONDoc, String type) {
        
        JSONArray entidades = JSONDoc.getJSONArray(type);
        System.out.println(entidades);
        
        Iterator it = entidades.iterator();
        // Procesar cada una de las entidades
        while (it.hasNext()) {

            JSONObject entidad = (JSONObject) it.next();  
            
            if (type == "debiles") {
                System.out.println(entidad.getString("fuerte").toUpperCase());
            }

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

        return it;
    }

    static void relation(Iterator it, JSONArray relations) {
        it = relations.iterator();

        while (it.hasNext()) {
            JSONObject rel = (JSONObject) it.next();

            //System.out.println( rel );
            //System.out.println( rel.names());
            System.out.println(rel.getString("nombre"));

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
