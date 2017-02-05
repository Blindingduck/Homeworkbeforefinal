package a57121035_0.it.montri.homeworkbeforefinal;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    TextView tv_id,tv_name;
    EditText edt_Price;
    Button btn_Edit;
    String name ,id,price;
    Uri myUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        tv_id = (TextView) findViewById(R.id.tvEID);
        tv_name = (TextView) findViewById(R.id.tvEName);
        edt_Price = (EditText) findViewById(R.id.edtEPrice);
        btn_Edit = (Button) findViewById(R.id.btnEdit);
        Intent data = getIntent();
        name = data.getStringExtra("name");
        id = data.getStringExtra("id");
        price = data.getStringExtra("price");
        myUri = Uri.parse("content://Database");

        tv_id.setText(id);
        tv_name.setText(name);
        edt_Price.setText(price);

        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check = 0;
                try {
                    List<NameValuePair> nv = new ArrayList<NameValuePair>();
                    check = UpdataData(tv_id.getText().toString(),Integer.parseInt(edt_Price.getText().toString()));
                    HttpClient hc = new DefaultHttpClient();
                    nv.add(new BasicNameValuePair("id",tv_id.getText().toString()));
                    nv.add(new BasicNameValuePair("price",edt_Price.getText().toString()));
                    HttpPost hp = new HttpPost("http://motori.coolpage.biz/android/updatedata.php");
                    hp.setEntity(new UrlEncodedFormEntity(nv));
                    HttpResponse hr = hc.execute(hp);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(check>0){
                    Toast.makeText(EditActivity.this, "Update", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(EditActivity.this, "Error Update Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int UpdataData(String id , int price) throws IOException {
        String where = "id = "+id;
        ContentValues values = new ContentValues();
        values.put("price",price);
        return getBaseContext().getContentResolver().update(myUri,values,where,null);
    }
}
