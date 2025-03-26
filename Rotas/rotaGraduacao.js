import { Router } from "express"; //micro-aplicação HTTP
import GraduacaoCtrl from "../controller/graduacaoCtrl";

const graduacaoCtrl = new GraduacaoCtrl();
const rotaGraduacao = Router();

rotaGraduacao.post("/", graduacaoCtrl.gravar);
rotaGraduacao.put("/", graduacaoCtrl.editar);
rotaGraduacao.patch("/:id", graduacaoCtrl.editar);
rotaGraduacao.delete("/:id", graduacaoCtrl.excluir);
rotaGraduacao.get("/:id", graduacaoCtrl.consultar);
rotaGraduacao.get("/",graduacaoCtrl.consultar);

export default rotaGraduacao;


