// IBookObserver.aidl
package com.skeeter.demo.ipc.aidl;

import com.skeeter.demo.ipc.Book;

// Declare any non-default types here with import statements

interface IBookListObserver {
    void onBookListChanged(in List<Book> newBookList);
}
