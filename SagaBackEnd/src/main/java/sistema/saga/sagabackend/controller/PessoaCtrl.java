package sistema.saga.sagabackend.controller;

import sistema.saga.sagabackend.dto.PessoaDTO;
import sistema.saga.sagabackend.model.Endereco;
import sistema.saga.sagabackend.model.Pessoa;

import java.time.LocalDate;
import java.util.Date;

public class PessoaCtrl {

    public PessoaCtrl() {
    }
//    public void gravarPessoa(String cpf, String rg, String nome, Date dataNascimento, String sexo, String locNascimento, String estadoNascimento, Endereco endereco, String estadoCivil){
public void gravarPessoa(PessoaDTO pessoaDTO) {
    if (verificaIntegridade(pessoaDTO.getCpf()) &&
            verificaIntegridade(pessoaDTO.getRg()) &&
            verificaIntegridade(pessoaDTO.getNome()) &&
            verificaIntegridade(pessoaDTO.getSexo()) &&
            verificaIntegridade(pessoaDTO.getLocNascimento()) &&
            verificaIntegridade(pessoaDTO.getEstadoNascimento()) &&
            verificaIntegridade(pessoaDTO.getEstadoCivil()) &&
            verificaIntegridade(pessoaDTO.getEndereco()) &&
            verificaIntegridade(pessoaDTO.getDataNascimento())) {

        Pessoa pessoa = new Pessoa(
                pessoaDTO.getCpf(),
                pessoaDTO.getRg(),
                pessoaDTO.getNome(),
                pessoaDTO.getDataNascimento(),
                pessoaDTO.getSexo(),
                pessoaDTO.getLocNascimento(),
                pessoaDTO.getEstadoNascimento(),
                pessoaDTO.getEndereco(),
                pessoaDTO.getEstadoCivil()
        );

        pessoa.gravarPessoa();
    } else {
        System.out.println("Erro: Dados inválidos.");
    }
}

    // Métodos de validação corrigidos
    private boolean verificaIntegridade(String elemento) {
        return elemento != null && !elemento.trim().isEmpty();
    }

    private boolean verificaIntegridade(LocalDate elemento) {
        return elemento != null;
    }

    private boolean verificaIntegridade(Endereco elemento) {
        return elemento != null;
    }

}
