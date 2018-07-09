package com.linkhand.fenxiao.feng.home;

import java.io.Serializable;

/**
 * Created by 11860_000 on 2018/2/28.
 */

public class MyGoodsFeng implements Serializable {
    private   String myid;//规格id
    private  String myname;//规格名
    private  String myallname;//类型名

    public String getMyid() {
        return myid;
    }

    public void setMyid(String myid) {
        this.myid = myid;
    }

    public String getMyname() {
        return myname;
    }

    public void setMyname(String myname) {
        this.myname = myname;
    }

    public String getMyallname() {
        return myallname;
    }

    public void setMyallname(String myallname) {
        this.myallname = myallname;
    }
}
