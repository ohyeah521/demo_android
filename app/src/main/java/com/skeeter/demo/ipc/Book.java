package com.skeeter.demo.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author michael created on 2016/11/26.
 */
public class Book implements Parcelable {

    private String mBookName;
    private int mPages;

    protected Book(Parcel in) {
        this.mBookName = in.readString();
        this.mPages = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mBookName);
        dest.writeInt(mPages);
    }
}
