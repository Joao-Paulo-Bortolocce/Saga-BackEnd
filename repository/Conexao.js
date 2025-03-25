import pkg from 'pg';

const { Pool } = pkg;

// const conexao = new Pool({
//     user: process.env.BD_USUARIO,
//     host: process.env.IP_BANCO_DE_DADOS,
//     database: process.env.BASE_DE_DADOS,
//     password: process.env.BD_SENHA,
//     port: process.env.PORTA_BRANCO_DE_DADOS
// });

const conexao = new Pool({
        user: "postgres",
        host: "localhost",
        database: "saga",
        password: "postgres123",
        port: 5432
    });

export default conexao;