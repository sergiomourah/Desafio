import { TdDialogService } from '@covalent/core';
import { ModalContratoComponent } from './../../modal-contrato/modal-contrato.component';
import { SelectionModel } from '@angular/cdk/collections';
import { ModalGestorComponent } from './../../modal-gestor/modal-gestor.component';
import { OrdemDeServico, PrioridadeValues, Gestor } from './../../../generated/entities';
import { Component, OnInit, ViewContainerRef } from '@angular/core';
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
    public activatedRouter: ActivatedRoute,
    private _dialogService: TdDialogService,
    private _viewContainerRef: ViewContainerRef) {
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
  }
/**
    * Inseri a ordem de serviço
    */
  private onInsertOrdemDeServico(): void {
    if (this.ordemdeservico.numeroOrdemDeServico == null){
      this.openSnackBar("Obrigatório informar Nº Ordem de Serviço!", "Mensagem");
      return;
    }
    if (this.ordemdeservico.contrato.id == null){
      this.openSnackBar("Obrigatório selecionar contrato cadastrado!", "Mensagem");
      return;
    }
    if (this.ordemdeservico.dataAbertura == null){
      this.openSnackBar("Obrigatório informar Data de Abertura!", "Mensagem");
      return;
    }
    if (this.ordemdeservico.gestor.id == null){
      this.openSnackBar("Obrigatório selecionar gestor cadastrado!", "Mensagem");
      return;
    }
    if (this.ordemdeservico.prioridade == null){
      this.openSnackBar("Obrigatório informar Prioridade!", "Mensagem");
      return;
    }
    if (this.ordemdeservico.descricaoProblema == null){
      this.openSnackBar("Obrigatório informar Descrição do Problema!", "Mensagem");
      return;
    }
    if (this.ordemdeservico.id == null) {
      this.service.insertOrdemDeServico(this.ordemdeservico).subscribe((ordemdeservico) => {
        //sucesso
        this.openSnackBar("Ordem de Serviço salva com sucesso!", "Mensagem");
        this.router.navigate(['/ordemdeservico']);
      }, (error) => {
        this.openSnackBar(error.message, "erro");
      });
    } else {
      this.service.updateOrdemDeServico(this.ordemdeservico).subscribe((ordemdeservico) => {
        //sucesso
        this.openSnackBar("Ordem de Serviço alterada com sucesso!", "Mensagem");
        this.router.navigate(['/ordemdeservico']);
      }, (error) => {
        this.openSnackBar(error.message, "erro");
      });
    }
  }
/**
    * Abre a dialog para selecionar o gestor
    */
  private openDialogGestor(): void {
    let dialogRef = this.dialog.open(ModalGestorComponent, {
      height: '600px',
      width: '800px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ordemdeservico.gestor = result.selected[0];
    });
  }
/**
    * Abre a dialog para selecionar o contrato
    */
  private openDialogContrato(): void {
    let dialogRef = this.dialog.open(ModalContratoComponent, {
      height: '600px',
      width: '800px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ordemdeservico.contrato = result.selected[0];
    });
  }
/**
    * Método para cancelar a operação
    */
  private onCancel() : void {
   if (this.ordemdeservico.contrato.id != null ||
       this.ordemdeservico.numeroOrdemDeServico != null ||
       this.ordemdeservico.dataAbertura != null ||
       this.ordemdeservico.dataPrevisaoConclusao != null ||
       this.ordemdeservico.descricaoProblema != null ||
       this.ordemdeservico.descricaoSolucao != null ||
       this.ordemdeservico.observacao != null ||
       this.ordemdeservico.valorOrdemDeServico != null ||
       this.ordemdeservico.prioridade != null ||
       this.ordemdeservico.gestor.id){
       this.openConfirm();
       }
       else{
        this.router.navigate(['/ordemdeservico']);
       }
  }
/**
    * Mesangem sucesso/erro
    */
  private openSnackBar(message: string, action: string): void {
    this.snackBar.open(message, action, {
      duration: 4000,
      direction: "ltr"
    });
  }
  /**
    * Chama mesnagem de confirmação doc ancelamento da operação
    */
  private openConfirm(): void {
    this._dialogService.openConfirm({
      message: 'Existem dados não salvos!\n ' + 
               'Algumas informações poderão ser perdidas.\n '  + 
               'Confirma o cancelamento do Ordem de Serviço?',
      disableClose: false, 
      viewContainerRef: this._viewContainerRef, 
      title: 'Confirmação', 
      cancelButton: 'Não', 
      acceptButton: 'Sim', 
      width: '500px', 
    }).afterClosed().subscribe((accept: boolean) => {
      if (accept) {
        this.router.navigate(['/ordemdeservico']);
      }   
    });
  }
}
