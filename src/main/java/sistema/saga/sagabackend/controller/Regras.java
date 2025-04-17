package sistema.saga.sagabackend.controller;

import sistema.saga.sagabackend.model.Endereco;
import sistema.saga.sagabackend.model.Pessoa;

import java.time.LocalDate;
import java.util.Map;

public class Regras {

    public static boolean  verificaIntegridade(String elemento) {
        return elemento != null && !elemento.trim().isEmpty();
    }

    public static boolean verificaIntegridade(int elemento) {
        return elemento > 0;
    }

    public static boolean verificaIntegridade(LocalDate elemento) {
        return elemento != null;
    }

    public static boolean verificaIntegridade(Endereco elemento) {
        return elemento != null;
    }

    public static Endereco HashToEndereco(Map<String, Object> end){
        if(end.isEmpty())
            return null;
        Endereco endereco = new Endereco(
                ((Number) end.get("endereco_id")).longValue(),
                (String) end.get("endereco_rua"),
                (int) end.get("endereco_num"),
                (String) end.get("endereco_complemento"),
                (String) end.get("endereco_cep"),
                (String) end.get("endereco_cidade"),
                (String) end.get("endereco_uf")
        );
        return endereco;
    }

    public static Pessoa HashToPessoa(Map<String, Object> pessoa){
        if(pessoa.isEmpty())
            return null;

        String cpf = (String) pessoa.get("cpf");
        String rg = (String) pessoa.get("rg");
        String nome = (String) pessoa.get("nome");
        java.sql.Date data = (java.sql.Date) pessoa.get("dataNascimento");
        LocalDate dataNascimento = data.toLocalDate();
        String sexo = (String) pessoa.get("sexo");
        String locNascimento = (String) pessoa.get("locNascimento");
        String estadoNascimento = (String) pessoa.get("estadoNascimento");
        String estadoCivil = (String) pessoa.get("estadoCivil");
        Map<String, Object>  end= (Map<String, Object>) pessoa.get("endereco");

        Endereco endereco = new Endereco(
                ((Number) end.get("endereco_id")).longValue(),
                (String) end.get("endereco_rua"),
                (int) end.get("endereco_num"),
                (String) end.get("endereco_complemento"),
                (String) end.get("endereco_cep"),
                (String) end.get("endereco_cidade"),
                (String) end.get("endereco_uf")
        );

        Pessoa novaPessoa = new Pessoa(cpf, rg, nome, dataNascimento, sexo, locNascimento, estadoNascimento, endereco, estadoCivil);

        return novaPessoa;
    }
}
