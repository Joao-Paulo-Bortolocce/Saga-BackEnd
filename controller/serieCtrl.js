import Serie from "../model/serie.js";

export default class SerieCtrl {

    gravar(requisicao, resposta) {
        resposta.type("application/json");

        if (requisicao.method === 'POST' && requisicao.is("application/json")) {

            const serieNum = requisicao.body.serieNum;
            const descricao = requisicao.body.descricao;

            if (serieNum && descricao) {

                const serie = new Serie(serieNum, descricao);

                serie.gravar()
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Série cadastrada com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao cadastrar a série: " + erro.message
                        });
                    });

            } else {
                resposta.status(400).json({
                    "status": false,
                    "mensagem": "Preencha o número e a descrição da série."
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
            const serie = new Serie();

            serie.consultar(termo)
                .then((listaSeries) => {
                    resposta.status(200).json(listaSeries ?? []);
                })
                .catch((erro) => {
                    resposta.status(500).json({
                        "status": false,
                        "mensagem": "Erro ao consultar as séries: " + erro.message
                    });
                });

        } else {
            resposta.status(400).json({
                "status": false,
                "mensagem": "Requisição inválida! Consulte a documentação da API."
            });
        }
    }

    alterar(requisicao, resposta) {
        resposta.type("application/json");

        if ((requisicao.method === 'PUT' || requisicao.method === 'PATCH') && requisicao.is("application/json")) {

            const id = requisicao.params.id;
            const serieNum = requisicao.body.serieNum;
            const descricao = requisicao.body.descricao;

            if (id && serieNum && descricao) {

                const serie = new Serie(serieNum, descricao, id);

                serie.alterar()
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Série atualizada com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao atualizar a série: " + erro.message
                        });
                    });

            } else {
                resposta.status(400).json({
                    "status": false,
                    "mensagem": "Preencha o ID, número e a descrição da série."
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

            if (id) {

                const serie = new Serie();

                serie.apagar(id)
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Série excluída com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao excluir a série: " + erro.message
                        });
                    });

            } else {
                resposta.status(400).json({
                    "status": false,
                    "mensagem": "Informe um ID válido para excluir."
                });
            }

        } else {
            resposta.status(400).json({
                "status": false,
                "mensagem": "Requisição inválida! Consulte a documentação da API."
            });
        }
    }
}
