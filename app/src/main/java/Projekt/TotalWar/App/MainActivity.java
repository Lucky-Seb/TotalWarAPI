package Projekt.TotalWar.App;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ApiCallHelper apiCallHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiCallHelper = new ApiCallHelper(this);

        // Example usage for a GET request
        String apiUrlGet = "http://10.131.213.178:8080/factions";
        apiCallHelper.makeGetRequest(apiUrlGet, new ApiCallHelper.ApiCallback() {
            @Override
            public void onApiCompleted(String result) {
                // Handle the API response here
                Log.d(TAG, "GET Request Response: " + result);
            }

            @Override
            public void onApiError(String error) {
                // Handle API error
                Log.e(TAG, "GET Request Error: " + error);
            }
        });

        // Example usage for a POST request
        String apiUrlPost = "http://10.131.213.178:8080/factions/";
        JSONObject postRequestBody = new JSONObject();
        try {
            postRequestBody.put("factionName", "Empire"); // Replace "Empire" with the actual value of factionName
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiCallHelper.makePostRequest(apiUrlPost, postRequestBody, new ApiCallHelper.ApiCallback() {
            @Override
            public void onApiCompleted(String result) {
                // Handle the API response here
                Log.d(TAG, "POST Request Response: " + result);
            }

            @Override
            public void onApiError(String error) {
                // Handle API error
                Log.e(TAG, "POST Request Error: " + error);
            }
        });

        // Example usage for a PUT request
        String apiUrlPut = "http://10.131.213.178:8080/factions/{factionId}";
        JSONObject putRequestBody = new JSONObject();
        try {
            putRequestBody.put("factionName", "UpdatedEmpire"); // Replace "UpdatedEmpire" with the new value of factionName
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiCallHelper.makePutRequest(apiUrlPut, putRequestBody, new ApiCallHelper.ApiCallback() {
            @Override
            public void onApiCompleted(String result) {
                // Handle the API response here
                Log.d(TAG, "PUT Request Response: " + result);
            }

            @Override
            public void onApiError(String error) {
                // Handle API error
                Log.e(TAG, "PUT Request Error: " + error);
            }
        });

        // Example usage for a DELETE request
        String apiUrlDelete = "http://10.131.213.178:8080/factions/{factionId}";

        apiCallHelper.makeDeleteRequest(apiUrlDelete, new ApiCallHelper.ApiCallback() {
            @Override
            public void onApiCompleted(String result) {
                // Handle the API response here
                Log.d(TAG, "DELETE Request Response: " + result);
            }

            @Override
            public void onApiError(String error) {
                // Handle API error
                Log.e(TAG, "DELETE Request Error: " + error);
            }
        });
    }
}