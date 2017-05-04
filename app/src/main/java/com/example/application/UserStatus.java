package com.example.application;

import com.example.utils.SharePerencesUtil;

/**
 * Created by Administrator on 2017/3/8.
 */

public class UserStatus {

    private static SharePerencesUtil sharePerences = SharePerencesUtil.getInstances();

    //设置id
    public static void setID(String id){
        sharePerences.setID(id);
    }

    public static String getID(){
        return sharePerences.getID();
    }

    //设置uuid
    public static void setUUID(String uuid){
        sharePerences.setUUID(uuid);
    }
    public static String getUUID(){
        return sharePerences.getUUID();
    }

    //nick
    public static void setNick(String nick){
        sharePerences.setNick(nick);
    }

    public static String getNick(){
        return sharePerences.getNick();
    }

    //gender
    public static void setGender(int gender){
        sharePerences.setGender(gender);
    }
    public static int getGender(){
        return sharePerences.getGender();
    }

    //cents
    public static void setCents(int cents){
        sharePerences.setCents(cents);
    }
    public static int getCents(){
        return sharePerences.getCents();
    }


    public static void setCard(String card){
        sharePerences.setCard(card);
    }
    public static String getCard(){
        return sharePerences.getCard();
    }

    public static void setRmb(int rmb){
        sharePerences.setRmb(rmb);
    }
    public static int getRmb(){
        return sharePerences.getRmb();
    }

    public static void setHeadUrl(String url){
        sharePerences.setHeadUrl(url);
    }
    public static String getHeadUrl(){
        return sharePerences.getHeadUrl();
    }

    public static void setLogin(boolean login){
        sharePerences.setLogin(login);
    }
    public static boolean getLogin(){
        return sharePerences.getLogin();
    }
    public static void setLoginType(int type){
        sharePerences.setLoginType(type);
    }
    public static int getLoinType(){
        return sharePerences.getLoginType();
    }

    public static void setOpenId(String openId){
        sharePerences.setOpenid(openId);
    }

    public static String getOpenId(){
        return sharePerences.getOpenid();
    }

    public static void clearUserStatus(){
        sharePerences.setNick("");
        sharePerences.setGender(0);
        sharePerences.setCents(0);
        sharePerences.setCard("");
        sharePerences.setRmb(0);
        sharePerences.setLogin(false);
        sharePerences.setLoginType(0);
        sharePerences.setPassWord("");
        sharePerences.setUUID("");
    }


}
