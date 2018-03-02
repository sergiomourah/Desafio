import { SelectionModel } from '@angular/cdk/collections';
import { ModalGestorComponent } from './../../modal-gestor/modal-gestor.component';
import { OrdemDeServico, PrioridadeValues, Gestor } from './../../../generated/entities';
import { Component, OnInit } from '@angular/core';
import { OrdemDeServicoService } from '../../../generated/services';
import { MatDialog, MatSnackBar } from '@angular/material';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ordemdeservico-inserir',
  templateUrl: './ordemdeservico-inserir.component.html',
  styleUrls: ['./ordemdeservico-inserir.component.css']
})
export class OrdemdeservicoInserirComponent implements OnInit {

  public prioridadevalues: string[] = PrioridadeValues;
  ordemdeservico : OrdemDeServico = {};
  constructor(private service : OrdemDeServicoService, 
              public dialog: MatDialog,
              public router: Router,
              public snackBar: MatSnackBar) { }

  ngOnInit() {
    this.ordemdeservico.gestor = {};
    this.ordemdeservico.contrato = {};
    this.ordemdeservico.contrato.id = 10001;
    this.ordemdeservico.contrato.numeroContrato = "59230";
    this.ordemdeservico.contrato.descricao = "CONTRATO PARA TESTE";
  }

  onInsertOrdemDeServico()  {
    this.service.insertOrdemDeServico(this.ordemdeservico).subscribe((ordemdeservico) => {
      //sucesso
      this.openSnackBar("Ordem de Serviço salva com sucesso!", "Mensagem");
      this.router.navigate(['/ordemdeservico']);
    }, (error) => {
      this.openSnackBar(error.message, "erro");
    });
  }

  public openDialog()
  {
    let dialogRef = this.dialog.open(ModalGestorComponent, {
      height: '600px',
      width: '800px'
    });
    
    dialogRef.afterClosed().subscribe(result => {
      this.ordemdeservico.gestor = result.selected[0];
    });
  }

 public openSnackBar(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 4000,
      direction: "ltr"
    });
  }
}
