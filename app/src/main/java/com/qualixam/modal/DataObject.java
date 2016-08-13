package com.qualixam.modal;

/**
 * Created by akhtarraza on 11/02/16.
 */
public class DataObject {
    private String mText1;
    private String mText2;
    private String mimage;

    public DataObject(String text1, String image){
        mText1 = text1;
        mimage = image;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    public String getMimage()
    {
        return mimage;
    }
    public void setMimage(String mimage)
    {
        this.mimage = mimage;
    }
}