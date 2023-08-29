package com.mycompany.supermarket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Supermarket {

    public static void main(String[] args) {
        Essentials metodosEssenciais = new Essentials();
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/Supermercado?user=root&password=";
            conn = DriverManager.getConnection(url);
            metodosEssenciais.programa(conn);
        } catch (SQLException erro) {
            System.out.println("Driver do banco de dados não encontrado ou erro na consulta.");
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


