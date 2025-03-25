import { Router } from "express"; //micro-aplicação HTTP
import SalasCtrl from "../controller/salasCtrl.js";

const salasCtrl = new SalasCtrl();
const rotaSala = Router();

rotaSala.post("/", salasCtrl.gravar);
rotaSala.put("/", salasCtrl.editar);
rotaSala.patch("/:id", salasCtrl.editar);
rotaSala.delete("/:id", salasCtrl.excluir);
rotaSala.get("/:id", salasCtrl.consultar);
rotaSala.get("/",salasCtrl.consultar);

export default rotaPessoa;


