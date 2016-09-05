package com.qualixam.modal;

/**
 * Created by akhtarraza on 11/02/16.
 */
public class ChatListData {
    private String registerid;
    private String name;
    private String number;

    public ChatListData(String registerid, String name,String number){
        this.registerid = registerid;
        this.name = name;
        this.number = number;
    }
    public String getRegisterid(){
        return registerid;
    }
    public void setRegisterid(String registerid){
        this.registerid = registerid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number= number;
    }
}