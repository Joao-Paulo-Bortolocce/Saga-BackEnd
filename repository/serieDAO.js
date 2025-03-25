import conectar from "./Conexao.js";
import Serie from "../model/serie.js";

export default class SerieDAO{
    constructor(){
        this.init();
    }

    async init() {
        try {
            const conexao = await conectar.connect();
            const sql = `
                CREATE TABLE IF NOT EXISTS serie (
                    serie_id INT NOT NULL,
                    CONSTRAINT pk_serie PRIMARY KEY(serie_id)
                );
            `;
            await conexao.query(sql);
            conexao.release();
        } 
        catch (erro) {
            console.log("Erro ao iniciar tabela serie: " + erro.message);
        }
    }
    

    async gravar(serie) {
        if (serie instanceof Serie) {
            const conexao = await conectar.connect();
            const sql = `INSERT INTO serie(serie_id) VALUES($1)`;
            const parametros = [serie.id];
    
            await conexao.query(sql, parametros);
            conexao.release();
        }
    }
    
   /* async alterar(serie) {
        if (serie instanceof Serie) {
            const conexao = await conectar.connect();
            const sql = `UPDATE serie SET serie_id = $1 WHERE serie_id = $2`;
            const parametros = [serie.id, serie.id]; // Ou o novo e o antigo id se for o caso
    
            await conexao.query(sql, parametros);
            conexao.release();
        }
    }*/
    
    async apagar(id) {
        const conexao = await conectar.connect();
        const sql = `DELETE FROM serie WHERE serie_id = $1`;
        const parametros = [id];
    
        await conexao.query(sql, parametros);
        conexao.release();
    }
    
    

    async get(filtro) {
        const conexao = await conectar.connect();
        let sql;
        let parametros;
        let unico = false;
    
        if (filtro === undefined || filtro === "") {
            sql = `SELECT * FROM serie`;
            parametros = [];
        } else if (!isNaN(filtro)) {
            sql = `SELECT * FROM serie WHERE serie_id = $1`;
            parametros = [filtro];
            unico = true;
        } else {
            sql = `SELECT * FROM serie WHERE serie_id::TEXT LIKE $1`;
            parametros = [`%${filtro}%`];
        }
    
        const result = await conexao.query(sql, parametros);
        conexao.release();
    
        if (result.rows.length > 0) {
            let listaSeries = result.rows.map(row => new Serie(row.serie_id));
            return unico ? listaSeries[0] : listaSeries;
        }
        return null;
    }
    
}