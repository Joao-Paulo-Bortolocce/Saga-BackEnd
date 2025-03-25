import Endereco from "../model/endereco.js";
import Pessoa from "../model/pessoa.js";

export default class PessoaCtrl {

    // Criar uma nova pessoa
    gravar(requisicao, resposta) {
        resposta.type("application/json");

        if (requisicao.method === 'POST' && requisicao.is("application/json")) {
            const cpf = requisicao.body.cpf;
            const rg = requisicao.body.rg;
            const nome = requisicao.body.nome;
            const dataNascimento = requisicao.body.dataNascimento;
            const sexo = requisicao.body.sexo;
            const locNascimento = requisicao.body.locNascimento;
            const estadoNascimento = requisicao.body.estadoNascimento;
            const enderecoId = requisicao.body.enderecoId;
            const estadoCivil = requisicao.body.estadoCivil;

            if (cpf && rg && nome && dataNascimento && sexo && locNascimento && estadoNascimento && enderecoId && estadoCivil) {
                let endereco = new Endereco(enderecoId);
                const pessoa = new Pessoa(cpf, rg, nome, dataNascimento, sexo, locNascimento, estadoNascimento, endereco, estadoCivil);
                pessoa.consultar(cpf)
                    .then((pessoaExistente) => {
                        if (!pessoaExistente) {
                            pessoa.gravar()  // Gravar a nova pessoa
                                .then(() => {
                                    resposta.status(200).json({
                                        "status": true,
                                        "mensagem": "Pessoa cadastrada com sucesso!"
                                    });
                                })
                                .catch((erro) => {
                                    resposta.status(500).json({
                                        "status": false,
                                        "mensagem": "Erro ao cadastrar a pessoa: " + erro.message
                                    });
                                });
                        } else {
                            resposta.status(400).json({
                                "status": false,
                                "mensagem": "Já existe uma pessoa cadastrada com este CPF."
                            });
                        }
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao verificar CPF: " + erro.message
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
            const cpf = requisicao.body.cpf;
            const rg = requisicao.body.rg;
            const nome = requisicao.body.nome;
            const dataNascimento = requisicao.body.dataNascimento;
            const sexo = requisicao.body.sexo;
            const locNascimento = requisicao.body.locNascimento;
            const estadoNascimento = requisicao.body.estadoNascimento;
            const enderecoId = requisicao.body.enderecoId;
            const estadoCivil = requisicao.body.estadoCivil;

            if (cpf && rg && nome && dataNascimento && sexo && locNascimento && estadoNascimento && enderecoId && estadoCivil) {
                let endereco = new Endereco(enderecoId);

                const pessoa = new Pessoa(cpf, rg, nome, dataNascimento, sexo, locNascimento, estadoNascimento, endereco, estadoCivil);

                pessoa.alterar()  // Alterar os dados da pessoa
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Pessoa atualizada com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao atualizar a pessoa: " + erro.message
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
            const cpf = requisicao.params.cpf;

            if (cpf) {
                const pessoa = new Pessoa(cpf);

                pessoa.apagar()
                    .then(() => {
                        resposta.status(200).json({
                            "status": true,
                            "mensagem": "Pessoa excluída com sucesso!"
                        });
                    })
                    .catch((erro) => {
                        resposta.status(500).json({
                            "status": false,
                            "mensagem": "Erro ao excluir a pessoa: " + erro.message
                        });
                    });
            } else {
                resposta.status(400).json({
                    "status": false,
                    "mensagem": "Informe um CPF válido para excluir a pessoa."
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
            const pessoa = new Pessoa();
            pessoa.consultar(termo)
                .then((listaPessoas) => {
                    let listaAux = [];
                    for(let pessoa of listaPessoas){
                        pessoa.endereco= new Endereco().buscaEndereco(pessoa.enderecoId)
                    }
                    resposta.status(200).json(listaPessoas);
                })
                .catch((erro) => {
                    resposta.status(500).json({
                        "status": false,
                        "mensagem": "Erro ao consultar a pessoa(s): " + erro.message
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
