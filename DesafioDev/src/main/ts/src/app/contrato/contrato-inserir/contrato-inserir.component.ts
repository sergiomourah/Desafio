import { TdDialogService } from '@covalent/core';
import { ModalClienteComponent } from './../../modal-cliente/modal-cliente.component';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog, MatSnackBar } from '@angular/material';
import { ContratoService } from './../../../generated/services';
import { Contrato, StatusContrato, StatusContratoValues } from './../../../generated/entities';
import { Component, OnInit, ViewContainerRef } from '@angular/core';

@Component({
  selector: 'app-contrato-inserir',
  templateUrl: './contrato-inserir.component.html',
  styleUrls: ['./contrato-inserir.component.css']
})
export class ContratoInserirComponent implements OnInit {

  contrato: Contrato = {};
  constructor(private service: ContratoService,
    private dialog: MatDialog,
    private router: Router,
    private snackBar: MatSnackBar,
    public activatedRouter: ActivatedRoute,
    private _dialogService: TdDialogService,
    private _viewContainerRef: ViewContainerRef) {
    let id = this.activatedRouter.snapshot.params["id"];
    //Buscar Ordem de Serviço
    this.service.findContratoById(id).subscribe((result) => {
      this.contrato = result;
    }, (error) => {
      console.log("Inserindo dados");
    });
  }

  ngOnInit() {
    this.contrato.cliente = {};
  }
/**
    * Inseri o contrato
    */
  private onInsertContrato(): void {
    if (this.contrato.numeroContrato == null){
      this.openSnackBar("Obrigatório informar Nº Contrato!", "Mensagem");
      return;
    }
    if (this.contrato.cliente.id == null){
      this.openSnackBar("Obrigatório informar Cliente!", "Mensagem");
      return;
    }
    if (this.contrato.dataContrato == null){
      this.openSnackBar("Obrigatório informar Data Contrato!", "Mensagem");
      return;
    }
    if (this.contrato.id == null) {
      this.service.insertContrato(this.contrato).subscribe((contrato) => {
        //sucesso
        this.openSnackBar("Contrato salvo com sucesso!", "Mensagem");
        this.router.navigate(['/contrato']);
      }, (error) => {
        this.openSnackBar(error.message, "erro");
      });
    } else {
      this.service.updateContrato(this.contrato).subscribe((contrato) => {
        //sucesso
        this.openSnackBar("Contrato alterado com sucesso!", "Mensagem");
        this.router.navigate(['/contrato']);
      }, (error) => {
        this.openSnackBar(error.message, "erro");
      });
    }
  }
/**
    * Abre dialog para chamar o cliente
    */
  private openDialog(): void {
    let dialogRef = this.dialog.open(ModalClienteComponent, {
      height: '600px',
      width: '800px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result != null)
        this.contrato.cliente = result.selected[0];
    });
  }
/**
    * Método para cancelar operação
    */
  private onCancel() : void {
    if (this.contrato.numeroContrato != null ||
        this.contrato.descricao != null ||
        this.contrato.dataContrato != null ||
        this.contrato.dataPrevisaoEncerramento != null ||
        this.contrato.cliente.id != null){
        this.openConfirm();
        }
        else{
         this.router.navigate(['/contrato']);
        }
   }
/**
    * Mensagem sucesso/erro
    */
  private openSnackBar(message: string, action: string): void {
    this.snackBar.open(message, action, {
      duration: 4000,
      direction: "ltr"
    });
  }
/**
    * Mensagem de confirmação doc cancelamento da operação
    */
  private openConfirm(): void {
    this._dialogService.openConfirm({
      message: 'Existem dados não salvos!\n ' + 
               'Algumas informações poderão ser perdidas.\n '  + 
               'Confirma o cancelamento do Contrato?',
      disableClose: false, 
      viewContainerRef: this._viewContainerRef, 
      title: 'Confirmação', 
      cancelButton: 'Não', 
      acceptButton: 'Sim', 
      width: '500px', 
    }).afterClosed().subscribe((accept: boolean) => {
      if (accept) {
        this.router.navigate(['/contrato']);
      }        
    });
  }
}
