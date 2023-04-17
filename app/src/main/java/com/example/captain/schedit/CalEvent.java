package com.example.captain.schedit;

/**
 * Created by Captain on 12/6/2017.
 */

public class CalEvent {
    private String mName;
    private String mDate;
    private String mFavorite;

    public CalEvent()
    {
        mName = "";
        mDate = "";
        mFavorite = "";
    }

    public CalEvent(final String name, final String date, final String favorite)
    {
        mName = name;
        mDate = date;
        mFavorite = favorite;
    }

    public String getName()
    {
        return mName;
    }

    public String getDate()
    {
        return mDate;
    }

    public String getFavorite()
    {
        return mFavorite;
    }
}
