import Endereco from "../model/endereco.js";
import PessoaDAO from "../repository/pessoaDAO.js";

export default class Pessoa {

    #cpf;
    #rg;
    #nome;
    #dataNascimento;
    #sexo;
    #locNascimento;
    #estadoNascimento;
    #endereco;
    #estadoCivil;

    get cpf() {
        return this.#cpf;
    }

    set cpf(novoCpf) {
        this.#cpf = novoCpf;
    }

    get rg() {
        return this.#rg;
    }

    set rg(novoRg) {
        this.#rg = novoRg;
    }

    get nome() {
        return this.#nome;
    }

    set nome(novoNome) {
        this.#nome = novoNome;
    }

    get dataNascimento() {
        return this.#dataNascimento;
    }

    set dataNascimento(novaDataNascimento) {
        this.#dataNascimento = novaDataNascimento;
    }

    get sexo() {
        return this.#sexo;
    }

    set sexo(novoSexo) {
        this.#sexo = novoSexo;
    }

    get locNascimento() {
        return this.#locNascimento;
    }

    set locNascimento(novoLocNascimento) {
        this.#locNascimento = novoLocNascimento;
    }

    get estadoNascimento() {
        return this.#estadoNascimento;
    }

    set estadoNascimento(novoEstadoNascimento) {
        this.#estadoNascimento = novoEstadoNascimento;
    }

    get endereco() {
        return this.#endereco;
    }

    set endereco(novoEndereco) {
        this.#endereco = novoEndereco;
    }

    get enderecoId(){
        return this.#endereco.id;
    }

    get estadoCivil() {
        return this.#estadoCivil;
    }

    set estadoCivil(novoEstadoCivil) {
        this.#estadoCivil = novoEstadoCivil;
    }

    constructor(cpf = "", rg = "", nome = "", dataNascimento = "", sexo = "", locNascimento = "", estadoNascimento = "", endereco = new Endereco(), estadoCivil = "") {
        this.#cpf = cpf;
        this.#rg = rg;
        this.#nome = nome;
        this.#dataNascimento = dataNascimento;
        this.#sexo = sexo;
        this.#locNascimento = locNascimento;
        this.#estadoNascimento = estadoNascimento;
        this.#endereco = endereco;
        this.#estadoCivil = estadoCivil;
    }

    toJSON() {
        return {
            "cpf": this.#cpf,
            "rg": this.#rg,
            "nome": this.#nome,
            "dataNascimento": this.#dataNascimento,
            "sexo": this.#sexo,
            "locNascimento": this.#locNascimento,
            "estadoNascimento": this.#estadoNascimento,
            "endereco": this.#endereco,  
            "estadoCivil": this.#estadoCivil
        };
    }

    async gravar() {
        const pessoaDAO = new PessoaDAO();
        return await pessoaDAO.gravar(this);
    }

    async alterar() {
        const pessoaDAO = new PessoaDAO();
        return await pessoaDAO.alterar(this);
    }

    async apagar() {
        const pessoaDAO = new PessoaDAO();
        return await pessoaDAO.apagar(this.#cpf);
    }

    async consultar(filtro) {
        const pessoaDAO = new PessoaDAO();
        return await pessoaDAO.get(filtro);
    }
}
