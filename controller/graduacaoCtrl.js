import Graduacao from "../model/graduacao.js";

export default class GraduacaoCrtl {

    gravar(requisicao, resposta) {
        resposta.type("application/json");

        if (requisicao.method === 'POST' && requisicao.is("application/json")) {
            const descricao = requisicao.body.descricao;

            if (descricao) {
                const graduacao = new Graduacao(0, descricao);
                graduacao.gravar()
                .then(() => {
                    resposta.status(200).json({
                        "status": true,
                        "mensagem": "Graduação cadastrada com sucesso!"
                    });
                })
                .catch((erro) => {
                    resposta.status(500).json({
                        "status": false,
                        "mensagem": "Erro ao cadastrar a graduação: " + erro.message
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
            const id = requisicao.body.id;
            const descricao = requisicao.body.descricao;

            if (id > 0 && descricao) {
                const graduacao = new graduacao(id, descricao);
                graduacao.alterar()
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Graduação atualizada com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao atualizar a graduação: " + erro.message
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
                const graduacao = new Graduacao(id);

                graduacao.apagar()
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Graduação excluída com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao excluir a graduação: " + erro.message
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
            const id = requisicao.params.id;
            if (isNaN(id)){
                id = "";
            }
            
            const graduacao = new Graduacao();
            
            graduacao.consultar(id)
                .then((listaGraducao) => {
                    let listaAux = [];
                    for(let graduacao of listaGraducao)
                    resposta.status(200).json(listaGraducao);
                })
                .catch((erro) => {
                    resposta.status(500).json({
                        "status": false,
                        "mensagem": "Erro ao consultar a(s) gradução(ões): " + erro.message
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
