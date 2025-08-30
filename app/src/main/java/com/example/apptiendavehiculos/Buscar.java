package com.example.apptiendavehiculos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Buscar extends AppCompatActivity {

    EditText edtIdBuscar;
    Button btnBuscar;
    TextView txtResultado;

    private final String URL_BASE = "http://26.159.71.121:3000/vehiculos/";

    RequestQueue requestQueue;

    private void loadUI(){
        edtIdBuscar = findViewById(R.id.edtIdBuscar);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtResultado = findViewById(R.id.txtResultado);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buscar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> insets);

        loadUI();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarVehiculo();
            }
        });
    }

    private void buscarVehiculo(){
        requestQueue = Volley.newRequestQueue(this);

        String id = edtIdBuscar.getText().toString();

        if(id.isEmpty()){
            Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = URL_BASE + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            String datos = "Marca: " + response.getString("marca") + "\n" +
                                    "Modelo: " + response.getString("modelo") + "\n" +
                                    "Color: " + response.getString("color") + "\n" +
                                    "Precio: " + response.getString("precio") + "\n" +
                                    "Placa: " + response.getString("placa");

                            txtResultado.setText(datos);
                        }catch (Exception e){
                            Log.e("Error JSON:", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error WS:", volleyError.toString());
                        Toast.makeText(getApplicationContext(), "Vehículo no encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
