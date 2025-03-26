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
                pessoa_cpf VARCHAR(14) NOT NULL,
                pessoa_rg VARCHAR(13) NOT NULL,
                pessoa_nome VARCHAR(60) NOT NULL,
                pessoa_datanascimento DATE NOT NULL,
                pessoa_sexo VARCHAR(2) NOT NULL,
                pessoa_locnascimento VARCHAR(80) NOT NULL,
                pessoa_estadonascimento VARCHAR(80) NOT NULL,
                pessoa_estadocivil VARCHAR(20) NOT NULL,
                pessoa_cep VARCHAR(10) NOT NULL,
                pessoa_rua VARCHAR(60) NOT NULL,
                pessoa_numero INT NOT NULL,
                pessoa_complemento VARCHAR(100) NOT NULL,
                pessoa_cidade VARCHAR(50) NOT NULL,
                pessoa_uf VARCHAR(50) NOT NULL,
                CONSTRAINT pk_pessoa PRIMARY KEY (pessoa_cpf)
            );
            `;
            await conexao.query(sql);
            conexao.release();
        } catch (erro) {
            console.error("Erro ao iniciar tabela pessoa:", erro);
        }
    }

    async gravar(pessoa) {
        if (!(pessoa instanceof Pessoa)) return;
        try {
            const conexao = await conectar.connect();
            const sql = `INSERT INTO pessoa (
                pessoa_cpf, pessoa_rg, pessoa_nome, pessoa_datanascimento, pessoa_sexo, 
                pessoa_locnascimento, pessoa_estadonascimento, pessoa_estadocivil, 
                pessoa_cep, pessoa_rua, pessoa_numero, pessoa_complemento, pessoa_cidade, pessoa_uf
            ) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14)`;

            const parametros = [
                pessoa.cpf,
                pessoa.rg,
                pessoa.nome,
                pessoa.dataNascimento,
                pessoa.sexo,
                pessoa.locNascimento,
                pessoa.estadoNascimento,
                pessoa.estadoCivil,
                pessoa.endereco.cep,
                pessoa.endereco.rua,
                pessoa.endereco.numero,
                pessoa.endereco.complemento,
                pessoa.endereco.cidade,
                pessoa.endereco.uf
            ];
            await conexao.query(sql, parametros);
            conexao.release();
        } catch (erro) {
            console.error("Erro ao gravar pessoa:", erro);
        }
    }

    async alterar(pessoa) {
        if (!(pessoa instanceof Pessoa)) return;
        try {
            const conexao = await conectar.connect();
            const sql = `UPDATE pessoa SET 
                pessoa_rg = $1, pessoa_nome = $2, pessoa_datanascimento = $3, pessoa_sexo = $4, 
                pessoa_locnascimento = $5, pessoa_estadonascimento = $6, pessoa_estadocivil = $7, 
                pessoa_cep = $8, pessoa_rua = $9, pessoa_numero = $10, pessoa_complemento = $11, pessoa_cidade = $12, pessoa_uf = $13
                WHERE pessoa_cpf = $14`;

            const parametros = [
                pessoa.rg,
                pessoa.nome,
                pessoa.dataNascimento,
                pessoa.sexo,
                pessoa.locNascimento,
                pessoa.estadoNascimento,
                pessoa.estadoCivil,
                pessoa.endereco.cep,
                pessoa.endereco.rua,
                pessoa.endereco.numero,
                pessoa.endereco.complemento,
                pessoa.endereco.cidade,
                pessoa.endereco.uf,
                pessoa.cpf
            ];
            await conexao.query(sql, parametros);
            conexao.release();
        } catch (erro) {
            console.error("Erro ao alterar pessoa:", erro);
        }
    }

    async apagar(cpf) {
        try {
            const conexao = await conectar.connect();
            const sql = `DELETE FROM pessoa WHERE pessoa_cpf = $1`;
            await conexao.query(sql, [cpf]);
            conexao.release();
        } catch (erro) {
            console.error("Erro ao apagar pessoa:", erro);
        }
    }

    async get(filtro) {
        try {
            const conexao = await conectar.connect();
            let sql, parametros;
            let unico = false;

            if (!filtro) filtro = "";

            if (filtro !== "" && !isNaN(filtro[0])) {
                sql = `SELECT * FROM pessoa WHERE pessoa_cpf = $1`;
                unico = true;
                parametros = [filtro];
            } else {
                sql = `SELECT * FROM pessoa WHERE pessoa_nome LIKE $1`;
                parametros = [`%${filtro}%`];
            }

            const resultado = await conexao.query(sql, parametros);
            conexao.release();

            const linhas = resultado.rows || [];
            if (linhas.length > 0) {
                const listaPessoas = linhas.map(linha => new Pessoa(
                    linha.pessoa_cpf,
                    linha.pessoa_rg,
                    linha.pessoa_nome,
                    linha.pessoa_datanascimento,
                    linha.pessoa_sexo,
                    linha.pessoa_locnascimento,
                    linha.pessoa_estadonascimento,
                    linha.pessoa_estadocivil,
                    new Endereco(
                        linha.pessoa_rua,
                        linha.pessoa_numero,
                        linha.pessoa_complemento,
                        linha.pessoa_cep,
                        linha.pessoa_uf,
                        linha.pessoa_cidade
                    )
                ));
                return unico ? listaPessoas[0] : listaPessoas;
            }
            return null;
        } catch (erro) {
            console.error("Erro ao buscar pessoa:", erro);
        }
    }
}
