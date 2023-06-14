package com.talentounido.cliente.PeticionesVolley;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.talentounido.cliente.modelo.RegisterContent;
import com.talentounido.cliente.modelo.Reporte;
import com.talentounido.cliente.modelo.SinRegisterContent;

public class Peticiones {
    private static final String url1 = "http://192.168.137.139:8080/";
    private static final String url2 = "http://192.168.2.41:8080/";
    private static final String remoteUrl="https://centroapi.azurewebsites.net/";
    private static final String BASE_URL = url2;

    private static RequestQueue peticion;

    public static void init(Context context) {
        if (peticion == null) {
            peticion = Volley.newRequestQueue(context);
        }
    }

    public static void login(String user, String pwd, Response.Listener<JSONObject> successListener,Response.ErrorListener errorListener) {
        String url = BASE_URL + "login";

        JSONObject credentials = new JSONObject();
        try {
            credentials.put("user", user);
            credentials.put("password", pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, credentials, successListener,errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        peticion.add(request);
    }

    public static void getLabs(String token, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "labs";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, successListener,errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        peticion.add(request);
    }
    public static void getHorariosByDias(String token, Response.Listener<JSONArray> succesListener, Response.ErrorListener errorListener){
        String url = BASE_URL + "horarios/dias";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null, succesListener,errorListener ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        peticion.add(request);
    }
    public static void getHorariosByDocente(String token,int id, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "horarios/docente/" + id + "/dias";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, successListener,errorListener) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        peticion.add(request);
    }

    public static void getGrupos(String token, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener){
        String url = BASE_URL + "grupos/grupos";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null,successListener, errorListener){
          @Override
          public  Map<String, String> getHeaders(){
              Map<String, String> headers = new HashMap<>();
              headers.put("Authorization", "Bearer " + token);
              return headers;
          }
        };
        peticion.add(request);
    }

    public static void getMaterias(String token, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener){
        String url = BASE_URL + "materias/addHorario";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, successListener, errorListener){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        peticion.add(request);
    }

    public static void getActividades(String token, Response.Listener<JSONObject> succesListener, Response.ErrorListener errorListener){
        String url = BASE_URL + "actividades";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, succesListener, errorListener){
          @Override
          public Map<String, String> getHeaders(){
              Map<String, String> headers = new HashMap<>();
              headers.put("Authorization", "Bearer "+ token);
              return headers;
          }
        };
        peticion.add(request);
    }
    public static void postCreateRegister(String token, RegisterContent content, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        String url = BASE_URL + "registros/create/";

        JSONObject jsonContent = new JSONObject();

        jsonContent.put("idHorario", content.getIdHorario());
        jsonContent.put("idActividad", content.getActividad());
        jsonContent.put("laboratorio", content.getLabName());
        JsonObjectRequest createRequest = new JsonObjectRequest(Request.Method.POST, url,jsonContent,successListener,errorListener){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+ token);
                return headers;
            }
        };
        peticion.add(createRequest);
    }
    public static void postCreateRegisterWithoutSchedule(String token, SinRegisterContent content, Response.Listener<JSONObject> succesListener, Response.ErrorListener errorListener ) throws JSONException {
        String url = BASE_URL + "registros/create/whitout";

        JSONObject jsonContent = new JSONObject();

        jsonContent.put("idGrupo", content.getIdGrupo());
        jsonContent.put("idMateria", content.getIdMateria());
        jsonContent.put("inicia", content.getInicia());
        jsonContent.put("finaliza", content.getFinaliza());
        jsonContent.put("dia", content.getDia());
        jsonContent.put("idActividad", content.getIdActividad());
        jsonContent.put("laboratorio", content.getLab());
        jsonContent.put("idUsuario", content.getIdUsuario());

        JsonObjectRequest createRequest = new JsonObjectRequest(Request.Method.POST, url, jsonContent, succesListener, errorListener){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+ token);
                return headers;
            }
        };
        peticion.add(createRequest);
    }
    public static void postAlumnosAtendidos(String token,int idRegister, int alumnos, Response.Listener<JSONObject> succesListener, Response.ErrorListener errorListener) throws JSONException {
        String url = BASE_URL + "registros/complete/" + idRegister;
        JSONObject alumnosData = new JSONObject();
        alumnosData.put("alumnos", alumnos);

        JsonObjectRequest completeRegister = new JsonObjectRequest(Request.Method.PUT,url, alumnosData,succesListener,errorListener){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer "+ token);
                return headers;
            }
        };
        peticion.add(completeRegister);
    }
    public static void getAvisos(String token, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener) {
        String url = BASE_URL+"avisos";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url,null,successListener,errorListener){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        peticion.add(request);
    }

    public static void postReporte (String token, Reporte reporte, Response.Listener<JSONObject> successListener, Response.ErrorListener errorListener) throws JSONException {
        String url = BASE_URL + "reportes";

        JSONObject data = new JSONObject();
        data.put("idLab",reporte.getLaboratorio());
        data.put("idUsuario",reporte.getIdUsuario());
        data.put("problema",reporte.getTitulo());
        data.put("descripcion", reporte.getProblema());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,data,successListener, errorListener){
            @Override
            public Map<String, String> getHeaders(){
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };
        peticion.add(request);
    }
    public static void setPreference(Context context, String name, String parameter){
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(name,parameter);
        editor.apply();
    }

    public static String getPreference(Context context, String name){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String preference = preferences.getString(name,null);
        if (preference == null || preference.isEmpty()){
            preference = "error";
        }
        return  preference;
    }
}
