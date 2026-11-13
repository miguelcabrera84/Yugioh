package com.example.yugioh;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LectorCSV {

    public static ListaCircularDoble<Carta> cargarCartas(String rutaRecurso) {
        ListaCircularDoble<Carta> lista = new ListaCircularDoble<>();

        try (InputStream is = LectorCSV.class.getResourceAsStream(rutaRecurso);

             BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {



            String linea = br.readLine();

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) continue;



                String[] cols = linea.split(",", -1);



                if (cols.length >= 9) {

                    String nombre = limpiar(cols[0]);

                    String atributo = limpiar(cols[1]);

                    int nivel = parsearEntero(cols[2]);

                    String tipo = limpiar(cols[3]);

                    int atk = parsearEntero(cols[4]);

                    int def = parsearEntero(cols[5]);

                    String rareza = limpiar(cols[6]);

                    String descripcion = limpiar(cols[7]);

                    String id = limpiar(cols[8]);



                    Carta carta = new Carta(nombre, atributo, nivel, tipo, atk, def, rareza, descripcion, id);

                    lista.insertaFinal(carta);

                }

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return lista;

    }



    private static String limpiar(String texto) {

        return texto == null ? "" : texto.replace("\"", "").trim();

    }



    private static int parsearEntero(String texto) {

        try {

            String limpio = limpiar(texto);

            if (limpio.isEmpty() || limpio.equalsIgnoreCase("N/A") || limpio.equals("?")) {

                return -1;

            }

            return (int) Double.parseDouble(limpio);

        } catch (NumberFormatException e) {

            return -1;

        }

    }

}