// IBookManager.aidl
package com.skeeter.demo.ipc.aidl;

import com.skeeter.demo.ipc.Book;
import com.skeeter.demo.ipc.aidl.IBookListObserver;

// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book newBook);

    // 注册观察者
    void registerBookListObserver(IBookListObserver observer);
    void unregisterBookListObserver(IBookListObserver observer);
}
