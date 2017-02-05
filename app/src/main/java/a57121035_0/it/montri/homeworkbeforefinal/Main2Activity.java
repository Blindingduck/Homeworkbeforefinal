package a57121035_0.it.montri.homeworkbeforefinal;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    Button btn_Load,btn_Show;
    ListView lv_Show;
    Uri myUri;
    Cursor myCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn_Load = (Button) findViewById(R.id.btnLoad);
        btn_Show = (Button) findViewById(R.id.btnShow);
        lv_Show = (ListView) findViewById(R.id.lvShow);
        myUri = Uri.parse("content://Database");

        lv_Show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent myIntent = new Intent(Main2Activity.this,EditActivity.class);
                String data = lv_Show.getItemAtPosition(i).toString();
                String[] item = null;
                for (int j = 0; j<lv_Show.getCount();j++)
                {
                    item  = data.split("[,]");
                }
                myIntent.putExtra("id",item[0].substring(item[0].lastIndexOf("=")+1));
                myIntent.putExtra("price",item[1].substring(item[1].lastIndexOf("=")+1));
                myIntent.putExtra("name",item[3].substring(item[3].lastIndexOf("=")+1,item[3].length()-1));
                startActivity(myIntent);
            }
        });

        btn_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<HashMap<String,String>> myList = new ArrayList<HashMap<String, String>>();
                String result = "";
                int i =1 ;
                myCursor = Main2Activity.this.getContentResolver().query(myUri,null,null,null,null);
                result = "Found Data : "+myCursor.getCount() +"list \n\n";
                while (myCursor.moveToNext()){
                    HashMap<String, String> myMap = new HashMap<String, String>();
                            myMap.put("No", i  + ".)");
                            myMap.put("ID", myCursor.getString(0));
                            myMap.put("Name", myCursor.getString(1));
                            myMap.put("Price", myCursor.getString(2));
                            myList.add(myMap);
                    i++;
                }
                String[] from = new String[] {"No","ID","Name","Price"};
                int[] to = new int[] {R.id.tvNo,R.id.tvID,R.id.tvName,R.id.tvPrice};
                SimpleAdapter ap = new SimpleAdapter(Main2Activity.this,myList,R.layout.listview_layout,from,to);
                lv_Show.setAdapter(ap);
            }
        });

        btn_Load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    HttpClient hc = new DefaultHttpClient();
                    HttpPost hp = new HttpPost("http://motori.coolpage.biz/android/loaddata.php");
                    HttpResponse hr = hc.execute(hp);
                    String data = "";
                    JSONArray jsonArray;
                    JSONObject jsonObject;
                    BufferedReader bf;
                    bf = new BufferedReader(new InputStreamReader(hr.getEntity().getContent()));
                    data = bf.readLine();
                    jsonArray = new JSONArray(data);
                    getBaseContext().getContentResolver().delete(myUri,null,null);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            Insert(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("price"));
//
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void Insert( String id , String Name,String Price) {
        ContentValues values = new ContentValues();
//        values.put("No",No);
        values.put("id",id);
        values.put("name",Name);
        values.put("price",Price);
        getBaseContext().getContentResolver().insert(myUri,values);
    }
}
