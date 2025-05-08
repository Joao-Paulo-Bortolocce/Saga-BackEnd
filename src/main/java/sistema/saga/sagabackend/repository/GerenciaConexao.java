package sistema.saga.sagabackend.repository;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

public class GerenciaConexao {
    private  Conexao conexao;
    private static final String URL = "jdbc:postgresql://localhost/";
    private static final String BASE_NAME = "saga";
    private static final String USER = "postgres";
    private static final String SENHA = "postgres123"; //voltar para postgres123

    public GerenciaConexao() {
        Conectar();
    }

    public boolean Conectar() {
        conexao = new Conexao();
        boolean conectado = conexao.conectar(URL, BASE_NAME, USER, SENHA);
        System.out.println(">>> Conexão com banco: " + (conectado ? "SUCESSO" : "FALHOU"));
        return conectado;
    }

    public boolean Desconectar() {
        return conexao.desconectar();
    }

    public Conexao getConexao() {
        return conexao;
    }

    public boolean criarBD(String BD) {
        try {
            String url = "jdbc:postgresql://localhost/";
            Connection con = DriverManager.getConnection(url, "postgres", "postgres123");

            Statement statement = con.createStatement();
            statement.execute("CREATE DATABASE " + BD + " WITH OWNER = postgres ENCODING = 'UTF8'  "
                    + "TABLESPACE = pg_default LC_COLLATE = 'Portuguese_Brazil.1252'  "
                    + "LC_CTYPE = 'Portuguese_Brazil.1252'  CONNECTION LIMIT = -1;");
            statement.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public boolean backup(String arquivo, String database) throws Exception {
        final ArrayList<String> comandos = new ArrayList();
        comandos.add("bdutil/pg_dump.exe");
        comandos.add("--host");
        comandos.add("localhost"); //ou  comandos.add("192.168.0.1");
        comandos.add("--port");
        comandos.add("5432");
        comandos.add("--username");
        comandos.add("postgres");
        comandos.add("--format");
        comandos.add("custom");
        comandos.add("--blobs");
        comandos.add("--verbose");
        comandos.add("--file");
        comandos.add("bdutil/" + arquivo);
        comandos.add(database);
        ProcessBuilder pb = new ProcessBuilder(comandos);
        pb.environment().put("PGPASSWORD", "postgres123");
        try {
            final Process process = pb.start();
            final BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();
            process.waitFor();
            process.destroy();
            JOptionPane.showMessageDialog(null, "Backup realizado com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro na realização do backup." + e);
            return false;
        }
    }

    public boolean restaurar(String arquivo, String database) throws Exception {
        final ArrayList<String> comandos = new ArrayList();
        comandos.add("bdutil/pg_restore.exe");
        comandos.add("-c");
        comandos.add("--host");
        comandos.add("localhost");
        comandos.add("--port");
        comandos.add("5432");
        comandos.add("--username");
        comandos.add("postgres");
        comandos.add("--dbname");
        comandos.add(database);
        comandos.add("--verbose");
        comandos.add("bdutil/" + arquivo);
        ProcessBuilder pb = new ProcessBuilder(comandos);
        pb.environment().put("PGPASSWORD", "postgres123");
        try {
            final Process process = pb.start();
            final BufferedReader r = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();
            process.waitFor();
            process.destroy();
            JOptionPane.showMessageDialog(null, "Restauração com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro na restauração.");
            return false;
        }
    }
}