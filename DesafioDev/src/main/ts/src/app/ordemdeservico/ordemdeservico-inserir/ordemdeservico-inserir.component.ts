import { ModalGestorComponent } from './../../modal-gestor/modal-gestor.component';
import { OrdemDeServico, PrioridadeValues } from './../../../generated/entities';
import { Component, OnInit } from '@angular/core';
import { OrdemDeServicoService } from '../../../generated/services';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-ordemdeservico-inserir',
  templateUrl: './ordemdeservico-inserir.component.html',
  styleUrls: ['./ordemdeservico-inserir.component.css']
})
export class OrdemdeservicoInserirComponent implements OnInit {

  public prioridadevalues: string[] = PrioridadeValues;
  ordemdeservico : OrdemDeServico = {};
  constructor(private service : OrdemDeServicoService, public dialog: MatDialog) { }

  ngOnInit() {
    this.ordemdeservico.gestor = {};
    this.ordemdeservico.gestor.id = 1;
    this.ordemdeservico.gestor.nome = "Sergio Moro";
    this.ordemdeservico.contrato = {};
    this.ordemdeservico.contrato.id = 10001;
    this.ordemdeservico.contrato.numeroContrato = "59230";
    this.ordemdeservico.contrato.descricao = "CONTRATO PARA TESTE";
  }

  InsertOrdemDeServico()  {
    this.service.insertOrdemDeServico(this.ordemdeservico).subscribe((ordemdeservico) => {
      //sucesso
      alert("Ordem de Serviço salva com sucesso!");
    }, (error) => {
     alert(error.message);
    });
  }

  public openDialog()
  {
    let dialogRef = this.dialog.open(ModalGestorComponent, {
      height: '600px',
      width: '800px'
    });
    
    dialogRef.afterClosed().subscribe(result => {
      console.log(result)
    });
  }
}
