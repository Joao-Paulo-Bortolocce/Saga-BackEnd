export default class Endereco {
    #rua;
    #numero;
    #complemento;
    #cep;
    #uf;
    #cidade;

    constructor(rua = "", numero = 0, complemento = "", cep = "", uf = "", cidade = "") {
        this.#rua = rua;
        this.#numero = numero;
        this.#complemento = complemento;
        this.#cep = cep;
        this.#uf = uf;
        this.#cidade = cidade;
    }

    get rua() {
        return this.#rua;
    }

    set rua(novaRua) {
        this.#rua = novaRua;
    }

    get numero() {
        return this.#numero;
    }

    set numero(novoNumero) {
        this.#numero = novoNumero;
    }

    get complemento() {
        return this.#complemento;
    }

    set complemento(novoComplemento) {
        this.#complemento = novoComplemento;
    }

    get cep() {
        return this.#cep;
    }

    set cep(novoCep) {
        this.#cep = novoCep;
    }

    get uf() {
        return this.#uf;
    }

    set uf(novoUf) {
        this.#uf = novoUf;
    }

    get cidade() {
        return this.#cidade;
    }

    set cidade(novaCidade) {
        this.#cidade = novaCidade;
    }

    toJSON() {
        return {
            rua: this.#rua,
            numero: this.#numero,
            complemento: this.#complemento,
            cep: this.#cep,
            uf: this.#uf,
            cidade: this.#cidade
        };
    }
}
