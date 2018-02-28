import { MsgDialogComponent } from './../msg-dialog/msg-dialog.component';
import { Prioridade, PrioridadeValues, OrdemDeServico } from './../../generated/entities';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatDialog } from '@angular/material';

@Component({
  selector: 'app-ordemdeservico',
  templateUrl: './ordemdeservico.component.html',
  styleUrls: ['./ordemdeservico.component.css']
})
export class OrdemdeservicoComponent implements OnInit {

  //Status Ordem de Serviço
  public status: any[] = [];

  public prioridadevalues: string[] = PrioridadeValues;

  public displayedColumns = ['status', 'numeroOrdemDeServico', 'numeroContrato', 'dataAbertura', 'dataConclusao', 'valorOrdemDeServico'];

  public ordemdeservico: OrdemDeServico = {};

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
    //Inicia Status Ordem de Serviço
    this.status = [
      { value: 0, viewValue: 'ABERTA' },
      { value: 1, viewValue: 'APROVADA' },
      { value: 2, viewValue: 'CANCELADA' },
      { value: 3, viewValue: 'HOMOLOGADA' },
      { value: 4, viewValue: 'CONCLUÍDA' }
    ];
  }

  CallDialog() {
    this.dialog.open(MsgDialogComponent, {
      data: {
        animal: 'panda'
      }
    });
  }
}
