package com.example.doan_thibanglaixe.Object;

import java.io.Serializable;
import java.util.List;

public class DeThi implements Serializable{
    List<CauHoi> listCauHoi;

    public DeThi(List<CauHoi> listCauHoi) {
        this.listCauHoi = listCauHoi;
    }

    public List<CauHoi> getListCauHoi() {
        return listCauHoi;
    }

    public void setListCauHoi(List<CauHoi> listCauHoi) {
        this.listCauHoi = listCauHoi;
    }
}
