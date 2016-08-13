package com.qualixam.modal;

/**
 * Created by akhtarraza on 11/02/16.
 */
public class BookmarkData {
    private String bookmark_id;
    private String register_id;
    private String user_id;
    private String register_name;

    public BookmarkData(String bookmark_id, String register_id, String user_id, String register_name){
        this.bookmark_id = bookmark_id;
        this.register_id = register_id;
        this.user_id = user_id;
        this.register_name = register_name;
    }
    public String getBookmark_id(){
        return bookmark_id;
    }
    public void setBookmark_id(String bookmark_id){
        this.bookmark_id = bookmark_id;
    }
    public String getRegister_id() {
        return register_id;
    }
    public void setRegister_id(String register_id) {
        this.register_id = register_id;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getRegister_name()
    {
        return register_name;
    }
    public void setRegister_name(String register_name)
    {
        this.register_name = register_name;
    }
}