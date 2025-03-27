import GraduacaoDAO from "../repository/graduacaoDAO.js";

export default class Graduacao {

    #id;
    #descricao;

    constructor(id = 0, descricao = 0) {
        this.#id = id;
        this.#descricao = descricao;
    }

    getId() {
        return this.#id;
    }

    setId(novoid) {
        this.#id = novoid;
    }

    getDescricao() {
        return this.#descricao;
    }

    setDescricao(novadescricao) {
        this.#descricao = novadescricao;
    }

    toJSON() {
        return {
            "id": this.#id,
            "descricao": this.#descricao,
        };
    }

    async gravar() {
        const graduacaoDAO = new GraduacaoDAO();
        return await graduacaoDAO.gravar(this);
    }

    async alterar() {
        const graduacaoDAO = new GraduacaoDAO();
        return await graduacaoDAO.alterar(this);
    }

    async apagar() {
        const graduacaoDAO = new GraduacaoDAO();
        return await graduacaoDAO.apagar(this.#id);
    }

    async consultar(filtro) {
        const graduacaoDAO = new GraduacaoDAO();
        return await graduacaoDAO.get(filtro);
    }
}
