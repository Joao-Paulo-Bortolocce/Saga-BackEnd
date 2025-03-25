import conectar from "./Conexao.js";
import Pessoa from "../model/pessoa.js";

export default class PessoaDAO {
    constructor() {
        this.init();
    }

    async init() {
        try {
            const conexao = await conectar();
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
            await conexao.execute(sql);
            await conexao.release();
        } catch (erro) {
            console.log("Erro ao iniciar tabela pessoa: " + erro.message);
        }
    }

    async gravar(pessoa) {
        if (pessoa instanceof Pessoa) {
            const conexao = await conectar();
            const sql = `INSERT INTO pessoa (pessoa_cpf, pessoa_rg, pessoa_nome, pessoa_datanascimento, pessoa_sexo, pessoa_locnascimento, pessoa_estadonascimento, pessoa_enderecoid, pessoa_estadocivil) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)`;
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
            await conexao.execute(sql, parametros);
            await conexao.release();
        }
    }

    async alterar(pessoa) {
        if (pessoa instanceof Pessoa) {
            const conexao = await conectar();
            const sql = `UPDATE pessoa SET pessoa_rg = ?, pessoa_nome = ?, pessoa_datanascimento = ?, pessoa_sexo = ?, pessoa_locnascimento = ?, pessoa_estadonascimento = ?, pessoa_enderecoid = ?, pessoa_estadocivil = ? 
                WHERE pessoa_cpf = ?`;
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
            await conexao.execute(sql, parametros);
            await conexao.release();
        }
    }

    async apagar(cpf) {
        const conexao = await conectar();
        const sql = `DELETE FROM pessoa WHERE pessoa_cpf = ?`;
        let parametros=[cpf]
        await conexao.execute(sql, parametros);
        await conexao.release();
    }
    
    async get(filtro) {
        const conexao = await conectar();
        let unico= false;
        let parametros;
        let sql;
        if(filtro===undefined)
            filtro="";
        if(filtro!="" && !isNaN(filtro[0])){
             sql= `SELECT * FROM pessoa WHERE pessoa_cpf = ?`;
            unico=true;
            parametros = [filtro];
        }
        else{
                
             sql = `SELECT * FROM pessoa WHERE pessoa_nome LIKE ?`;
            parametros=[`%${filtro}%`]
        }
        const [linhas, campos] = await conexao.execute(sql, parametros);
        await conexao.release();
        if(linhas.length>0){
            let listaPessoas=[];
            for(const linha of linhas){
                const pessoa= new Pessoa(
                    linha.pessoa_cpf,
                    linha.pessoa_rg,
                    linha.pessoa_nome,
                    linha.pessoa_datanascimento,
                    linha.pessoa_sexo,
                    linha.pessoa_locnascimento,
                    linha.pessoa_estadonascimento,
                    linha.pessoa_enderecoid,
                    linha.pessoa_estadocivil
                );
                listaPessoas.push(pessoa);
            }
            if(unico)
                return listaPessoas[0];
            return listaPessoas;
        }
        return null;

    }
}