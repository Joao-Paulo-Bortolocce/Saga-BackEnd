import conectar from "./Conexao.js";
import Graduacao from "../model/graduacao.js";

export default class GraduacaoDAO {
    constructor() {
        this.init();
    }

    async init() {
        try {
            const conexao = await conectar.connect();
            const sql = `
            CREATE TABLE IF NOT EXISTS graduacao (
                graduacao_id SERIAL PRIMARY KEY,
                graduacao_descricao VARCHAR(50) NOT NULL
            );
            `;
            await conexao.query(sql);
            await conexao.release();
        } catch (erro) {
            console.log("Erro ao iniciar tabela graduacao: " + erro.message);
        }
    }

    async gravar(graduacao) {
        if (graduacao instanceof Graduacao) {
            const conexao = await conectar.connect();
            const sql = `INSERT INTO graduacao(graduacao_id, graduacao_descricao)
	            'VALUES ($1, $2)`;
            const parametros = [
                graduacao.id,
                graduacao.descricao
            ];
            await conexao.query(sql, parametros);
            await conexao.release();
        }
    }

    async alterar(graduacao) {
        if (salas instanceof Salas) {
            const conexao = await conectar.connect();
            const sql = `UPDATE graduacao SET graduacao_descricao=$1 WHERE graduacao_id=$2`;
            const parametros = [
                graduacao.descricao,
                graduacao.id
            ];
            await conexao.query(sql, parametros);
            await conexao.release();
        }
    }

    async apagar(id) {
        const conexao = await conectar.connect();
        const sql = `DELETE FROM graduacao WHERE graduacao_id = $1`;
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
            sql= `SELECT * FROM graduacao WHERE graduacao_id = $1`;
            unico=true;
            parametros = [filtro];
        }
        else{
            sql = `SELECT * FROM graduacao WHERE graduacao_descricao LIKE $1`;
            parametros=[`%${filtro}%`]
        }
        const resultado = await conexao.query(sql, parametros);
        const linhas = resultado.rows || [];
        if(linhas.length>0){
            let listaGraduacao=[];
            for(const linha of linhas){
                const graduacao= new Graduacao(
                linha.graduacao_id,
                linha.graduacao_descricao
                );
                listaGraduacao.push(graduacao);
                }
                if(unico)
                    return listaGraduacao[0];
                return listaGraduacao;
        }
        return null;
    }
}