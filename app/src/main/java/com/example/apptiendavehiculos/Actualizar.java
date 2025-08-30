package com.example.apptiendavehiculos;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Actualizar extends AppCompatActivity {

    EditText edtId, edtMarca, edtModelo, edtColor, edtPrecio, edtPlaca;
    Button btnActualizar;

    private final String URL = "http://26.159.71.121:3000/vehiculos/";

    RequestQueue requestQueue;

    private void loadUI(){
        edtId = findViewById(R.id.edtid);
        edtMarca = findViewById(R.id.edtMarca);
        edtModelo = findViewById(R.id.edtModelo);
        edtColor = findViewById(R.id.edtColor);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtPlaca = findViewById(R.id.edtPlaca);
        btnActualizar = findViewById(R.id.btnActualizar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            return insets;
        });

        loadUI();

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataWS();
            }
        });
    }

    private void updateDataWS(){
        requestQueue = Volley.newRequestQueue(this);

        // Crear el objeto JSON con los datos
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("marca", edtMarca.getText().toString());
            jsonObject.put("modelo", edtModelo.getText().toString());
            jsonObject.put("color", edtColor.getText().toString());
            jsonObject.put("precio", edtPrecio.getText().toString());
            jsonObject.put("placa", edtPlaca.getText().toString());
        }catch (Exception error){
            Log.e("Error en JSON", error.toString());
        }

        // Concatenamos el ID al final de la URL
        String urlFinal = URL + edtId.getText().toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,   // 👈 aquí PUT en vez de POST
                urlFinal,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Vehículo actualizado", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error en WS: ", volleyError.toString());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}
