package com.example.a5_lv4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import java.util.ArrayList;

public class first_activity extends AppCompatActivity {
    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        initData();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter adapter = new Adapter(arrayList);
        recyclerView.setAdapter(adapter);

    }

    public void initData() {
        for (int i = 0; i < 100; i++) {
            arrayList.add("红岩网校移动开发部作业");
            arrayList.add("5-Lv4");
        }
    }
}
