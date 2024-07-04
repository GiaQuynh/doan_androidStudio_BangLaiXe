package com.example.doan_thibanglaixe.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.example.doan_thibanglaixe.Adapter.AdapterRecyclerViewLyThuyet;
import com.example.doan_thibanglaixe.Dao.CauHoiDAO;
import com.example.doan_thibanglaixe.R;

import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class LyThuyetActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rcv_lyTHuyet;
    Button bt_truoc, bt_sau;
    AdapterRecyclerViewLyThuyet adapterRecyclerViewLyThuyet;
    int dem = 1;
    CauHoiDAO cauHoiDAO;
    Toolbar toolbar;
    TextView toolbar_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ly_thuyet);
        setControl();
    }
    public void setControl(){
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        bt_truoc = findViewById(R.id.bt_truoc);
        bt_sau = findViewById(R.id.bt_sau);
        rcv_lyTHuyet = findViewById(R.id.rcv_lyThuyet);
        cauHoiDAO = new CauHoiDAO(this);
        adapterRecyclerViewLyThuyet = new AdapterRecyclerViewLyThuyet(this,cauHoiDAO.getListCauHoi(dem));
        rcv_lyTHuyet.setAdapter(adapterRecyclerViewLyThuyet);
        rcv_lyTHuyet.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        OverScrollDecoratorHelper.setUpOverScroll(rcv_lyTHuyet, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        bt_truoc.setOnClickListener(this);
        bt_sau.setOnClickListener(this);
        RecyclerViewHeader header = (RecyclerViewHeader) findViewById(R.id.header);
        header.attachTo(rcv_lyTHuyet);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Nút "Up" được nhấn
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
        if (view==bt_truoc){
            if (dem!=1){
                dem--;
                adapterRecyclerViewLyThuyet
                        = new AdapterRecyclerViewLyThuyet(LyThuyetActivity.this,cauHoiDAO.getListCauHoi(dem));
                rcv_lyTHuyet.setAdapter(adapterRecyclerViewLyThuyet);
            }
        } else {
            if (dem!=18){
                dem++;
                adapterRecyclerViewLyThuyet
                        = new AdapterRecyclerViewLyThuyet(LyThuyetActivity.this,cauHoiDAO.getListCauHoi(dem));
                rcv_lyTHuyet.setAdapter(adapterRecyclerViewLyThuyet);
            }
        }
        toolbar_title.setText(dem+"/18");
    }
}
