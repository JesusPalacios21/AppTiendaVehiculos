package com.example.apptiendavehiculos;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listar extends AppCompatActivity {

    ListView lstvehiculos;
    private final String URL = "http://26.159.71.121:3000/vehiculos";

    //Canal de comunicacion
    RequestQueue requestQueue;

    private void loadUI(){
        lstvehiculos = findViewById(R.id.lstVehiculos);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            //Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadUI();
        getData();

    }//On create

    /**
     * Obtiene los datos del Web Service
     */
    private void getData(){
        //1. Habilitar un canal de comunicacion
        requestQueue = Volley.newRequestQueue(this);
        //2. Preparar datos del servicio
        JsonArrayRequest jsonArrayRequest =  new JsonArrayRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        //Log.d("Datos recibidos:", jsonArray.toString());
                        renderData(jsonArray);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error en el servicio:", volleyError.toString());
                    }
                }
        );

        //3. Ejecutar la solicitud
        requestQueue.add(jsonArrayRequest);

    }//getData

    private void renderData(JSONArray jsonArray){
        try{
            ArrayList<String> listavehiculos = new ArrayList<>(); //Contenedor de datos

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extraemos todos los campos
                String id = jsonObject.getString("id");
                String marca = jsonObject.getString("marca");
                String modelo = jsonObject.getString("modelo");
                String color = jsonObject.getString("color");
                String precio = jsonObject.getString("precio");
                String placa = jsonObject.getString("placa");

                // Armamos un string bonito y legible
                String info = "ID: " + id + "\n" +
                        "Marca: " + marca + "\n" +
                        "Modelo: " + modelo + "\n" +
                        "Color: " + color + "\n" +
                        "Precio: $" + precio + "\n" +
                        "Placa: " + placa;

                listavehiculos.add(info);
            }

            // Ahora cada item se ve como una ficha con saltos de línea
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    listavehiculos
            );

            lstvehiculos.setAdapter(adapter);

        }catch(Exception error){
            Log.e("Error renderizado:", error.toString());
        }
    }


}