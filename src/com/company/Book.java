package com.company;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private boolean inStock;
    private Reader user;

    public Book(String title, String author) {
        this.id = Library.genUniqueId();
        this.title = title;
        this.author = author;
        this.inStock = true;
    }

    public static List<Book> searchBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ключевую фразу для поиска...");
        String search = scanner.nextLine();
        List<Book> books = new ArrayList<>();
        for (Book book : Main.library.getBookList()) {
            if (book.getAuthor().toLowerCase().matches("(.*)" + search.toLowerCase() + "(.*)")
                    || book.getTitle().toLowerCase().matches("(.*)" + search.toLowerCase() + "(.*)")) {
                books.add(book);
            }
        }
        if (books.size() == 0){
            System.out.println("Книг с такими параметрами не найдено!");
            searchBook();
        } else getAllBooks(books);
        return books;
    }

    public static void getAllBooks(List<Book> bookList) {
        for (Book book : bookList) {
            book.getInfo();
        }
        System.out.println("**************************************");
    }

    public static void getAllTakenBooks(List<Book> allBookList) {
        List<Book> books = new ArrayList<>();
        for (Book book : allBookList) {
            if (!book.isInStock()){
                books.add(book);
            }
        }
        if (books.size() == 0){
            System.out.println("Нет взятых книг!");
        } else {
            for (Book book: books) {
                book.getInfo();
            }
        }
        System.out.println("**************************************");
    }

    void getInfo() {
        System.out.println("ID: " + getId()
                + " | Название: " + getTitle()
                + " | Автор: " + getAuthor()
                + " | Статус: " + (isInStock() ? "Доступно" : "Взято"));
    }

    public static void addNewBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название книги...");
        String title = scanner.nextLine();
        System.out.println("Введите автора книги...");
        String author = scanner.nextLine();
        Book book = new Book(title, author);
        Main.library.getBookList().add(book);
        System.out.print("Добавлена книга - ");
        book.getInfo();
        System.out.println("**************************************");
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public void setUser(Reader user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isInStock() {
        return inStock;
    }

    public Reader getUser() {
        return user;
    }
}
