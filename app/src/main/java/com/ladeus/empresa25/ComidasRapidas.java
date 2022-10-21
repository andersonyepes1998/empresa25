package com.ladeus.empresa25;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ComidasRapidas extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{

    EditText etidentificacion, etname, etCiudad, etDomicilio, etTelefono;
    CheckBox chactivo;
    RequestQueue rq;
    JsonRequest jrq;
    String ID,NOMBRE,CIUDAD,DOMICILIO, TELEFONO;
    public byte sw;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comidas_rapidas);

        sw = 0;
        getSupportActionBar().hide();
        etidentificacion = findViewById(R.id.etidentificacion);
        etname= findViewById(R.id.etname);
        etCiudad = findViewById(R.id.etCiudad);
        etDomicilio = findViewById(R.id.etDomicilio);
        etTelefono = findViewById(R.id.etTelefono);
        rq = Volley.newRequestQueue(this);
    }


    public void Guardar (View view){
        ID  = etidentificacion.getText().toString();
        NOMBRE = etname.getText().toString();
        CIUDAD = etCiudad.getText().toString();
        DOMICILIO = etDomicilio.getText().toString();
        TELEFONO = etTelefono.getText().toString();
        if (ID.isEmpty() || NOMBRE.isEmpty() || CIUDAD.isEmpty() || DOMICILIO.isEmpty() || TELEFONO.isEmpty()){
            Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            etidentificacion.requestFocus();
        }else{
            if(sw == 0){
                url = "http://172.18.59.67:80/WebService/registroComidas.php";
            }else{
                url = "http://172.18.59.67:80/WebService/actualizaComidas.php";
                sw = 0;
            }

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Limpiar_Campos();
                            Toast.makeText(getApplicationContext(), "Domicilio realizado!", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Domicilio incorrecto!", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ID",etidentificacion.getText().toString().trim());
                    params.put("NOMBRE", etname.getText().toString().trim());
                    params.put("CIUDAD",etCiudad.getText().toString().trim());
                    params.put("DOMICILIO",etDomicilio.getText().toString().trim());
                    params.put("TELEFONO",etTelefono.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);

        }
    }

/*
    public void Eliminar (View view){
        ID  = etidentificacion.getText().toString();
        if (ID.isEmpty()){
            Toast.makeText(this, "El Domicilio es requerido", Toast.LENGTH_SHORT).show();
            etidentificacion.requestFocus();
        }else{

            url = "http://192.168.1.13:80/WebService/eliminaComidas.php";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Limpiar_Campos();
                            Toast.makeText(getApplicationContext(), "El Domicilio fue Eliminado Correctamente!", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Domicilio no se elimino!", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ID",etidentificacion.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);
        }
    }
*/

    public void Anular (View view){
        ID  = etidentificacion.getText().toString();
        if (ID.isEmpty()){
            Toast.makeText(this, "El usuario es requerido", Toast.LENGTH_SHORT).show();
            etidentificacion.requestFocus();
        }else{

            url = "http://172.18.59.67:80/WebService/anulaComida.php";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Limpiar_Campos();
                            Toast.makeText(getApplicationContext(), "Usuario Anulado Correctamente!", Toast.LENGTH_LONG).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Usuario no se anulo!", Toast.LENGTH_LONG).show();
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("ID",etidentificacion.getText().toString().trim());
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(postRequest);

        }
    }


    public  void Consultar(View view){
        ID = etidentificacion.getText().toString();
        if (ID.isEmpty()){
            Toast.makeText(this, "El domicilio es requerido para la busqueda", Toast.LENGTH_SHORT).show();
        }else {
            url = "http://172.18.59.67:80/WebService/consultaComidas.php?ID="+ID;
            jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            rq.add(jrq);
        }
    }


    public void Limpiar(View view){
        Limpiar_Campos();
    }
    public void  Regresar (View view){
        Intent intmain = new Intent(this,MainActivity.class);
        startActivity(intmain);
    }
    private void Limpiar_Campos(){
        sw = 0;
        etidentificacion.setText("");
        etname.setText("");
        etCiudad.setText("");
        etDomicilio.setText("");
        etTelefono.setText("");
        chactivo.setChecked(false);
        etidentificacion.requestFocus();

    }



    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Domicilio no Registrado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        sw = 1;
        Toast.makeText(this, "Domicilio registrado", Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            etname.setText(jsonObject.optString("NOMBRE"));
            etCiudad.setText(jsonObject.optString("CIUDAD"));
            etDomicilio.setText(jsonObject.optString("DOMICILIO"));
            etTelefono.setText((int) jsonObject.optDouble("TELEFONO"));
            if(jsonObject.optString("ACTIVO").equals("si")){
                chactivo.setChecked(true);
            }else{
                chactivo.setChecked(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}