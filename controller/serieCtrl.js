import Serie from "../model/serie.js";

export default class SerieCtrl{

    gravar(requisicao, resposta){
        resposta.type("application/json");

        if(requisicao.method == 'POST' && requisicao.is("application/json")){
            const id = requisicao.body.id;

            if(id){
                const serie = new Serie(id);
                serie.consultar(id)
                    .then((serieExistente) => {
                        if(!serieExistente){
                            serie.gravar()
                                .then(() => {
                                    resposta.status(200).json({
                                        "status": true,
                                        "mensagem": "Serie cadastrada com sucesso!"
                                    });
                                })
                                .catch((erro) => {
                                    resposta.status(500).json({
                                        "status": false,
                                        "mensagem": "Erro ao cadastrar a serie: " + erro.message
                                    });
                                })
                        }
                        else{
                            resposta.status(400).json({
                                "status": false,
                                "mensagem": "Já existe uma serie cadastrada com esse id."
                            });
                        }
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao verificar ID: " + erro.message
                        });
                    });
            }
            else{
                resposta.status(400).json({
                    "status": false,
                    "mensagem": "Preencha todos os campos corretamente conforme documentação da API."
                });
            }
        }
        else{
            resposta.status(400).json({
                "status": false,
                "mensagem": "Requisição inválida! Consulte a documentação da API."
            });
        }
    }

    /*editar(requisicao, resposta) {
        resposta.type("application/json");

        if ((requisicao.method === 'PUT' || requisicao.method === 'PATCH') && requisicao.is("application/json")) {
            const id = requisicao.body.id;

            if (id) {
                const serie = new Serie(id);

                serie.alterar()  // Alterar os dados 
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Serie atualizada com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao atualizar a serie: " + erro.message
                        });
                    });
            } 
            else {
                resposta.status(400).json({
                    "status": false,
                    "mensagem": "Preencha todos os campos corretamente conforme documentação da API."
                });
            }
        } 
        else {
            resposta.status(400).json({
                "status": false,
                "mensagem": "Requisição inválida! Consulte a documentação da API."
            });
        }
    }*/

    excluir(requisicao, resposta) {
        resposta.type("application/json");
    
        if (requisicao.method === 'DELETE') {
            const id = requisicao.params.id;
    
            if (id) {
                const serie = new Serie(id);
    
                serie.apagar()
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Serie excluída com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao excluir a serie: " + erro.message
                        });
                    });
            } else {
                resposta.status(400).json({
                    "status": false,
                    "mensagem": "Informe um ID válido para excluir a serie."
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
                    resposta.status(200).json(listaSeries);
                })
                .catch((erro) => {
                    resposta.status(500).json({
                        "status": false,
                        "mensagem": "Erro ao consultar a serie(s): " + erro.message
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