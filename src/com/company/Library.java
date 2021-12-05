package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Library implements Serializable {
    private List<Reader> readerList;
    private List<Book> bookList;
    static List<Integer> ids = new ArrayList<>();

    public Library(List<Reader> readerList, List<Book> bookList) {
        this.readerList = readerList;
        this.bookList = bookList;
    }

    public List<Reader> getReaderList() {
        return readerList;
    }

    public void setReaderList(List<Reader> readerList) {
        this.readerList = readerList;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public static int genUniqueId() {
        System.out.println("added password branch");
        int id;
        while (true) {
            id = new Random().nextInt(1000);
            if (checkForDuplicates(id)) {
                ids.add(id);
                break;
            }
        }
        return id;
    }

    private static boolean checkForDuplicates(int id) {
        for (int i : ids) {
            return i != id;
        }
        return true;
    }
}
