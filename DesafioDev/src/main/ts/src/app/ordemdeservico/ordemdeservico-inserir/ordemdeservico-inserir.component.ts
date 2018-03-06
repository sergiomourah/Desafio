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
  ordemdeservico: OrdemDeServico = {};
  constructor(private service: OrdemDeServicoService,
    private dialog: MatDialog,
    private router: Router,
    private snackBar: MatSnackBar,
    public activatedRouter: ActivatedRoute) {
    let id = this.activatedRouter.snapshot.params["id"];
    //Buscar Ordem de Serviço
    this.service.findOrdemDeServicoById(id).subscribe((result) => {
      this.ordemdeservico = result;
    }, (error) => {
      console.log("Inserindo dados");
    });
  }

  ngOnInit() {
    this.ordemdeservico.gestor = {};
    this.ordemdeservico.contrato = {};
    this.ordemdeservico.contrato.id = 10001;
    this.ordemdeservico.contrato.numeroContrato = "59230";
    this.ordemdeservico.contrato.descricao = "CONTRATO PARA TESTE";
  }

  private onInsertOrdemDeServico() : void {
    this.service.insertOrdemDeServico(this.ordemdeservico).subscribe((ordemdeservico) => {
      //sucesso
      this.openSnackBar("Ordem de Serviço salva com sucesso!", "Mensagem");
      this.router.navigate(['/ordemdeservico']);
    }, (error) => {
      this.openSnackBar(error.message, "erro");
    });
  }

  private openDialog() : void {
    let dialogRef = this.dialog.open(ModalGestorComponent, {
      height: '600px',
      width: '800px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ordemdeservico.gestor = result.selected[0];
    });
  }

  private openSnackBar(message: string, action: string) : void {
    this.snackBar.open(message, action, {
      duration: 4000,
      direction: "ltr"
    });
  }
}
