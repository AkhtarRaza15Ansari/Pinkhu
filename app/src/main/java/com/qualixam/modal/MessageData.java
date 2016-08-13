package com.qualixam.modal;

/**
 * Created by akhtarraza on 11/02/16.
 */
public class MessageData {
    private String message_from;
    private String message_to;
    private String message;
    private String date_time;
    private String type;

    public MessageData(String message_from, String message_to,String message,String type,String date_time){
        this.message_from = message_from;
        this.message_to = message_to;
        this.message = message;
        this.date_time = date_time;
        this.type = type;
    }
    public String getMessage_from(){
        return message_from;
    }
    public void setMessage_from(String message_from){
        this.message_from = message_from;
    }
    public String getMessage_to() {
        return message_to;
    }
    public void setMessage_to(String message_to) {
        this.message_to = message_to;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType(){
        return type;
    }
    public void setDatetime(String date_time){
        this.date_time = date_time;
    }
    public String setDatetime() {
        return date_time;
    }


}