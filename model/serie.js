import SerieDAO from "../repository/serieDAO.js";

export default class Serie{
    #id
 
    getId(){
        return this.id;
    }
    
    setId(id){
        this.id = id;
    }

    constructor(id = 0) {
        this.#id = id;
    }
    

    toJSON(){
        return{
            "id": this.#id
        };
    }

    async gravar(){
        const serieDAO = new SerieDAO();
        return await serieDAO.gravar(this);
    }

    /*async alterar(){
        const serieDAO = new SerieDAO();
        return await serieDAO.alterar(this);
    }*/

    async apagar(){
        const serieDAO = new SerieDAO();
        return await serieDAO.apagar(this.#id);
    }

    async consultar(filtro){
        const serieDAO = new SerieDAO();
        return await serieDAO.get(filtro);
    }
}