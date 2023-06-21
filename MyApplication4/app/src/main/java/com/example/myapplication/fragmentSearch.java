package com.example.myapplication;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class fragmentSearch extends Fragment implements View.OnClickListener{
    private SearchView searchView;
    private Spinner sp;
    private Button btSearch;
    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private SQLiteHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragment_search,container,false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView= view.findViewById(R.id.search);
        sp = view.findViewById(R.id.loai);
        recyclerView = view.findViewById(R.id.recycleView);
        String[] arr = getResources().getStringArray(R.array.loai);
        String[] arr1 = new String[arr.length+1];
        arr1[0] = "All";
        for(int i=0;i< arr.length;i++){
            arr1[i+1] = arr[i];
        }
        sp.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.itemspinner,arr1));

        adapter = new RecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Product> list = db.getAllProduct();
        adapter.setList(list);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Product> list = db.searchByTen(newText);
                adapter.setList(list);
                return true;
            }
        });
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String loai = sp.getItemAtPosition(position).toString();
                List<Product> list;
                if(!loai.equals("All")){
                    list=db.searchByLoai(loai);
                }
                else{
                    list = db.getAllProduct();
                }
                adapter.setList(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}