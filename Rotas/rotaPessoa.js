//Associar os métodos da camada de controle de produto 
//à requisições GET, POST, PUT, PATCH e DELETE HTTP

import { Router } from "express"; //micro-aplicação HTTP
import PessoaCtrl from "../controller/pessoaCtrl.js";

const pessoaCtrl = new PessoaCtrl();
const rotaPessoa = Router();

rotaPessoa.post("/", pessoaCtrl.gravar);
rotaPessoa.put("/:cpf", pessoaCtrl.editar);
rotaPessoa.patch("/:cpf", pessoaCtrl.editar);
rotaPessoa.delete("/:cpf", pessoaCtrl.excluir);
rotaPessoa.get("/:termo", pessoaCtrl.consultar);
rotaPessoa.get("/",pessoaCtrl.consultar);

export default rotaPessoa;


