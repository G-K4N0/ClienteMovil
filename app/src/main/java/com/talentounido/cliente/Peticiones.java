package com.talentounido.cliente;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import modelo.Respuesta;

public class Peticiones {

    private String baseUrl = "https://centroapi.azurewebsites.net/";
    RequestQueue peticion;
    JSONObject token;
    public Peticiones(Context context) {
        peticion = Volley.newRequestQueue(context);
        token = new JSONObject();
    }

    public JSONObject login(String user, String pwd) throws JSONException {
        String url = baseUrl + "login";
        JSONObject credentials = new JSONObject();

        credentials.put("nickname", user);
        credentials.put("password", pwd);

        JsonObjectRequest loginRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                credentials,
                response -> {
                   if (response.has("token")){
                       try {
                           token.put("token",response.getString("token"));
                       } catch (JSONException e) {
                           throw new RuntimeException(e);
                       }
                   }
                },
                error -> {
                    try {
                        token.put("error",error.getMessage());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        peticion.add(loginRequest);
        return token;
    }
}
