package sistema.saga.sagabackend.repository;

import sistema.saga.sagabackend.model.Ficha;
import sistema.saga.sagabackend.model.FichaDaMatricula;
import sistema.saga.sagabackend.model.Matricula;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class FichaDaMatriculaDAO {

    public FichaDaMatriculaDAO() {
    }

    public boolean gravar(FichaDaMatricula ficha,Conexao conexao){
        String sql = """
                        INSERT INTO fichadamatricula(
                        	fichadamatricula_matricula_matricula_id, fichadamatricula_ficha_ficha_id, fichadamatricula_observacao, fichadamatricula_status)
                        	VALUES ('#1','#2','#3','#4');
                """;
        sql = sql.replace("#1", "" + ficha.getMatricula().getId());
        sql = sql.replace("#2", "" + ficha.getFicha().getFicha_id());
        sql = sql.replace("#3", "" + ficha.getObservacao());
        sql = sql.replace("#3", "" + ficha.getStatus());

        return conexao.manipular(sql);
    }

    public boolean alterar(FichaDaMatricula ficha, Conexao conexao) {
        String sql = """
        UPDATE public.fichadamatricula
        SET fichadamatricula_observacao = '#3',
            fichadamatricula_status = '#4'
        WHERE fichadamatricula_matricula_matricula_id = #1 
          AND fichadamatricula_ficha_ficha_id = #2;
        """;

        sql = sql.replace("#1", String.valueOf(ficha.getMatricula().getId()));
        sql = sql.replace("#2", String.valueOf(ficha.getFicha().getFicha_id()));
        sql = sql.replace("#3", ficha.getObservacao().replace("'", "''")); // protege aspas simples
        sql = sql.replace("#4", String.valueOf(ficha.getStatus()));

        return conexao.manipular(sql);
    }


    public boolean apagar(FichaDaMatricula ficha,Conexao conexao){
        String sql = """
                        DELETE FROM public.fichadamatricula
                        	WHERE fichadamatricula_matricula_matricula_id='#1' AND fichadamatricula_ficha_ficha_id='#2';
                """;
        sql = sql.replace("#1", "" + ficha.getMatricula().getId());
        sql = sql.replace("#2", "" + ficha.getFicha().getFicha_id());

        return conexao.manipular(sql);
    }




    public List<FichaDaMatricula> getAll(int validadas,Conexao conexao) {
        String sql="";
        if(validadas==2){

             sql = """
                SELECT * FROM fichadamatricula;
                """;
        }
        else{

            if(validadas==1){
                sql = """
                SELECT * FROM fichadamatricula
                    where fichadamatricula_status= 3;
                """;
            }
            else{

                sql = """
                SELECT * FROM fichadamatricula
                    where fichadamatricula_status= 2;
                """;
            }

        }

        List<FichaDaMatricula> fichas = new ArrayList<>();
        try{
            ResultSet resultSet = conexao.consultar(sql);
            while(resultSet.next()) {
                fichas.add(new FichaDaMatricula(new Matricula(resultSet.getInt("fichadamatricula_matricula_matricula_id")),new Ficha(resultSet.getInt("fichadamatricula_ficha_ficha_id")), resultSet.getString("fichadamatricula_observacao"), resultSet.getInt("fichadamatricula_status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fichas;
    }

}
