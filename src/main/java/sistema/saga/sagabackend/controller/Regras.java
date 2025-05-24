package sistema.saga.sagabackend.controller;

import sistema.saga.sagabackend.model.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public class Regras {

    public static boolean  verificaIntegridade(String elemento) {
        return elemento != null && !elemento.trim().isEmpty();
    }

    public static boolean verificaIntegridade(int elemento) {
        return elemento >= 0;
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

        String cpf = (String) pessoa.get("pessoa_cpf");
        String rg = (String) pessoa.get("pessoa_rg");
        String nome = (String) pessoa.get("pessoa_nome");
        java.sql.Date data = (java.sql.Date) pessoa.get("pessoa_datanascimento");
        LocalDate dataNascimento = data.toLocalDate();
        String sexo = (String) pessoa.get("pessoa_sexo");
        String locNascimento = (String) pessoa.get("pessoa_locNascimento");
        String estadoNascimento = (String) pessoa.get("pessoa_estadoNascimento");
        String estadoCivil = (String) pessoa.get("pessoa_estadoCivil");
        Map<String, Object>  end= (Map<String, Object>) pessoa.get("endereco");

        Endereco endereco = Regras.HashToEndereco(end);

        return new Pessoa(cpf, rg, nome, dataNascimento, sexo, locNascimento, estadoNascimento, endereco, estadoCivil);
    }
    public static Aluno HashToAluno(Map<String, Object> aluno){
        if(aluno.isEmpty())
            return null;
        int ra = (int) aluno.get("aluno_ra");
        String restricaoMedica = (String) aluno.get("aluno_restricaomedica");
        Pessoa pessoa= Regras.HashToPessoa((Map<String, Object>) aluno.get("pessoa"));

        return new Aluno(ra,restricaoMedica,pessoa);
    }

    public static AnoLetivo HashToAnoLetivo(Map<String, Object> anoMap) {
        if (anoMap.isEmpty())
            return null;

        int id = (int) anoMap.get("anoletivo_id");
        LocalDate inicio = ((java.sql.Date) anoMap.get("anoletivo_inicio")).toLocalDate();
        LocalDate termino = ((java.sql.Date) anoMap.get("anoletivo_termino")).toLocalDate();

        return new AnoLetivo(id, inicio, termino);
    }

    public static Serie HashToSerie(Map<String, Object> serieMap) {
        if (serieMap.isEmpty())
            return null;

        int id = (int) serieMap.get("serie_id");
        int numero = (int) serieMap.get("serie_num");
        String descricao = (String) serieMap.get("serie_descr");

        return new Serie(id, numero, descricao);
    }

    public static Turma HashToTurma(Map<String, Object> turmaMap) {
        if (turmaMap.isEmpty())
            return null;
        Map<String, Object> serieMap = (Map<String, Object>) turmaMap.get("serie");
        Map<String, Object> anoLetivoMap = (Map<String, Object>) turmaMap.get("anoletivo");
        char letra = (char) turmaMap.get("turma_letra");
        Serie serie = Regras.HashToSerie(serieMap);
        AnoLetivo anoLetivo = Regras.HashToAnoLetivo(anoLetivoMap);
        return new Turma(anoLetivo, serie, letra);
    }
}
