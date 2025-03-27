import conectar from "./Conexao.js";
import Salas from "../model/salas.js";

export default class SalasDAO {
    constructor() {
        this.init();
    }

    async init() {
        try {
            const conexao = await conectar.connect();
            const sql = `
            CREATE TABLE IF NOT EXISTS salas (
                salas_id SERIAL PRIMARY KEY,
                salas_ncarteiras INT NOT NULL
            );
            `;
            await conexao.query(sql);
            await conexao.release();
        } catch (erro) {
            console.log("Erro ao iniciar tabela salas: " + erro.message);
        }
    }

    async gravar(salas) {
        if (salas instanceof Salas) {
            const conexao = await conectar.connect();
            const sql = `INSERT INTO salas (salas_ncarteiras) VALUES ($1)`;
            const parametros = [salas.getNcarteira()];
            await conexao.query(sql,parametros);
            conexao.release();
        }
    }

    async alterar(salas) {
        if (salas instanceof Salas) {
            const conexao = await conectar.connect();
            const sql = `UPDATE salas SET salas_ncarteiras=$1 WHERE salas_id=$2`;
            const parametros = [
                salas.getNcarteira(), salas.getId()
            ];
            await conexao.query(sql, parametros);
            conexao.release();
        }
    }

    async apagar(id) {
        const conexao = await conectar.connect();
        const sql = `DELETE FROM salas WHERE salas_id = $1`;
        let parametros=[id]
        await conexao.query(sql, parametros);
        await conexao.release();
    }
    
    async get(filtro) {
        const conexao = await conectar.connect();
        let sql;
        let parametros;
        let unico = false;

        if (!filtro) {
            sql = `SELECT * FROM salas`;
            parametros = [];
        } 
        else if (!isNaN(filtro)) {
            sql = `
              SELECT * FROM salas WHERE salas_id = $1 OR salas_ncarteiras = $1
            `;
            parametros = [filtro];
            unico = false;
        }
        else {
            sql = `SELECT * FROM salas WHERE salas_id = $1 OR salas_ncarteiras = $1`;
            parametros = [`%${filtro}%`];
        }

        const result = await conexao.query(sql, parametros);
        conexao.release();

        if (result.rows.length > 0) {
            const listaSalas = result.rows.map(row =>
                new Salas(row.salas_id, row.salas_ncarteiras)
            );
            return unico ? listaSalas[0] : listaSalas;
        }
        return null;
    }
}