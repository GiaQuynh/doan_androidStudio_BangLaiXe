package com.example.doan_thibanglaixe.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doan_thibanglaixe.Adapter.AdapterRecyclerViewThiSatHach;
import com.example.doan_thibanglaixe.Dao.CauHoiDAO;
import com.example.doan_thibanglaixe.Object.CauHoi;
import com.example.doan_thibanglaixe.Object.DeThi;
import com.example.doan_thibanglaixe.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThiSatHachActivity extends AppCompatActivity implements View.OnClickListener{
    int dem = 1;
    Button bt_truoc, bt_sau,bt_huyBo, bt_dongY, bt_dongYTiep;
    CauHoiDAO cauHoiDAO;
    public static List<CauHoi> list;
    List<CauHoi> listCauHoi = new ArrayList<CauHoi>();
    RecyclerView rcv_thiSatHach;
    AdapterRecyclerViewThiSatHach adapterRecyclerViewThiSatHach;
    Dialog dialogFinish;
    public static int soCauDung = 0;
    StringBuilder [] dapAnLuaChon = new StringBuilder[35];
    ArrayList a;
    public static int sttCauHoi [] = new int[100];
    public static boolean checkDungSai[] = new boolean[100];
    public static int SIZE = 0;
    List<DeThi> listDeThi = new ArrayList<>();
    boolean ok = false;
    int FLAG=0;

    boolean allQuestionsAnswered = false;
    Dialog dialogNotFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thi_sat_hach);

        if (getIntent().getCharExtra("tenBaiThi",' ')=='a') SIZE = 20;
        else SIZE = 30;
        ranDomCauHoi();
        setControl();

        listDeThi = docFile("lichsu.txt");
        if (listDeThi.size()<20){
            listDeThi.add(new DeThi(list));
        } else {
            for (int i=19;i>=1;i--) listDeThi.set(i,listDeThi.get(i-1));
            listDeThi.set(0,new DeThi(list));
        }
        ghiFile(listDeThi);

        rcv_thiSatHach.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterRecyclerViewThiSatHach = new AdapterRecyclerViewThiSatHach(this,list.get(0),0);
        rcv_thiSatHach.setAdapter(adapterRecyclerViewThiSatHach);
    }

    public List<DeThi> docFile(String fileName){
        List<DeThi> listDeThi = new ArrayList<>();
        try {
            File file = getFileStreamPath(fileName);
            if (file==null||!file.exists()){
                file = new File(fileName);
            }
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listDeThi = (List<DeThi>) ois.readObject();
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listDeThi;
    }

    public void ghiFile(List<DeThi> listDeThi){
        try {
            File file = getFileStreamPath("lichsu.txt");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listDeThi);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setControl() {
        bt_truoc = findViewById(R.id.bt_truoc);
        bt_sau = findViewById(R.id.bt_sau);
        dialogFinish = new Dialog(this);
        dialogFinish.setContentView(R.layout.custom_dialog_finish);
        dialogFinish.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogFinish.setCanceledOnTouchOutside(false);
        bt_huyBo = dialogFinish.findViewById(R.id.bt_huyBo);
        bt_dongY = dialogFinish.findViewById(R.id.bt_dongY);
        bt_huyBo.setOnClickListener(this);
        bt_dongY.setOnClickListener(this);
        bt_truoc.setOnClickListener(this);
        bt_sau.setOnClickListener(this);
        rcv_thiSatHach = findViewById(R.id.rcv_thiSatHach);
        dialogNotFinished = new Dialog(this);
        dialogNotFinished.setContentView(R.layout.custom_dialog_not_finished);
        dialogNotFinished.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogNotFinished.setCanceledOnTouchOutside(false);
        bt_dongYTiep = dialogNotFinished.findViewById(R.id.bt_dongYTiep);
        bt_dongYTiep.setOnClickListener(this);
    }
    public void ranDomCauHoi() {
        list = new ArrayList<CauHoi>();
        Random rd = new Random();
        cauHoiDAO = new CauHoiDAO(this);
        listCauHoi = cauHoiDAO.getListCauHoi();
        a = new ArrayList();
        int x;
        for (int i = 0; i < SIZE; i++) {
            do {
                int j = 450 - SIZE + i;
                x = rd.nextInt(j);
            }
            while (a.contains(x));
            a.add(x);
            list.add(listCauHoi.get((int) a.get(i)));
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_truoc) {
            dapAnLuaChon[dem-1] = adapterRecyclerViewThiSatHach.getDA();
            if (dem != 1) {
                dem--;
                resetCauHoi();
            }
        } else if (view.getId() == R.id.bt_sau) {
            dapAnLuaChon[dem-1] = adapterRecyclerViewThiSatHach.getDA();
            if (dem != SIZE) {
                dem++;
                resetCauHoi();
            } else {
                ok = true;
                dialogFinish.show();
            }
        } else if (view.getId() == R.id.bt_huyBo) {
            dapAnLuaChon[dem-1] = adapterRecyclerViewThiSatHach.getDA();
            dialogFinish.dismiss();
        } else if (view.getId() == R.id.bt_dongY || view.getId() == R.id.bt_cancel) {
            if (ok) {
                dapAnLuaChon[dem-1] = adapterRecyclerViewThiSatHach.getDA();
                soCauDung = 0;
                for (int i=0; i<SIZE; i++) {
                    Log.d("Dap an dung cau "+(i+1), list.get(i).getDapAn());
                    Log.d("Dap an lua chon cau "+(i+1), dapAnLuaChon[i].toString());
                    if (list.get(i).getDapAn().compareTo(dapAnLuaChon[i].toString())==0) {
                        soCauDung++;
                        checkDungSai[i] = true;
                    } else {
                        checkDungSai[i] = false;
                    }
                }
                Intent intent_ketQua = new Intent(ThiSatHachActivity.this, KetQuaActivity.class);
                intent_ketQua.putExtra("soCauDung", soCauDung);
                startActivity(intent_ketQua);
            }
            dialogFinish.dismiss();
        }
    }

    private void resetCauHoi() {
        adapterRecyclerViewThiSatHach = new AdapterRecyclerViewThiSatHach(this, list.get(dem-1), dem-1);
        rcv_thiSatHach.setAdapter(adapterRecyclerViewThiSatHach);
        Log.d("Dap an dung:",list.get(dem-1).getDapAn());
    }
}