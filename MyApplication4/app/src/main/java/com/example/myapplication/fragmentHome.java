package com.example.myapplication;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class fragmentHome extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    RecycleViewAdapter adapter;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView= view.findViewById(R.id.recycleView);
        adapter= new RecycleViewAdapter();
        db= new SQLiteHelper(getContext());
        LinearLayoutManager manager= new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }


    @Override
    public void onItemClick(View view, int position) {
        Product product =adapter.getProduct(position);
        Intent intent= new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("product",product);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Product> list= db.getAllProduct();
        adapter.setList(list);

    }
}