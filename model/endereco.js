import EnderecoDAO from '../repository/enderecoDAO.js'; // Importação do DAO

export default class Endereco {
    constructor(id = 0, rua = "", numero = 0, complemento = "", cep = "", uf = "", cidade = "") {
        this.id = id;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cep = cep;
        this.uf = uf;
        this.cidade = cidade;
    }

    getId() {
        return this.id;
    }

    setId(id) {
        this.id = id;
    }

    getRua() {
        return this.rua;
    }

    setRua(rua) {
        this.rua = rua;
    }

    getNumero() {
        return this.numero;
    }

    setNumero(numero) {
        this.numero = numero;
    }

    getComplemento() {
        return this.complemento;
    }

    setComplemento(complemento) {
        this.complemento = complemento;
    }

    getCep() {
        return this.cep;
    }

    setCep(cep) {
        this.cep = cep;
    }

    getUf() {
        return this.uf;
    }

    setUf(uf) {
        this.uf = uf;
    }

    getCidade() {
        return this.cidade;
    }

    setCidade(cidade) {
        this.cidade = cidade;
    }

    async buscaEndereco(idEndereco) {
        try {
            const enderecoDAO = new EnderecoDAO();
            return await enderecoDAO.get(idEndereco); 
        } catch (erro) {
            throw new Error("Erro ao buscar endereço: " + erro.message);
        }
    }
}
