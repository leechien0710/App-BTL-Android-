package com.example.myapplication;

import static com.example.myapplication.AddActivity.token;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener{

    public Spinner sp;
    private EditText eten,egia,esoluong;
    private CheckBox checkBox1,checkBox2,checkBox3;
    private Button btUpdate ,btDelete,btBack;
    private Product p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        init();
        btUpdate.setOnClickListener(this);
        btDelete.setOnClickListener(this);
        Intent intent=getIntent();
        p= (Product) intent.getSerializableExtra("product");
        eten.setText(p.getTen());
        egia.setText(p.getGia());
        esoluong.setText(p.getSl());
        int l=0;
        for(int i=0;i<sp.getCount();i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(p.getLoai())){
                l=i;
                break;
            }
        }
        sp.setSelection(l);
    }
    public void init(){
        sp = findViewById(R.id.loai);
        eten=findViewById(R.id.ten);
        egia = findViewById(R.id.gia);
        esoluong = findViewById(R.id.soluong);
        checkBox1 = findViewById(R.id.do_checkbox);
        checkBox2 = findViewById(R.id.trang_checkbox);
        checkBox3 = findViewById(R.id.den_checkbox);
        btUpdate = findViewById(R.id.btUpdate);
        btDelete = findViewById(R.id.btDelete);
        btBack = findViewById(R.id.btBack);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.itemspinner,getResources().getStringArray(R.array.loai)));
    }

    @Override
    public void onClick(View view) {
        SQLiteHelper db= new SQLiteHelper(this);
        if(view==btBack){
            finish();
        }
        if(view==btUpdate){
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
                int id  = p.getId();
                Product  product = new Product(id,ten,gia,loai,sl,mau);
                db.updateProduct(product);
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                String id_user = mAuth.getCurrentUser().getUid();
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = sdf.format(now);
                History history = new History(id,id_user,"UPDATE",formattedDate);
                db.addHistory(history);
                FCMSend.send(UpdateDeleteActivity.this,token,"Đã có sản phẩm bị sửa đổi","Ấn vào đây để xem sự thay đổi");

                finish();
            }
        }
        if(view==btDelete){
            int id=p.getId();
            AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thong bao xoa!");
            builder.setTitle("Ban co chac muon xoa "+p.getTen()+" khong?");
            builder.setIcon(R.drawable.baseline_remove_circle_24);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.deleteProduct(id);
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    String id_user = mAuth.getCurrentUser().getUid();
                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = sdf.format(now);
                    History history = new History(id,id_user,"DELETE",formattedDate);
                    db.addHistory(history);
                    FCMSend.send(UpdateDeleteActivity.this,token,"Đã có sản phẩm bị xóa","Ấn vào đây để xem sự thay đổi");
                    finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }
    }

}