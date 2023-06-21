package com.example.myapplication;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {
    private List<Product> list;
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapter() {
        list=new ArrayList<>();
    }

    public RecycleViewAdapter(List<Product> list) {
        this.list = list;
    }

    public void setList(List<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public Product getProduct(int position){
        return list.get(position);
    }
    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Product product= list.get(position);
        holder.tvten.setText(product.getTen());
        holder.tvgia.setText(product.getGia());
        holder.tvsoluong.setText(product.getSl());
        holder.tvloai.setText(product.getLoai());
        holder.tvmau.setText(product.getMau());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvten,tvgia,tvloai,tvsoluong,tvmau;
        public HomeViewHolder(@NonNull View view) {
            super(view);
            tvten=view.findViewById(R.id.ten);
            tvgia=view.findViewById(R.id.gia);
            tvloai=view.findViewById(R.id.loai);
            tvsoluong=view.findViewById(R.id.soluong);
            tvmau=view.findViewById(R.id.mau);
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
