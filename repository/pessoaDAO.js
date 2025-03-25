import conectar from "./Conexao.js";
import Pessoa from "../model/pessoa.js";
import Endereco from "../model/endereco.js";

export default class PessoaDAO {
    constructor() {
        this.init();
    }

    async init() {
        try {
            const conexao = await conectar.connect();
            const sql = `
            CREATE TABLE IF NOT EXISTS pessoa (
                pessoa_cpf VARCHAR(15) NOT NULL,
                pessoa_rg VARCHAR(20) NOT NULL,
                pessoa_nome VARCHAR(100) NOT NULL,
                pessoa_datanascimento DATE NOT NULL,
                pessoa_sexo VARCHAR(10) NOT NULL,
                pessoa_locnascimento VARCHAR(100) NOT NULL,
                pessoa_estadonascimento VARCHAR(50) NOT NULL,
                pessoa_enderecoid INT NOT NULL,
                pessoa_estadocivil VARCHAR(20) NOT NULL,
                CONSTRAINT pk_pessoa PRIMARY KEY (pessoa_cpf),
                CONSTRAINT fk_enderecoPessoa FOREIGN KEY (pessoa_enderecoid) references endereco (endereco_id)
            );
            `;
            await conexao.query(sql);
            await conexao.release();
        } catch (erro) {
            console.log("Erro ao iniciar tabela pessoa: " + erro.message);
        }
    }

    async gravar(pessoa) {
        try {

            if (pessoa instanceof Pessoa) {
                const conexao = await conectar.connect();
                const sql = `INSERT INTO pessoa (pessoa_cpf, pessoa_rg, pessoa_nome, pessoa_datanascimento, pessoa_sexo, pessoa_locnascimento, pessoa_estadonascimento, pessoa_enderecoid, pessoa_estadocivil) 
                VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9)`;
                let enderecoId = pessoa.enderecoId;


                const parametros = [
                    pessoa.cpf,
                    pessoa.rg,
                    pessoa.nome,
                    pessoa.dataNascimento,
                    pessoa.sexo,
                    pessoa.locNascimento,
                    pessoa.estadoNascimento,
                    enderecoId,
                    pessoa.estadoCivil
                ];
                await conexao.query(sql, parametros);
                await conexao.release();
            }
        }
        catch (erro) {
            console.log("erro ao iniciar" + erro.message)
        }
    }

    async alterar(pessoa) {
        try {

            if (pessoa instanceof Pessoa) {
                const conexao = await conectar.connect();
                const sql = `UPDATE pessoa SET pessoa_rg = $1, pessoa_nome = $2, pessoa_datanascimento = $3, pessoa_sexo = $4, pessoa_locnascimento = $5, pessoa_estadonascimento = $6, pessoa_enderecoid = $7, pessoa_estadocivil = $8 
            WHERE pessoa_cpf = $9`;
                const parametros = [
                    pessoa.rg,
                    pessoa.nome,
                    pessoa.dataNascimento,
                    pessoa.sexo,
                    pessoa.locNascimento,
                    pessoa.estadoNascimento,
                    pessoa.enderecoId,
                    pessoa.estadoCivil,
                    pessoa.cpf
                ];
                await conexao.query(sql, parametros);
                await conexao.release();
            }
        }
        catch (erro) {
            console.log("erro ao iniciar" + erro.message)
        }
    }

    async apagar(cpf) {
        try {
            const conexao = await conectar.connect();
            const sql = `DELETE FROM pessoa WHERE pessoa_cpf = $1`;
            let parametros = [cpf]
            await conexao.query(sql, parametros);
            await conexao.release();
        }
        catch (erro) {
            console.log("erro ao iniciar" + erro.message)
        }
    }

    async get(filtro) {
        try {
            const conexao = await conectar.connect();
            let unico = false;
            let parametros;
            let sql;
            if (filtro === undefined)
                filtro = "";
            if (filtro != "" && !isNaN(filtro[0])) {
                sql = `SELECT * FROM pessoa WHERE pessoa_cpf = $1`;
                unico = true;
                parametros = [filtro];
            }
            else {

                sql = `SELECT * FROM pessoa WHERE pessoa_nome LIKE $1`;
                parametros = [`%${filtro}%`]
            }
            const resultado = await conexao.query(sql, parametros);
            const linhas = resultado.rows || []; // Garante que seja um array
            if (linhas.length > 0) {
                let listaPessoas = [];
                for (const linha of linhas) {
                    let endereco = new Endereco(linha.pessoa_enderecoid)
                    const pessoa = new Pessoa(
                        linha.pessoa_cpf,
                        linha.pessoa_rg,
                        linha.pessoa_nome,
                        linha.pessoa_datanascimento,
                        linha.pessoa_sexo,
                        linha.pessoa_locnascimento,
                        linha.pessoa_estadonascimento,
                        endereco,
                        linha.pessoa_estadocivil
                    );
                    listaPessoas.push(pessoa);
                }
                if (unico)
                    return listaPessoas[0];
                return listaPessoas;
            }
            return null;
        }
        catch (erro) {
            console.log("erro ao iniciar" + erro.message)
        }
    }
}