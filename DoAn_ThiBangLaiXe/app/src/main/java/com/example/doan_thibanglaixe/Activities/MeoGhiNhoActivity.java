package com.example.doan_thibanglaixe.Activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_thibanglaixe.Adapter.AdapterRecyclerViewMeoGhiNho;
import com.example.doan_thibanglaixe.Dao.MeoGhiNhoDAO;
import com.example.doan_thibanglaixe.R;

import ca.barrenechea.widget.recyclerview.decoration.DividerDecoration;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class MeoGhiNhoActivity extends AppCompatActivity {
    RecyclerView rcv_meoGhiNho;
    AdapterRecyclerViewMeoGhiNho adapterRecyclerViewMeoGhiNho;
    MeoGhiNhoDAO meoGhiNhoDAO;
    private StickyHeaderDecoration decor;
    FloatingActionButton btn_menu;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meoghinho);
        setControl();
        setEvent();
    }
    public void setControl(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        rcv_meoGhiNho = findViewById(R.id.rcv_meoGhiNho);
        rcv_meoGhiNho.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        meoGhiNhoDAO = new MeoGhiNhoDAO(this);
        adapterRecyclerViewMeoGhiNho = new AdapterRecyclerViewMeoGhiNho(this,meoGhiNhoDAO.getListMeoGhiNho());
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.default_header_color)
                .build();
        rcv_meoGhiNho.setHasFixedSize(true);
        rcv_meoGhiNho.addItemDecoration(divider);
        rcv_meoGhiNho.setAdapter(adapterRecyclerViewMeoGhiNho);
        decor = new StickyHeaderDecoration(adapterRecyclerViewMeoGhiNho);
        rcv_meoGhiNho.addItemDecoration(decor, 1);
        OverScrollDecoratorHelper.setUpOverScroll(rcv_meoGhiNho, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        btn_menu = findViewById(R.id.btn_menu);
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

    private void setEvent(){
        btn_menu.setOnClickListener(view -> {
            PopupMenu popupMenu
                    = new PopupMenu(MeoGhiNhoActivity.this,btn_menu);
            popupMenu.getMenuInflater().inflate(R.menu.menu_meoghinho,popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.item_tatca) {
                    adapterRecyclerViewMeoGhiNho
                            = new AdapterRecyclerViewMeoGhiNho(MeoGhiNhoActivity.this,meoGhiNhoDAO.getListMeoGhiNho());
                    rcv_meoGhiNho.removeItemDecoration(decor);
                    decor = new StickyHeaderDecoration(adapterRecyclerViewMeoGhiNho);
                    rcv_meoGhiNho.addItemDecoration(decor, 1);
                    rcv_meoGhiNho.setAdapter(adapterRecyclerViewMeoGhiNho);
                }


                if (item.getItemId() == R.id.item_meolythuyet) {
                    adapterRecyclerViewMeoGhiNho = new AdapterRecyclerViewMeoGhiNho(MeoGhiNhoActivity.this, meoGhiNhoDAO.getListMeoLyThuyet());
                    rcv_meoGhiNho.removeItemDecoration(decor);
                    decor = new StickyHeaderDecoration(adapterRecyclerViewMeoGhiNho);
                    rcv_meoGhiNho.addItemDecoration(decor, 1);
                    rcv_meoGhiNho.setAdapter(adapterRecyclerViewMeoGhiNho);
                } else if (item.getItemId() == R.id.item_meobienbao) {
                    adapterRecyclerViewMeoGhiNho = new AdapterRecyclerViewMeoGhiNho(MeoGhiNhoActivity.this, meoGhiNhoDAO.getListMeoBienBao());
                    rcv_meoGhiNho.removeItemDecoration(decor);
                    decor = new StickyHeaderDecoration(adapterRecyclerViewMeoGhiNho);
                    rcv_meoGhiNho.addItemDecoration(decor, 1);
                    rcv_meoGhiNho.setAdapter(adapterRecyclerViewMeoGhiNho);
                } else if (item.getItemId() == R.id.item_meosahinh) {
                    adapterRecyclerViewMeoGhiNho = new AdapterRecyclerViewMeoGhiNho(MeoGhiNhoActivity.this, meoGhiNhoDAO.getListMeoSaHinh());
                    rcv_meoGhiNho.removeItemDecoration(decor);
                    decor = new StickyHeaderDecoration(adapterRecyclerViewMeoGhiNho);
                    rcv_meoGhiNho.addItemDecoration(decor, 1);
                    rcv_meoGhiNho.setAdapter(adapterRecyclerViewMeoGhiNho);
                }


                return true;
            });
            popupMenu.setGravity(Gravity.CENTER_HORIZONTAL);
            popupMenu.show();
        });
    }
}
