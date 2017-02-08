package a57121035_0.it.montri.homeworkbeforefinal;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText edt_ID,edt_Pass;
    Button btn_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        edt_ID = (EditText) findViewById(R.id.edtID);
        edt_Pass = (EditText) findViewById(R.id.edtPassword);
        btn_Login = (Button) findViewById(R.id.btnLogin);
        //fghjkl;hgfghjklkjhgfx

        

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpClient hc = new DefaultHttpClient();
                HttpResponse hr = null;
                List<NameValuePair> nv = new ArrayList<NameValuePair>();
                nv.add(new BasicNameValuePair("username",edt_ID.getText().toString()));
                nv.add(new BasicNameValuePair("password",edt_Pass.getText().toString()));
                HttpPost hp = new HttpPost("http://10.0.2.2/android/login.php");// http://motori.coolpage.biz/android/login.php
                BufferedReader bf;
                String data;
                try {
                    hp.setEntity(new UrlEncodedFormEntity(nv));
                    hr = hc.execute(hp);
                    bf = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                    data = bf.readLine();
                    JSONArray jsonArray = new JSONArray(data);
                    JSONObject jObject = new JSONObject();
                    for(int i =0;i<jsonArray.length();i++){
                        jObject = jsonArray.getJSONObject(i);
                        if(jObject.getString("username").equals(edt_ID.getText().toString())&&jObject.getString("password").equals(edt_Pass.getText().toString())){
                            Toast.makeText(MainActivity.this, "Welcome "+jObject.getString("full_name"), Toast.LENGTH_SHORT).show();
                            Intent myIntent = new Intent(MainActivity.this,Main2Activity.class);
                            startActivity(myIntent);//
                        }
                        else
                            Toast.makeText(MainActivity.this, "Error Login", Toast.LENGTH_SHORT).show();

                    }
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
