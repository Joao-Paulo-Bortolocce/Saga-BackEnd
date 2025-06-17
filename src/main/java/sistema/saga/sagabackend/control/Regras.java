package sistema.saga.sagabackend.control;

import sistema.saga.sagabackend.model.*;

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
    public static Endereco HashToEnderecoFront(Map<String, Object> end){
        if(end == null || end.isEmpty())
            return null;
        Endereco endereco = new Endereco(
                ((Number) end.get("id")).longValue(),
                (String) end.get("rua"),
                (int) end.get("numero"),
                (String) end.get("complemento"),
                (String) end.get("cep"),
                (String) end.get("cidade"),
                (String) end.get("uf")
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

    public static Pessoa HashToPessoaFront(Map<String, Object> pessoa){
        if(pessoa == null || pessoa.isEmpty())
            return null;

        String cpf = (String) pessoa.get("cpf");
        String rg = (String) pessoa.get("rg");
        String nome = (String) pessoa.get("nome");
        String dt = (String) pessoa.get("dataNascimento");
        LocalDate dataNascimento = LocalDate.parse(dt);
        String sexo = (String) pessoa.get("sexo");
        String locNascimento = (String) pessoa.get("locNascimento");
        String estadoNascimento = (String) pessoa.get("estadoNascimento");
        String estadoCivil = (String) pessoa.get("estadoCivil");
        Map<String, Object>  end= (Map<String, Object>) pessoa.get("endereco");

        Endereco endereco = Regras.HashToEnderecoFront(end);

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
    public static Aluno HashToAlunoFront(Map<String, Object> aluno){
        if(aluno == null || aluno.isEmpty())
            return null;

        int ra = (int) aluno.get("ra");

        String restricaoMedica = aluno.containsKey("restricaomedica") ? (String) aluno.get("restricaomedica") : "";
        Pessoa pessoa = null;

        if (aluno.containsKey("pessoa")) {
            Map<String, Object> pessoaMap = (Map<String, Object>) aluno.get("pessoa");
            if (pessoaMap != null && !pessoaMap.isEmpty()) {
                pessoa = Regras.HashToPessoaFront(pessoaMap);
            }
        }

        return new Aluno(ra, restricaoMedica, pessoa);
    }

    public static AnoLetivo HashToAnoLetivo(Map<String, Object> anoMap) {
        if (anoMap.isEmpty())
            return null;

        int id = (int) anoMap.get("anoletivo_id");
        LocalDate inicio = ((java.sql.Date) anoMap.get("anoletivo_inicio")).toLocalDate();
        LocalDate fim = ((java.sql.Date) anoMap.get("anoletivo_fim")).toLocalDate();

        return new AnoLetivo(id, inicio, fim);
    }

    public static AnoLetivo HashToAnoLetivoFront(Map<String, Object> anoMap) {
        if (anoMap == null || anoMap.isEmpty())
            return null;

        int id = (int) anoMap.get("id");
        String dt = (String) anoMap.get("inicio");
        LocalDate inicio = LocalDate.parse(dt);
        dt = (String) anoMap.get("fim");
        LocalDate fim= LocalDate.parse(dt);
        return new AnoLetivo(id, inicio, fim);
    }

    public static Serie HashToSerie(Map<String, Object> serieMap) {
        if (serieMap.isEmpty())
            return null;

        int id = (int) serieMap.get("serie_id");
        int numero = (int) serieMap.get("serie_num");
        String descricao = (String) serieMap.get("serie_descr");

        return new Serie(id, numero, descricao);
    }

    public static Serie HashToSerieFront(Map<String, Object> serieMap) {
        if (serieMap == null || serieMap.isEmpty())
            return null;

        int id = (int) serieMap.get("serieId");
        int numero = (int) serieMap.get("serieNum");
        String descricao = (String) serieMap.get("serieDescr");

        return new Serie(id, numero, descricao);
    }

    public static Turma HashToTurma(Map<String, Object> turmaMap) {
        if (turmaMap == null || turmaMap.isEmpty()) return null;

        Map<String, Object> serieMap = (Map<String, Object>) turmaMap.get("serie");
        Map<String, Object> anoLetivoMap = (Map<String, Object>) turmaMap.get("anoletivo");
        Map<String, Object> profissionalMap = (Map<String, Object>) turmaMap.get("profissional");
        Map<String, Object> salaMap = (Map<String, Object>) turmaMap.get("sala");

        Object letraObj = turmaMap.get("turma_letra");
        if (letraObj == null || letraObj.equals(0)) return null;

        char letra;
        if (letraObj instanceof String str && str.length() == 1) {
            letra = str.charAt(0);
        } else if (letraObj instanceof Character c) {
            letra = c;
        } else if (letraObj instanceof Byte b) {
            letra = (char) b.byteValue();
        } else {
            return null;
        }

        if(letra==0)
            return null;

        Serie serie = (serieMap != null) ? Regras.HashToSerie(serieMap) : null;
        AnoLetivo anoLetivo = (anoLetivoMap != null) ? Regras.HashToAnoLetivo(anoLetivoMap) : null;
        Profissional profissional = (profissionalMap != null) ? Regras.HashToProfissional(profissionalMap) : null;
        Sala sala = (salaMap != null) ? Regras.HashToSala(salaMap) : null;

        return new Turma(anoLetivo, serie, letra, profissional, sala);
    }


    public static Profissional HashToProfissional(Map<String, Object> profMap) {
        if (profMap.isEmpty()) return null;
        int ra = (int) profMap.get("profissional_rn");
        return new Profissional(ra);
    }

    public static Sala HashToSala(Map<String, Object> salaMap) {
        if (salaMap.isEmpty()) return null;
        int id = (int) salaMap.get("salas_id");
        return new Sala(id, 0, "");
    }

    public static Turma HashToTurmaFront(Map<String, Object> turmaMap) {
        if (turmaMap == null || turmaMap.isEmpty())
            return null;

        Map<String, Object> serieMap = (Map<String, Object>) turmaMap.get("serie");
        Map<String, Object> anoLetivoMap = (Map<String, Object>) turmaMap.get("anoletivo");
        Map<String, Object> profissionalMap = (Map<String, Object>) turmaMap.get("profissional");
        Map<String, Object> salaMap = (Map<String, Object>) turmaMap.get("sala");

        String letraStr = (String) turmaMap.get("letra");
        if (letraStr == null || letraStr.length() != 1)
            return null;
        char letra = letraStr.charAt(0);

        Serie serie = Regras.HashToSerieFront(serieMap);
        AnoLetivo anoLetivo = Regras.HashToAnoLetivoFront(anoLetivoMap);
        Profissional profissional = Regras.HashToProfissional(profissionalMap); // ou HashToProfissionalFront
        Sala sala = Regras.HashToSala(salaMap); // ou HashToSalaFront

        return new Turma(anoLetivo, serie, letra, profissional, sala);
    }


    public static Matricula HashToMatriculaFront(Map<String, Object> matriculaMap){
        if (matriculaMap.isEmpty())
            return null;

        int id = (int) matriculaMap.get("id");
        String dt = (String) matriculaMap.get("data");
        LocalDate data = LocalDate.parse(dt);
        boolean aprovado = (boolean) matriculaMap.get("aprovado");
        boolean valido = (boolean) matriculaMap.get("valido");

        if (!(verificaIntegridade(id) && verificaIntegridade(data)))
            return null;

        Map<String, Object> serieMap = (Map<String, Object>) matriculaMap.get("serie");
        Map<String, Object> anoLetivoMap = (Map<String, Object>) matriculaMap.get("anoLetivo");
        Map<String, Object> alunoMap = (Map<String, Object>) matriculaMap.get("aluno");

        Serie serie = HashToSerieFront(serieMap);
        AnoLetivo anoLetivo = HashToAnoLetivoFront(anoLetivoMap);
        Aluno aluno = HashToAlunoFront(alunoMap);

        // Novo trecho: montar a Turma com a letra
        Turma turma = null;
        if (matriculaMap.containsKey("turma_letra") && matriculaMap.get("turma_letra") != null) {
            String letra = matriculaMap.get("turma_letra").toString();
            if (!letra.isBlank()) {
                turma = new Turma();
                turma.setLetra(letra.charAt(0));
                turma.setSerie(serie);
                turma.setAnoLetivo(anoLetivo);
            }
        }

        return new Matricula(id, aluno, anoLetivo, serie, turma, aprovado, data, valido);
    }

    public static Graduacao HashToGraduacao(Map<String, Object> graduacaoMap) {
        if (graduacaoMap.isEmpty())
            return null;

        int id = (int) graduacaoMap.get("graduacao_id");
        String descricao = (String) graduacaoMap.get("graduacao_descricao");

        return new Graduacao(id, descricao);
    }
}
