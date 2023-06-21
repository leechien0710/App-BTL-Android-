package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HisRCVAdapter extends  RecyclerView.Adapter<HisRCVAdapter.HomeViewHolder>{
    private List<History> list;
    private HisRCVAdapter.ItemListener itemListener;
    private Context context;
    private  SQLiteHelper db;


    public void setItemListener(HisRCVAdapter.ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public HisRCVAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.db = new SQLiteHelper(context);
    }

    public HisRCVAdapter(List<History> list) {
        this.list = list;
    }

    public void setList(List<History> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public History getHistory(int position){
        return list.get(position);
    }
    @NonNull
    @Override
    public HisRCVAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history,parent,false);
        return new HisRCVAdapter.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HisRCVAdapter.HomeViewHolder holder, int position) {
        History history= list.get(position);
        String id_user = history.getId_user();
        int id_product = history.getId_product();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Accounts/"+id_user+"/firstName");
        myRef.keepSynced(true);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue(String.class);
                holder.user.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference("Accounts/"+id_user+"/dp");
        myRef2.keepSynced(true);
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = dataSnapshot.getValue(String.class);
                Picasso.get().load(image).into(holder.dp);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Product p = db.getProductById(id_product);
        if(p==null){
            holder.product.setText("NOT EXIST");
        }
        else{
        holder.product.setText(p.getTen());}
        holder.actions.setText(history.getActions());
        holder.time.setText(history.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView user,product,actions,time;
        private CircleImageView dp;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            user = view.findViewById(R.id.user);
            product = view.findViewById(R.id.product);
            actions = view.findViewById(R.id.actions);
            time = view.findViewById(R.id.time);
            dp = view.findViewById(R.id.display_pic);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onItemClick(view,getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view,int position);
    }
}
