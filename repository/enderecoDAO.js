import conectar from "./Conexao.js";
import Endereco from "../model/endereco.js";

export default class EnderecoDAO {
    constructor() {
        this.init();
    }

    async init() {
        try {
            const conexao = await conectar.connect();
            const sql = `
            CREATE TABLE IF NOT EXISTS endereco (
                endereco_id SERIAL PRIMARY KEY,
                endereco_rua VARCHAR(255) NOT NULL,
                endereco_numero INT NOT NULL,
                endereco_complemento VARCHAR(255),
                endereco_cep VARCHAR(20) NOT NULL,
                endereco_uf VARCHAR(2) NOT NULL,
                endereco_cidade VARCHAR(100) NOT NULL
            );
            `;
            await conexao.query(sql);
            await conexao.release();
        } catch (erro) {
            console.log("Erro ao iniciar tabela endereco: " + erro.message);
        }
    }

    async gravar(endereco) {
        if (endereco instanceof Endereco) {
            const conexao = await conectar.connect();
            const sql = `INSERT INTO endereco (endereco_rua, endereco_numero, endereco_complemento, endereco_cep, endereco_uf, endereco_cidade) 
                VALUES ($1, $2, $3, $4, $5, $6) RETURNING endereco_id`;
            const parametros = [
                endereco.rua,
                endereco.numero,
                endereco.complemento,
                endereco.cep,
                endereco.uf,
                endereco.cidade
            ];
            const resultado = await conexao.query(sql, parametros);
            await conexao.release();
            return resultado.rows[0].endereco_id;
        }
    }

    async alterar(endereco) {
        if (endereco instanceof Endereco) {
            const conexao = await conectar.connect();
            const sql = `UPDATE endereco SET endereco_rua = $1, endereco_numero = $2, endereco_complemento = $3, endereco_cep = $4, endereco_uf = $5, endereco_cidade = $6 
                WHERE endereco_id = $7`;
            const parametros = [
                endereco.rua,
                endereco.numero,
                endereco.complemento,
                endereco.cep,
                endereco.uf,
                endereco.cidade,
                endereco.id
            ];
            await conexao.query(sql, parametros);
            await conexao.release();
        }
    }

    async apagar(id) {
        const conexao = await conectar.connect();
        const sql = `DELETE FROM endereco WHERE endereco_id = $1`;
        const parametros = [id];
        await conexao.query(sql, parametros);
        await conexao.release();
    }

    async get(filtro) {
        const conexao = await conectar.connect();
        let parametros;
        let sql;
        if (!filtro) {
            filtro = "";
        }
        if (!isNaN(filtro)) {
            sql = `SELECT * FROM endereco WHERE endereco_id = $1`;
            parametros = [filtro];
        } else {
            sql = `SELECT * FROM endereco WHERE endereco_rua LIKE $1 OR endereco_cidade LIKE $2`;
            parametros = [`%${filtro}%`, `%${filtro}%`];
        }
        const resultado = await conexao.query(sql, parametros);
        await conexao.release();
        const linhas = resultado.rows || [];
        if (linhas.length > 0) {
            let listaEnderecos = linhas.map(linha => new Endereco(
                linha.endereco_id,
                linha.endereco_rua,
                linha.endereco_numero,
                linha.endereco_complemento,
                linha.endereco_cep,
                linha.endereco_uf,
                linha.endereco_cidade
            ));
            return listaEnderecos.length === 1 ? listaEnderecos[0] : listaEnderecos;
        }
        return null;
    }
}