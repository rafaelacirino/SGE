import { InscricaoResposta } from "./InscricaoResposta";

export class Inscricao{
    id: number;
    idUsuario: number;
    idEvento: number;
    idSituacaoPreInscricao: number;
    inscricaoRespostas: InscricaoResposta[] = [];

    constructor(){}
}