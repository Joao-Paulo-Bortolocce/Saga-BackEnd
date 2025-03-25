import conectar from "./Conexao.js";
import Endereco from "../model/endereco.js"; 

export default class EnderecoDAO {
    constructor() {
        this.init();
    }


    async init() {
        try {
            const conexao = await conectar();
            const sql = `
            CREATE TABLE IF NOT EXISTS endereco (
                endereco_id INT AUTO_INCREMENT PRIMARY KEY,
                endereco_rua VARCHAR(255) NOT NULL,
                endereco_numero INT NOT NULL,
                endereco_complemento VARCHAR(255),
                endereco_cep VARCHAR(20) NOT NULL,
                endereco_uf VARCHAR(2) NOT NULL,
                endereco_cidade VARCHAR(100) NOT NULL
            );
            `;
            await conexao.execute(sql);
            await conexao.release();
        } catch (erro) {
            console.log("Erro ao iniciar tabela endereco: " + erro.message);
        }
    }

    async gravar(endereco) {
        if (endereco instanceof Endereco) {
            const conexao = await conectar();
            const sql = `INSERT INTO endereco (endereco_rua, endereco_numero, endereco_complemento, endereco_cep, endereco_uf, endereco_cidade) 
                VALUES (?, ?, ?, ?, ?, ?)`;
            const parametros = [
                endereco.rua,
                endereco.numero,
                endereco.complemento,
                endereco.cep,
                endereco.uf,
                endereco.cidade
            ];
            await conexao.execute(sql, parametros);
            await conexao.release();
        }
    }

    async alterar(endereco) {
        if (endereco instanceof Endereco) {
            const conexao = await conectar();
            const sql = `UPDATE endereco SET endereco_rua = ?, endereco_numero = ?, endereco_complemento = ?, endereco_cep = ?, endereco_uf = ?, endereco_cidade = ? 
                WHERE endereco_id = ?`;
            const parametros = [
                endereco.rua,
                endereco.numero,
                endereco.complemento,
                endereco.cep,
                endereco.uf,
                endereco.cidade,
                endereco.id
            ];
            await conexao.execute(sql, parametros);
            await conexao.release();
        }
    }

    async apagar(id) {
        const conexao = await conectar();
        const sql = `DELETE FROM endereco WHERE endereco_id = ?`;
        const parametros = [id];
        await conexao.execute(sql, parametros);
        await conexao.release();
    }

    async get(filtro) {
        const conexao = await conectar();
        const unico = false;
        let parametros;
        if (filtro !== undefined && filtro !== "" && !isNaN(filtro[0])) {
            const sql = `SELECT * FROM endereco WHERE endereco_id = ?`;
            parametros = [filtro];
        } else {
            const sql = `SELECT * FROM endereco WHERE endereco_rua LIKE ? OR endereco_cidade LIKE ?`;
            parametros = [`%${filtro}%`, `%${filtro}%`];
        }
        const [linhas, campos] = await conexao.execute(sql, parametros);
        await conexao.release();
        if (linhas.length > 0) {
            let listaEnderecos = [];
            for (const linha of linhas) {
                const endereco = new Endereco(
                    linha.endereco_id,
                    linha.endereco_rua,
                    linha.endereco_numero,
                    linha.endereco_complemento,
                    linha.endereco_cep,
                    linha.endereco_uf,
                    linha.endereco_cidade
                );
                listaEnderecos.push(endereco);
            }
            if (unico) return listaEnderecos[0];
            return listaEnderecos;
        }
        return null;
    }
}
