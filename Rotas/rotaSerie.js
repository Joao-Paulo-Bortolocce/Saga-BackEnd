//Associar os métodos da camada de controle de produto 
//à requisições GET, POST, PUT, PATCH e DELETE HTTP

import { Router } from "express"; //micro-aplicação HTTP
import SerieCtrl from "../controller/serieCtrl.js";

const serieCtrl = new SerieCtrl();
const rotaSerie = Router();

rotaSerie.post("/", serieCtrl.gravar);
//rotaSerie.put("/", serieCtrl.editar);
//rotaSerie.patch("/:id", serieCtrl.editar);
rotaSerie.delete("/:id", serieCtrl.excluir);
rotaSerie.get("/:termo", serieCtrl.consultar);
rotaSerie.get("/",serieCtrl.consultar);

export default rotaSerie;


