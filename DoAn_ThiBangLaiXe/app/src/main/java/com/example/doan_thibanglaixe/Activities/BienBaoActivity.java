package com.example.doan_thibanglaixe.Activities;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_thibanglaixe.Adapter.AdapterRecyclerViewBienBao;
import com.example.doan_thibanglaixe.Dao.BienBaoDAO;
import com.example.doan_thibanglaixe.R;

import ca.barrenechea.widget.recyclerview.decoration.DividerDecoration;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;


public class BienBaoActivity extends AppCompatActivity {
    RecyclerView rcv_bienBao;
    AdapterRecyclerViewBienBao adapterRecyclerViewBienBao;
    RecyclerView.Adapter aa;
    BienBaoDAO bienBaoDAO;
    private StickyHeaderDecoration decor;
    Button btn_menu;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bien_bao);
        setControl();
    }

    private void setControl() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        rcv_bienBao = findViewById(R.id.rcv_bienBao);
        rcv_bienBao.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bienBaoDAO = new BienBaoDAO(this);
        adapterRecyclerViewBienBao = new AdapterRecyclerViewBienBao(this,bienBaoDAO.getListBienBao());
        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.default_header_color)
                .build();
        rcv_bienBao.setHasFixedSize(true);
        rcv_bienBao.addItemDecoration(divider);
        rcv_bienBao.setAdapter(adapterRecyclerViewBienBao);
        decor = new StickyHeaderDecoration(adapterRecyclerViewBienBao);
        rcv_bienBao.addItemDecoration(decor, 1);
        OverScrollDecoratorHelper.setUpOverScroll(rcv_bienBao, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                androidx.appcompat.widget.PopupMenu popupMenu
                        = new androidx.appcompat.widget.PopupMenu(BienBaoActivity.this,btn_menu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_bienbao,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.item_tatca) {
                            adapterRecyclerViewBienBao = new AdapterRecyclerViewBienBao(BienBaoActivity.this, bienBaoDAO.getListBienBao());
                            rcv_bienBao.removeItemDecoration(decor);
                            decor = new StickyHeaderDecoration(adapterRecyclerViewBienBao);
                            rcv_bienBao.addItemDecoration(decor, 1);
                            rcv_bienBao.setAdapter(adapterRecyclerViewBienBao);
                        } else if (item.getItemId() == R.id.item_nguyhiem) {
                            adapterRecyclerViewBienBao = new AdapterRecyclerViewBienBao(BienBaoActivity.this, bienBaoDAO.getListBienBaoNguyHiem());
                            rcv_bienBao.removeItemDecoration(decor);
                            decor = new StickyHeaderDecoration(adapterRecyclerViewBienBao);
                            rcv_bienBao.addItemDecoration(decor, 1);
                            rcv_bienBao.setAdapter(adapterRecyclerViewBienBao);
                        } else if (item.getItemId() == R.id.item_cam) {
                            adapterRecyclerViewBienBao = new AdapterRecyclerViewBienBao(BienBaoActivity.this, bienBaoDAO.getListBienBaoCam());
                            rcv_bienBao.removeItemDecoration(decor);
                            decor = new StickyHeaderDecoration(adapterRecyclerViewBienBao);
                            rcv_bienBao.addItemDecoration(decor, 1);
                            rcv_bienBao.setAdapter(adapterRecyclerViewBienBao);
                        } else if (item.getItemId() == R.id.item_hieulenh) {
                            adapterRecyclerViewBienBao = new AdapterRecyclerViewBienBao(BienBaoActivity.this, bienBaoDAO.getListBienBaoHieuLenh());
                            rcv_bienBao.removeItemDecoration(decor);
                            decor = new StickyHeaderDecoration(adapterRecyclerViewBienBao);
                            rcv_bienBao.addItemDecoration(decor, 1);
                            rcv_bienBao.setAdapter(adapterRecyclerViewBienBao);
                        } else if (item.getItemId() == R.id.item_chidan) {
                            adapterRecyclerViewBienBao = new AdapterRecyclerViewBienBao(BienBaoActivity.this, bienBaoDAO.getListBienBaoChiDan());
                            rcv_bienBao.removeItemDecoration(decor);
                            decor = new StickyHeaderDecoration(adapterRecyclerViewBienBao);
                            rcv_bienBao.addItemDecoration(decor, 1);
                            rcv_bienBao.setAdapter(adapterRecyclerViewBienBao);
                        } else if (item.getItemId() == R.id.item_phu) {
                            adapterRecyclerViewBienBao = new AdapterRecyclerViewBienBao(BienBaoActivity.this, bienBaoDAO.getListBienBaoPhu());
                            rcv_bienBao.removeItemDecoration(decor);
                            decor = new StickyHeaderDecoration(adapterRecyclerViewBienBao);
                            rcv_bienBao.addItemDecoration(decor, 1);
                            rcv_bienBao.setAdapter(adapterRecyclerViewBienBao);
                        } else {
                        }

                        return true;
                    }
                });
                popupMenu.setGravity(Gravity.CENTER_HORIZONTAL);
                popupMenu.show();
            }
        });
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

}
