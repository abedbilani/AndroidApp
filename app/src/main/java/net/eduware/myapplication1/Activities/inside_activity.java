package net.eduware.myapplication1.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.gson.Gson;

import net.eduware.myapplication1.Modules.AllData;
import net.eduware.myapplication1.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class inside_activity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);
        try {
            String x = new NetworkCall().execute("").get();
            AllData allData = new Gson().fromJson(x, AllData.class);
            String sccId = allData.GetAllSchoolsResult.OperationData.get(0).SchoolID;
            EditText editText = (EditText) findViewById(R.id.json);
            editText.setText(sccId);
        } catch (Exception e) {
        }
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public class NetworkCall extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {

                return run("https://eduflag.eduwareonline.com/Service1.svc/rest/GetAllSchools");

            } catch (Exception ex) {

            }
            return null;
        }
    }

}
