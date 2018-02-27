package tasks.com.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String apiUrl="http://moziru.com/images/manga-clipart-black-and-white-4.jpg";
    Button btn;
    ImageView image;
    String img;
   public ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynk_task);
        initialize();
        action();
    }
    public void initialize(){
        btn= (Button) findViewById(R.id.btn);
        image= (ImageView) findViewById(R.id.image);
    }
    public void action(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask asynkTask=new AsynkTask();
                asynkTask.execute();
            }
        });
    }
    public class AsynkTask extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String flag = "";
            URL url = null;
            try {
                url = new URL(apiUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader input = new InputStreamReader(in);
                int data = input.read();
                while (data != -1) {
                    flag += (char) data;
                    data = input.read();
                    System.out.print(flag);
                }
                return flag;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return flag;


        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try{
            JSONArray jsonArray = new JSONArray();

                JSONObject jsonObject = jsonArray.getJSONObject(0);
                img=jsonObject.getString("image");
                Picasso.with(getApplicationContext())
                        .load(img)
                        .into(image);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
