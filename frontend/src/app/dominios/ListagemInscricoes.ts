import { InscricaoResposta } from "./InscricaoResposta"
import { Perguntas } from "./Perguntas"

export class ListagemInscricoes{
    id: number
    nomeUsuario: string
    emailUsuario: string
    situacaoDescricao:string
    idSituação:number
    perguntas: Perguntas[] = []
    inscricoesResposta: InscricaoResposta[] = []

    constructor(){}
}