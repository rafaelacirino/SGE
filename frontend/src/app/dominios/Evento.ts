import { EventoPergunta } from "./EventoPergunta";

export class Evento {

    id: number;
    titulo: string;
    periodoInicio: Date;
    periodoFim: Date;
    tipoInsc: boolean;
    descricao: string;
    qtdVagas: number;
    idTipoEvento: number;
    valor: number;
    local: string;
    perguntas: EventoPergunta [] = [];
}