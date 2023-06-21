package com.example.myapplication;

import static com.example.myapplication.MyApplication.CHANNEL_ID;
import static com.google.firebase.messaging.Constants.MessagePayloadKeys.SENDER_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;


import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    public static String token;
    public Spinner sp;
    private EditText eten,egia,esoluong;
    private CheckBox checkBox1,checkBox2,checkBox3;
    private Button btCreate ,btCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        btCreate.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("Firebase", "Fetching FCM registration token failed:" + task.getException());
                        return;
                    }


                    token = task.getResult();
                    Log.w("Firebase", token);
                });
    }
    public void init(){
        sp = findViewById(R.id.loai);
        eten=findViewById(R.id.ten);
        egia = findViewById(R.id.gia);
        esoluong = findViewById(R.id.soluong);
        checkBox1 = findViewById(R.id.do_checkbox);
        checkBox2 = findViewById(R.id.trang_checkbox);
        checkBox3 = findViewById(R.id.den_checkbox);
        btCreate = findViewById(R.id.btCreate);
        btCancel = findViewById(R.id.btCancel);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.itemspinner,getResources().getStringArray(R.array.loai)));
    }

    @Override
    public void onClick(View view) {
        if(view==btCancel){
            finish();
        }
        if(view==btCreate){
            String ten = eten.getText().toString();
            String gia= egia.getText().toString();
            String loai=sp.getSelectedItem().toString();
            String sl =esoluong.getText().toString();
            String mau = "";
            if(checkBox1.isChecked()){
                mau+= checkBox1.getText().toString();
            }
            if(checkBox2.isChecked()){
                mau+= checkBox2.getText().toString();
            }
            if(checkBox3.isChecked()){
                mau+= checkBox3.getText().toString();
            }
            if(!ten.isEmpty() && !gia.isEmpty() && !sl.isEmpty()){
                Product  p = new Product(ten,gia,loai,sl,mau);
                SQLiteHelper db= new SQLiteHelper(this);
                long productId = db.addProduct(p);
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String id_user = mAuth.getCurrentUser().getUid();
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = sdf.format(now);
                History history = new History((int)productId,id_user,"CREATE",formattedDate);
                db.addHistory(history);
                FCMSend.send(AddActivity.this,token,"Đã có sản phẩm được thêm mới","Ấn vào đây để xem sự thay đổi");
                finish();
            }
        }

    }


}