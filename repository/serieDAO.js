import conectar from "./Conexao.js";
import Serie from "../model/serie.js";

export default class SerieDAO {

    constructor() {
        this.init();
    }

    async init() {
        try {
            const conexao = await conectar.connect();
            const sql = `
                CREATE TABLE IF NOT EXISTS serie (
                    serie_id SERIAL PRIMARY KEY,
                    serie_num INT NOT NULL,
                    serie_descr VARCHAR NOT NULL
                );
            `;
            await conexao.query(sql);
            conexao.release();
        } catch (erro) {
            console.log("Erro ao iniciar tabela serie: " + erro.message);
        }
    }

    async gravar(serie) {
        if (serie instanceof Serie) {
            const conexao = await conectar.connect();
            const sql = `INSERT INTO serie (serie_num, serie_descr) VALUES ($1, $2)`;
            const parametros = [serie.getSerieNum(), serie.getDescricao()];
            await conexao.query(sql, parametros);
            conexao.release();
        }
    }

    async alterar(serie) {
        if (serie instanceof Serie) {
            const conexao = await conectar.connect();
            const sql = `UPDATE serie SET serie_num = $1, serie_descr = $2 WHERE serie_id = $3`;
            const parametros = [serie.getSerieNum(), serie.getDescricao(), serie.getId()];
            await conexao.query(sql, parametros);
            conexao.release();
        }
    }

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

        if (!filtro) {
            sql = `SELECT * FROM serie`;
            parametros = [];
        } 
        else if (!isNaN(filtro)) {
            sql = `SELECT * FROM serie WHERE serie_id = $1`;
            parametros = [filtro];
            unico = true;
        } 
        else {
            sql = `SELECT * FROM serie WHERE serie_num::TEXT LIKE $1 OR serie_descr ILIKE $1`;
            parametros = [`%${filtro}%`];
        }

        const result = await conexao.query(sql, parametros);
        conexao.release();

        if (result.rows.length > 0) {
            const listaSeries = result.rows.map(row =>
                new Serie(row.serie_num, row.serie_descr, row.serie_id)
            );
            return unico ? listaSeries[0] : listaSeries;
        }
        return null;
    }
}
