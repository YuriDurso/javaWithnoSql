package com.mycompany.supermarket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Essentials {

    public static void programa(Connection conn) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 10; i++) {
            System.out.println("1- Inserir \n2- Imprimir \n3- Excluir \n4- Alterar \n0- Encerrar");
            int option = sc.nextInt();
            sc.nextLine();
            if (option == 1) {
                System.out.println("Nome: ");
                String nome = sc.nextLine();
                System.out.println("Marca: ");
                String marca = sc.nextLine();
                System.out.println("Quantidade: ");
                int quantidade = sc.nextInt();
                System.out.println("Preco: ");
                float preco = sc.nextFloat();
                inserirProduto(conn, nome, marca, quantidade, preco);
            } else if (option == 2) {
                imprimirProdutos(conn);

            } else if (option == 3) {
                System.out.println("ID do produto: ");
                int idProduto = sc.nextInt();
                System.out.println("Quantidade a ser excluída: ");
                int quantidadeExcluir = sc.nextInt();
                excluirUnidadesProduto(conn, idProduto, quantidadeExcluir);
            } else if (option == 4) {
                System.out.println("ID do produto a ser alterado: ");
                int idProduto = sc.nextInt();
                sc.nextLine();
                alteraProduto(conn, idProduto, sc);
            } else if (option == 0) {
                break;
            } else {
                System.out.println("Caracter inválido");
            }
        }
    }

    public static void alteraProduto(Connection conn, int idProduto, Scanner sc) {
        //String sql = "UPDATE produtos (NOME, MARCA, QNT_ESTQ, PRECO) VALUES (?, ?, ?, ?) WHERE ID=?";
        String sql = "UPDATE produtos SET NOME = ?, MARCA = ?, QNT_ESTQ = ?, PRECO = ? WHERE ID = ?";

        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println("Novo Nome: ");
            String novoNome = sc.nextLine();
            System.out.println("Nova Marca: ");
            String novaMarca = sc.nextLine();
            System.out.println("Nova Quantidade em Estoque: ");
            int novaQuantidade = sc.nextInt();
            System.out.println("Novo Preço: ");
            float novoPreco = sc.nextFloat();

            stmt.setString(1, novoNome);
            stmt.setString(2, novaMarca);
            stmt.setInt(3, novaQuantidade);
            stmt.setFloat(4, novoPreco);
            stmt.setInt(5, idProduto);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produto alterado com sucesso!");
            } else {
                System.out.println("Produto não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao alterar produto: " + e.getMessage());
        }
    }

    public static void inserirProduto(Connection conn, String nome, String marca, int qntEstoque, double preco) {
        String sql = "INSERT INTO produtos (NOME, MARCA, QNT_ESTQ, PRECO) VALUES (?, ?, ?, ?)";

        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, marca);
            stmt.setInt(3, qntEstoque);
            stmt.setDouble(4, preco);
            stmt.executeUpdate();
            System.out.println("Novo produto inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir novo produto: " + e.getMessage());
        }
    }

    public static void imprimirProdutos(Connection conn) {
        String sql = "SELECT * FROM produtos";

        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String nome = rs.getString("NOME");
                String marca = rs.getString("MARCA");
                int qntEstoque = rs.getInt("QNT_ESTQ");
                double preco = rs.getDouble("PRECO");

                System.out.println("ID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Marca: " + marca);
                System.out.println("Quantidade em Estoque: " + qntEstoque);
                System.out.println("Preço: " + preco);
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao imprimir produtos: " + e.getMessage());
        }
    }

    public static void excluirUnidadesProduto(Connection conn, int idProduto, int quantidadeExcluir) {
        String sql = "UPDATE produtos SET QNT_ESTQ = QNT_ESTQ - ? WHERE ID = ?";

        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantidadeExcluir);
            stmt.setInt(2, idProduto);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Unidades excluídas com sucesso!");
            } else {
                System.out.println("Produto não encontrado ou quantidade insuficiente em estoque.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir unidades: " + e.getMessage());
        }
    }

}
