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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Eliminar extends AppCompatActivity {

    EditText edtIdEliminar;
    Button btnEliminar;

    private final String URL_BASE = "http://26.159.71.121:3000/vehiculos/";

    RequestQueue requestQueue;

    private void loadUI(){
        edtIdEliminar = findViewById(R.id.edtIdEliminar);
        btnEliminar = findViewById(R.id.btnEliminar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_eliminar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> insets);

        loadUI();

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarVehiculo();
            }
        });
    }

    private void eliminarVehiculo(){
        requestQueue = Volley.newRequestQueue(this);

        String id = edtIdEliminar.getText().toString();

        if(id.isEmpty()){
            Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = URL_BASE + id;

        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Vehículo eliminado correctamente", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("Error WS:", volleyError.toString());
                        Toast.makeText(getApplicationContext(), "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(stringRequest);
    }
}
