import mysql from 'mysql2/promise';

//Lembre-se, nossa aplicação neste momento estará se comunicando com outra aplicação.
//Desse modo, nossa aplicação não tem controle sobre a outra.
//O que exige uma comunicação assíncrona.
export default async function conectar(){
    
    if (global.poolConexoes){
        //retorna do pool uma conexão
        return await poolConexoes.getConnection();
    }
    else{
        global.poolConexoes = await mysql.createPool({
            "host":process.env.IP_BANCO_DE_DADOS,
            "port":process.env.PORTA_BRANCO_DE_DADOS,
            "database":process.env.BASE_DE_DADOS,
            "user":process.env.BD_USUARIO,
            "password":process.env.BD_SENHA,
            "connectTimeout":60000,
            "waitForConnections":true,
            "connectionLimit":20,
            "queueLimit":20
        });
        return await global.poolConexoes.getConnection();
    }
}