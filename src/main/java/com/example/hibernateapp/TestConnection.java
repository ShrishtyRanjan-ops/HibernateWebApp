
package com.example.hibernateapp;

import org.hibernate.SessionFactory;

public class TestConnection {

    public static void main(String[] args) {
        try {
            SessionFactory factory =
                    HibernateUtil.getSessionFactory();

            System.out.println(
                    "Hibernate connected successfully!"
            );

            factory.close();

        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
