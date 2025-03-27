import Salas from "../model/salas.js";

export default class SalasCtrl {

    gravar(requisicao, resposta) {
        resposta.type("application/json");

        if (requisicao.method === 'POST' && requisicao.is("application/json")) {
            const ncarteiras = requisicao.body.ncarteiras;

            if (ncarteiras) {
                const salas = new Salas(0, ncarteiras);
                salas.gravar()
                .then(() => {
                    resposta.status(200).json({
                        "status": true,
                        "mensagem": "Sala cadastrada com sucesso!"
                    });
                })
                .catch((erro) => {
                    resposta.status(500).json({
                        "status": false,
                        "mensagem": "Erro ao cadastrar a sala: " + erro.message
                    });
                });
            }
            else {
                resposta.status(400).json({
                    "status": false,
                    "mensagem": "Preencha todos os campos corretamente conforme documentação da API."
                });
            }
        } else {
            resposta.status(400).json({
                "status": false,
                "mensagem": "Requisição inválida! Consulte a documentação da API."
            });
        }
    }

    editar(requisicao, resposta) {
        resposta.type("application/json");

        if ((requisicao.method === 'PUT' || requisicao.method === 'PATCH') && requisicao.is("application/json")) {
            const id = requisicao.params.id;
            const ncarteiras = requisicao.body.ncarteiras;

            if (id && ncarteiras) {
                const salas = new Salas(id, ncarteiras);

                salas.alterar()
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Sala atualizada com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao atualizar a sala: " + erro.message
                        });
                    });

            } else {
                resposta.status(400).json({
                    "status": false,
                    "mensagem": "Preencha todos os campos corretamente conforme documentação da API."
                });
            }
        } else {
            resposta.status(400).json({
                "status": false,
                "mensagem": "Requisição inválida! Consulte a documentação da API."
            });
        }
    }

    excluir(requisicao, resposta) {
        resposta.type("application/json");

        if (requisicao.method === 'DELETE') {
            const id = requisicao.params.id;

            if (id>0) {
                const salas = new Salas(id);

                salas.apagar()
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Sala excluída com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao excluir a sala: " + erro.message
                        });
                    });
            } else {
                resposta.status(400).json({
                    "status": false,
                    "mensagem": "Informe um id válido para excluir a sala."
                });
            }
        } else {
            resposta.status(400).json({
                "status": false,
                "mensagem": "Requisição inválida! Consulte a documentação da API."
            });
        }
    }

    consultar(requisicao, resposta) {
        resposta.type("application/json");

        if (requisicao.method === "GET") {
            
            const termo = requisicao.params.termo;
            
            const sala = new Salas();
            
            sala.consultar(termo)
                .then((listaSalas) => {
                    resposta.status(200).json(listaSalas);
                })
                .catch((erro) => {
                    resposta.status(500).json({
                        "status": false,
                        "mensagem": "Erro ao consultar a sala(s): " + erro.message
                    });
                });

        } else {
            resposta.status(400).json({
                "status": false,
                "mensagem": "Requisição inválida! Consulte a documentação da API."
            });
        }
    }
}
