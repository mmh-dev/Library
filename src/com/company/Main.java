package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Library library;
    static Reader loggedReader;

    public static void main(String[] args) {

        List<Book> books = new ArrayList<>();
        books.add(new Book("Harry Potter", "Joan Rolling"));
        books.add(new Book("Shaitanat", "Tohir Malik"));
        books.add(new Book("Chelovek-nevidimka", "Herbert Wales"));

        List<Reader> readers = new ArrayList<>();
        readers.add(new Reader("Tom", "tom", "tom1234"));
        readers.add(new Reader("Jerry", "jerry", "jerry1234"));
        readers.add(new Reader("Spike", "spike", "spike1234"));

        library = new Library(readers, books);
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database"));
            library = (Library) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("База данных повреждена или не найдена!");
        }
        System.out.println("Добро пожаловать в систему управлением библиотекой");
        System.out.println("*******************************");
        mainMenu();

        System.out.println("hello world!");

        System.out.println("Privet");

    }

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Поиск книг");
            System.out.println("2. Просмотр всех книг");
            System.out.println("3. Просмотр всех читателей");
            System.out.println("4. Просмотр всех взятых книг");
            System.out.println("5. Добавить книгу");
            System.out.println((loggedReader != null) ? "6. Взять книгу" : "6. Войти");
            System.out.println((loggedReader != null) ? "7. Вернуть книгу" : "7. Зарегистрироваться");
            System.out.println("8. Выход");
            try {
                int choice = scanner.nextInt();
                if (choice < 9 && choice > 0) {
                    switch (choice) {
                        case 1:
                            Book.searchBook();
                            break;
                        case 2:
                            Book.getAllBooks(library.getBookList());
                            break;
                        case 3:
                            Reader.getAllReaders(library.getReaderList());
                            break;
                        case 4:
                            Book.getAllTakenBooks(library.getBookList());
                            break;
                        case 5:
                            Book.addNewBook();
                            break;
                        case 6:
                            if (loggedReader != null) {
                                Reader.takeBook();
                            } else login();
                            break;
                        case 7:
                            if (loggedReader != null) {
                                Reader.returnBook();
                            } else Reader.createAccount();
                            break;
                        case 8:
                            System.out.println("Выход из системы...");
                            try {
                                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database"));
                                oos.writeObject(library);
                                oos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("Запись не удалась!");
                            }
                            System.exit(0);
                    }
                } else {
                    System.out.println("Введите правильное число!");
                    scanner.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Неверный формат ввода! Повторите ввод");
            }
        }
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        while (true) {
            try {
                System.out.println("Введите ваш логин...");
                String login = scanner.nextLine();
                System.out.println("Введите ваш пароль...");
                String password = scanner.nextLine();
                while (true) {
                    for (Reader r : library.getReaderList()) {
                        if (r.getLogin().equals(login) && r.getPassword().equals(password)) {
                            System.out.print("Здраствуйте, " + r.getName() + "! ");
                            System.out.println("Что будем делать сегодня?");
                            System.out.println("******************************************");
                            System.out.println();
                            loggedReader = r;
                            mainMenu();
                        }
                    }
                    if (count < 2) {
                        count++;
                        System.out.println("Неправильный логин или пароль! У вас осталось " + (3 - count) + " попыток ввода");
                        System.out.println("Введите ваш логин...");
                        login = scanner.nextLine();
                        System.out.println("Введите ваш пароль...");
                        password = scanner.nextLine();
                    } else {
                        System.out.println("Вы 3 раза ввели неправильный логин или пароль. Система закрывается!");
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                System.out.println("Неправильный формат ввода!");
            }
        }
    }
}