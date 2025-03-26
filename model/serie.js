import SerieDAO from "../repository/serieDAO.js";

export default class Serie{
    #id
    #serieNum
    #descricao
 
    getId(){
        return this.#id;
    }
    
    setId(id){
        this.id = id;
    }

    getSerieNum(){
        return this.#serieNum;
    }
    
    setSerieNum(id){
        this.serieNum = serieNum;
    }

    getDescricao(){
        return this.#descricao;
    }

    setDescricao(){
        this.descricao = descricao;
    }

    constructor(serieNum = 0, descricao = "", id = 0) {
        this.#id = id;
        this.#serieNum = serieNum;
        this.#descricao = descricao;
    }
    

    toJSON(){
        return{
            "id": this.#id,
            "serieNum": this.#serieNum,
            "descricao": this.#descricao
        };
    }

    async gravar() {
        const serieDAO = new SerieDAO();
        return await serieDAO.gravar(this);
    }

    async alterar() {
        const serieDAO = new SerieDAO();
        return await serieDAO.alterar(this);
    }

    async apagar(id) {
        const serieDAO = new SerieDAO();
        return await serieDAO.apagar(id);
    }

    async consultar(filtro) {
        const serieDAO = new SerieDAO();
        return await serieDAO.get(filtro);
    }
}