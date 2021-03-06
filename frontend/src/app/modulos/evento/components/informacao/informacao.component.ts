import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng';
import { Evento } from 'src/app/dominios/Evento';
import { Perguntas } from 'src/app/dominios/Perguntas';
import { TipoEvento } from 'src/app/dominios/TipoEvento';
import { EventoService } from '../../services/evento.service';

@Component({
  selector: 'app-informacao',
  templateUrl: './informacao.component.html',
  styleUrls: ['./informacao.component.css']
})
export class InformacaoComponent implements OnInit {

  @Input() evento = new Evento
  @Input() tipoEvento: string
  @Input() perguntas: Perguntas[] = []

  @Output() eventoDeletado = new EventEmitter<Evento>();
 
  constructor(
    private eventoServico: EventoService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService 
  ) { }

  ngOnInit(): void {
    
  }

  addSingle(error, titulo, corpo ) {
    this.messageService.add({severity:error, summary:titulo, detail:corpo});
  }

  confirm(id: number) {
    this.confirmationService.confirm({
        message: 'Deseja deletar mesmo esse evento?',
        accept: () => {
            this.deletarEvento(id)
        }
    });
  }

  deletarEvento(id: number){
    this.eventoServico.deletarEvento(id).subscribe(() => {
      this.addSingle("success", "Evento deletado", "")
      this.fecharDialog(this.evento)
      
    },
    err => alert(err));
  }

  fecharDialog(eventoDeletado: Evento) {
    this.eventoDeletado.emit(eventoDeletado);
  }
  
}
