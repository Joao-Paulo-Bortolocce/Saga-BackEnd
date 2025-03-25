import SalasDAO from '../repository/salasDAO.js';

export default class Salas {

    #id;
    #ncarteiras;

    constructor(id = 0, ncarteiras = 0) {
        this.#id = id;
        this.#ncarteiras = ncarteiras;
    }

    getId() {
        return this.#id;
    }

    setId(novoid) {
        this.#id = novoid;
    }

    getNcarteira() {
        return this.#ncarteiras;
    }

    setNcarteira(novocarteiras) {
        this.#ncarteiras = novocarteiras;
    }

    toJSON() {
        return {
            "id": this.#id,
            "ncarteiras": this.#ncarteiras,
        };
    }

    async gravar() {
        const salasDAO = new SalasDAO();
        return await salasDAO.gravar(this);
    }

    async alterar() {
        const salasDAO = new SalasDAO();
        return await salasDAO.alterar(this);
    }

    async apagar() {
        const salasDAO = new SalasDAO();
        return await salasDAO.apagar(this.#id);
    }

    async consultar(filtro) {
        const salasDAO = new SalasDAO();
        return await salasDAO.get(filtro);
    }
}
