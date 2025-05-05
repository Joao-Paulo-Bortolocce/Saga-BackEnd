package sistema.saga.sagabackend.repository;
import java.sql.*;

public class Conexao {
    private Connection connect;
    private String erro;

    public Conexao() {
        erro = "";
        connect = null;
    }

    public Connection getConnect() {
        return connect;
    }

    public boolean conectar(String local, String banco, String usuario, String senha) {
        boolean conectado = false;
        try {
            String url = local + banco; // "jdbc:postgresql://localhost/"+banco;
            connect = DriverManager.getConnection(url, usuario, senha);
            conectado = true;
        } catch (SQLException sqlex) {
            erro = "Impossivel conectar com a base de dados: " + sqlex.toString();
        } catch (Exception ex) {
            erro = "Outro erro: " + ex.toString();
        }
        return conectado;
    }

    public String getMensagemErro() {
        return erro;
    }

    public boolean getEstadoConexao() {
        return (connect != null);
    }

    public boolean manipular(String sql) {
        boolean executou = false;
        try {
            System.out.println(">> SQL EXECUTADO:");
            System.out.println(sql);

            Statement statement = connect.createStatement();
            int result = statement.executeUpdate(sql);
            statement.close();

            System.out.println(">> Linhas afetadas: " + result);
            executou = result >= 1;
        } catch (SQLException sqlex) {
            erro = "Erro ao executar SQL: " + sqlex.getMessage();
            System.out.println(">> ERRO no SQL:");
            sqlex.printStackTrace(); // Mostra exatamente o erro do PostgreSQL
        }
        return executou;
    }

    public ResultSet consultar(String sql) {
        ResultSet rs = null;
        try {
            Statement statement = connect.createStatement();
            rs = statement.executeQuery(sql);
        } catch (SQLException sqlex) {
            erro = "Erro: " + sqlex.toString();
            rs = null;
        }
        return rs;
    }

    public int getMaxPK(String tabela, String chave) {
        String sql = "select max(" + chave + ") from " + tabela;
        int max = 0;
        ResultSet rs = consultar(sql);
        try {
            if (rs != null && rs.next())
                max = rs.getInt(1);
        } catch (SQLException sqlex) {
            erro = "Erro: " + sqlex.toString();
            max = -1;
        }
        return max;
    }

    public boolean desconectar() {
        boolean desconectado = false;
        try {
            if (connect != null && !connect.isClosed()) {
                connect.close();
                desconectado = true;
            }
        } catch (SQLException e) {
            erro = "Erro ao desconectar: " + e.toString();
        }
        return desconectado;
    }

    public boolean iniciarTransacao() {
        try {
            if (connect != null && !connect.isClosed()) {
                connect.setAutoCommit(false);
                return true;
            }
        } catch (SQLException e) {
            erro = "Erro ao iniciar transação: " + e.toString();
        }
        return false;
    }

    public boolean commit() {
        try {
            if (connect != null && !connect.isClosed()) {
                connect.commit();
                return true;
            }
        } catch (SQLException e) {
            erro = "Erro ao fazer commit: " + e.toString();
        }
        return false;
    }

    public boolean rollback() {
        try {
            if (connect != null && !connect.isClosed()) {
                connect.rollback();
                return true;
            }
        } catch (SQLException e) {
            erro = "Erro ao fazer rollback: " + e.toString();
        }
        return false;
    }

    public boolean fimTransacao() {
        try {
            if (connect != null && !connect.isClosed()) {
                connect.setAutoCommit(true);
                return true;
            }
        } catch (SQLException e) {
            erro = "Erro ao finalizar transação: " + e.toString();
        }
        return false;
    }
}



//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/saga");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("postgres123");
//        return dataSource;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(dataSource());
//    }