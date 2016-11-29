package com.skeeter.demo.ipc.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.skeeter.demo.ipc.Book;
import com.skeeter.demo.ipc.aidl.IBookListObserver;
import com.skeeter.demo.ipc.aidl.IBookManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author michael created on 2016/11/27.
 */
class BookManager extends Binder implements IBookManager {
    /**
     * 考虑并发
     */
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<IBookListObserver> mBookListObserver = new CopyOnWriteArrayList<>();

    @Override
    public List<Book> getBookList() throws RemoteException {
        return mBookList;
    }

    @Override
    public void addBook(Book newBook) throws RemoteException {
        mBookList.add(newBook);
        onBookListChanged();
    }

    public void onBookListChanged() {
        for (IBookListObserver observer : mBookListObserver) {
            if (observer != null) {
                try {
                    observer.onBookListChanged(mBookList);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void registerBookListObserver(IBookListObserver observer) throws RemoteException {
        mBookListObserver.add(observer);
    }

    @Override
    public void unregisterBookListObserver(IBookListObserver observer) throws RemoteException {
        mBookListObserver.remove(observer);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }
}
