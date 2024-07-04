package com.example.doan_thibanglaixe.Activities;

import static com.example.doan_thibanglaixe.Activities.ThiSatHachActivity.SIZE;
import static com.example.doan_thibanglaixe.Activities.ThiSatHachActivity.soCauDung;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doan_thibanglaixe.R;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class KetQuaActivity extends AppCompatActivity implements View.OnClickListener{
    TextView tv_diem;
    ImageView img_ketQua;
    Button btXemDapAn, btDeThiKhac;

    int dem = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua);
        setControl();
    }
    public void setControl(){

        tv_diem = findViewById(R.id.tv_diem);
        img_ketQua = findViewById(R.id.img_ketQua);
        btXemDapAn = findViewById(R.id.btXemDapAn);
        btDeThiKhac = findViewById(R.id.btDeThiKhac);
        btXemDapAn.setOnClickListener(this);
        btDeThiKhac.setOnClickListener(this);
        if (SIZE == 20){
            tv_diem.setText(soCauDung+"/20");
            if (soCauDung<16){
                img_ketQua.setImageDrawable(getDrawable(this,"truotchot"));
            } else {
                img_ketQua.setImageDrawable(getDrawable(this,"dochot"));
            }
        } else if (SIZE == 30){
            tv_diem.setText(soCauDung+"/30");
            if (soCauDung<26){
                img_ketQua.setImageDrawable(getDrawable(this,"truotchot"));
            } else {
                img_ketQua.setImageDrawable(getDrawable(this,"dochot"));
            }
        }

    }

    public Drawable getDrawable(Context context,String name){
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());
        Drawable drawable = resources.getDrawable(resourceId);
        return drawable;
    }

    @Override
    public void onClick(View view) {
        if (view==btXemDapAn){
            Intent intent_xemDapAn = new Intent(KetQuaActivity.this,XemLaiDapAnActivity.class);
            startActivity(intent_xemDapAn);
        } else if (view==btDeThiKhac){
            Intent intent_thiSatHach = new Intent(KetQuaActivity.this,ThiSatHachActivity.class);
            if (SIZE == 20){
                intent_thiSatHach.putExtra("tenBaiThi",'a');
            } else{
                intent_thiSatHach.putExtra("tenBaiThi",'b');
            }
            startActivity(intent_thiSatHach);
        }
    }
}
