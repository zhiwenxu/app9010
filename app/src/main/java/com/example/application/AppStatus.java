package com.example.application;

import com.example.utils.SharePerencesUtil;

/**
 * Created by Administrator on 2017/3/8.
 */

public class AppStatus {
    private static SharePerencesUtil sharePerences = SharePerencesUtil.getInstances();
    //判断是否第一次登录
    public static void setFirstUse(boolean first){
        sharePerences.setFirstUse(first);
    }
    public static boolean getFirstUse(){
        return sharePerences.getFirstUse();
    }
    //保存正常登陆的有用户名
    public static void setLoginUserName(String username){
        sharePerences.setLoginUserName(username);
    }
    public static String getLoginUserName(){
        return sharePerences.getLoginUserName();
    }
    //保存密码
    public static void setPassWord(String password){
        sharePerences.setPassWord(password);
    }

    public static String getPassWord(){
        return sharePerences.getPassWord();
    }

    //记录json文件到本地
    public static void setCXHD(String str){
        sharePerences.setCXHD(str);
    }
    public static void setXPCX(String str){
        sharePerences.setXPCX(str);
    }
    public static void setGSXW(String str){
        sharePerences.setGSXW(str);
    }
    public static void setGSZX(String str){
        sharePerences.setGSZX(str);
    }
    public static void setZPXX(String str){
        sharePerences.setZPXX(str);
    }
    public static void setLYBK(String str){
        sharePerences.setLYBK(str);
    }
    public static void setCJWT(String str){
        sharePerences.setCJWT(str);
    }
    public static void setYHTK(String str){
        sharePerences.setYHTK(str);
    }
    public static void setGYWM(String str){
        sharePerences.setGYWM(str);
    }
    public static void setLoad(boolean load){
        sharePerences.setLoad(load);
    }
    public static void setChangeImage(String str){
        sharePerences.setChangeImage(str);
    }
    public static String getCXHD(){
        return sharePerences.getCXHD();
    }
    public static String getXPCX(){
        return sharePerences.getXPCX();
    }
    public static String getGSXW(){
        return sharePerences.getGSXW();
    }
    public static String getGSZX(){
        return sharePerences.getGSZX();
    }
    public static String getZPXX(){
        return sharePerences.getZPXX();
    }
    public static String getLYBK(){
        return sharePerences.getLYBK();
    }
    public static String getCJWT(){
        return sharePerences.getCJWT();
    }
    public static String getYHTK(){
        return sharePerences.getYHTK();
    }
    public static String getGYWM(){
        return sharePerences.getGYWM();
    }
    public static boolean getLoad(){
        return sharePerences.getLoad();
    }
    public static String getChangeImage(){
        return sharePerences.getChangeImage();
    }
    //版本检查时间点标记
    public static void setVersionDate(long date){
        sharePerences.setVersionDate(date);
    }

    public static long getVersionDate(){
        return sharePerences.getVersionDate();
    }



}
