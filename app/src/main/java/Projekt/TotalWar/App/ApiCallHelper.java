package Projekt.TotalWar.App;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ApiCallHelper {

    private static final String TAG = "ApiCallHelper";
    private RequestQueue requestQueue;
    private Context context;

    public ApiCallHelper(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void makeGetRequest(String apiUrl, final ApiCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onApiCompleted(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onApiError(error.toString());
                    }
                });

        requestQueue.add(stringRequest);
    }

    public void makePostRequest(String apiUrl, JSONObject requestBody, final ApiCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, apiUrl, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onApiCompleted(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onApiError(error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public void makePutRequest(String apiUrl, JSONObject requestBody, final ApiCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, apiUrl, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onApiCompleted(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onApiError(error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public void makeDeleteRequest(String apiUrl, final ApiCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onApiCompleted(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onApiError(error.toString());
                    }
                });

        requestQueue.add(stringRequest);
    }

    public interface ApiCallback {
        void onApiCompleted(String result);

        void onApiError(String error);
    }
}