package com.smapley.moni.mode;

/**
 * Created by smapley on 16/2/1.
 */
public class ZhuangMode {

    private String user1;
    private String  mi;
    private String biaoti="";
    private String type;
    private String dan="";
    private String pei="";
    private String fjmi="";


    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getMi() {
        return mi;
    }

    public void setMi(String mi) {
        this.mi = mi;
    }

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDan() {
        return dan;
    }

    public void setDan(String dan) {
        this.dan = dan;
    }

    public String getPei() {
        return pei;
    }

    public void setPei(String pei) {
        this.pei = pei;
    }

    public String getFjmi() {
        return fjmi;
    }

    public void setFjmi(String fjmi) {
        this.fjmi = fjmi;
    }

    @Override
    public String toString() {
        return "ZhuangMode{" +
                "user1='" + user1 + '\'' +
                ", mi='" + mi + '\'' +
                ", biaoti='" + biaoti + '\'' +
                ", type=" + type +
                ", dan='" + dan + '\'' +
                ", pei='" + pei + '\'' +
                ", fjmi='" + fjmi + '\'' +
                '}';
    }
}
