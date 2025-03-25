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
            CREATE TABLE IF NOT EXISTS pessoa (
                salas_id integer NOT NULL,
                salas_ncarteiras integer NOT NULL,
                CONSTRAINT pk_salas PRIMARY KEY (salas_id)
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
            const sql = `INSERT INTO salas(salas_id, salas_ncarteiras)
	            'VALUES ($1, $2)`;
            const parametros = [
                salas.id,
                salas.ncarteiras
            ];
            await conexao.query(sql, parametros);
            await conexao.release();
        }
    }

    async alterar(salas) {
        if (salas instanceof Salas) {
            const conexao = await conectar.connect();
            const sql = `UPDATE salas SET salas_ncarteiras=$1 WHERE salas_id=$2`;
            const parametros = [
                salas.ncarteiras,
                salas.id
            ];
            await conexao.query(sql, parametros);
            await conexao.release();
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
        let unico= false;
        let parametros;
        let sql;
        if(filtro===undefined)
            filtro="";
        if(filtro!="" && !isNaN(filtro[0])){
            sql= `SELECT * FROM salas WHERE salas_id = $1`;
            unico=true;
            parametros = [filtro];
        }
        else{
            sql = `SELECT * FROM salas WHERE salas_ncarteiras LIKE $1`;
            parametros=[`%${filtro}%`]
        }
        const resultado = await conexao.query(sql, parametros);
        const linhas = resultado.rows || [];
        if(linhas.length>0){
            let listaSalas=[];
            for(const linha of linhas){
                const salas= new Salas(
                linha.salas_id,
                linha.salas_ncarteiras
                );
                    listaSalas.push(salas);
                }
                if(unico)
                    return listaSalas[0];
                return listaSalas;
        }
        return null;
    }
}