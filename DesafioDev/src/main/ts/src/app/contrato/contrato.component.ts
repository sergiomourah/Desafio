import { ModalMotivoComponent } from './../modal-motivo/modal-motivo.component';
import { ITdDataTableColumn, TdDialogService } from '@covalent/core';
import { Component, OnInit, ViewContainerRef } from '@angular/core';

import { Contrato, Cliente, PageRequest, StatusContratoValues } from './../../generated/entities';
import { ContratoService } from './../../generated/services';
import { MatDialog, MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-contrato',
  templateUrl: './contrato.component.html',
  styleUrls: ['./contrato.component.css']
})
export class ContratoComponent implements OnInit {

  //Fitros para busca
  private filtro: any = {};
  private dataSource: Contrato[];
  private pageable: PageRequest;
  //Declara Valor Enum Status
  private statusvalues: string[] = StatusContratoValues;
  contrato: Contrato = {};
  //Retorno Mensagem Confirmação
  private retorno: boolean;
  constructor(private service: ContratoService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private _dialogService: TdDialogService,
    private _viewContainerRef: ViewContainerRef) {

  }

  ngOnInit() {
    //Lista as Ordens de Serviço
    this.onlistContratoByFilters();
  }

  // colunas da tabela
  configWidthColumns: ITdDataTableColumn[] = [
    { name: 'status', label: 'Status', width: 120 },
    { name: 'numeroContrato', label: 'Nº Contrato', width: 120 },
    { name: 'cliente.nome', label: 'Cliente', width: 120 },
    { name: 'dataContrato', label: 'Data Contrato', width: 150 },
    { name: 'dataEncerramento', label: 'Data Encerramento', width: 150 },
    { name: 'acao', label: 'Ações', width: 350 },
  ];
  private openConfirm(confirmacao: any): void {
    this._dialogService.openConfirm({
      message: confirmacao.msg,
      disableClose: false, // defaults to false
      viewContainerRef: this._viewContainerRef, //OPTIONAL
      title: 'Confirmação', //OPTIONAL, hides if not provided
      cancelButton: 'Não', //OPTIONAL, defaults to 'CANCEL'
      acceptButton: 'Sim', //OPTIONAL, defaults to 'ACCEPT'
      width: '500px', //OPTIONAL, defaults to 400px
    }).afterClosed().subscribe((accept: boolean) => {
      if (accept) {
        //Encerrar Contrato
        if (confirmacao.method == 'ENCERRAR') {
          this.service.updateContratoToEncerrar(confirmacao.data).subscribe((contrato) => {
            //sucesso
            this.openSnackBar(confirmacao.sucesso, "Mensagem");
            //Atualiza a Table
            this.onlistContratoByFilters();
          }, (error) => {
            this.openSnackBar(error.message, "erro");
          });
        } //Reabrir Contrato
        else if (confirmacao.method == 'REABRIR') {
          this.service.updateContratoToReabrir(confirmacao.data).subscribe((contrato) => {
            //sucesso
            this.openSnackBar(confirmacao.sucesso, "Mensagem");
            //Atualiza a Table
            this.onlistContratoByFilters();
          }, (error) => {
            this.openSnackBar(error.message, "erro");
          });
        }//Suspender Contrato
        else if (confirmacao.method == 'SUSPENDER') {
          //Abrir Dialog Motivo Cancelamento
          let dialogRef = this.dialog.open(ModalMotivoComponent, {
            width: '350px',
            data: { title: 'Suspensão' }
          });
          //Receber retorno susbcribe da dialog
          dialogRef.afterClosed().subscribe(result => {
            //Cancelar Ordem de Serviço
            let motivo: string = result;
            if (motivo != null) {
              this.service.updateContratoToSuspender(confirmacao.data, motivo).subscribe((contrato) => {
                //sucesso
                this.openSnackBar(confirmacao.sucesso, "Mensagem");
                //Atualiza a Table
                this.onlistContratoByFilters();
              }, (error) => {
                this.openSnackBar(error.message, "erro");
              });
            }
          });
        } //Remover Contrato
        else if (confirmacao.method == 'EXCLUIR') {
          this.service.removeContrato(confirmacao.id).subscribe(() => {
            //sucesso
            this.openSnackBar(confirmacao.sucesso, "Mensagem");
            //Atualiza a Table
            this.onlistContratoByFilters();
          }, (error) => {
            this.openSnackBar(error.message, "erro");
          });
        }
      }
    });
  }
  /**
    * Executa a consulta da contrato e retorna a lista
    */
  private onlistContratoByFilters(): void {
    this.service.listContratoByFilters(this.filtro.numeroContrato,
      this.filtro.nomeCliente != null ?
        "%" + this.filtro.nomeCliente + "%" :
        this.filtro.nomeCliente,
      this.filtro.statusContrato,
      this.filtro.dataAberturaIni,
      this.filtro.dataAberturaFin,
      this.filtro.dataEncerramentoIni,
      this.filtro.dataEncerramentoFin,
      this.pageable).subscribe((result) => {
        this.dataSource = result.content;
      }, (error) => {
        alert(error.message);
      });
  }
  /**
    * Homologa Ordem de Serviço
    */
  private OnUpdateContratoToEncerrar(contrato: Contrato): void {
    let confirmacao: any = {
      msg: 'Deseja encerrar o contrato selecionado?',
      method: 'ENCERRAR',
      sucesso: 'Contrato encerrado com sucesso!',
      data: contrato
    };
    this.openConfirm(confirmacao);
  }
  /**
    * Suspender Contrato 
    */
  private OnUpdateContratoToReabrir(contrato: Contrato): void {
    let confirmacao: any = {
      msg: 'Deseja reabrir o contrato selecionado?',
      method: 'REABRIR',
      sucesso: 'Contrato reaberto com sucesso!',
      data: contrato
    };
    this.openConfirm(confirmacao);
  }
  /**
      * Cancela Ordem de Serviço
      */
  private OnUpdateContratoToSuspender(contrato: Contrato): void {
    let confirmacao: any = {
      msg: 'Deseja suspender o contrato selecionado?',
      method: 'SUSPENDER',
      sucesso: 'Contrato suspenso com sucesso!',
      data: contrato
    };
    this.openConfirm(confirmacao);
  }
  /**
    * Remove Ordem de Serviço
    */
  private OnRemoveContrato(id: number): void {
    let confirmacao: any = {
      msg: 'Deseja excluir o contrato selecionado?',
      method: 'EXCLUIR',
      sucesso: 'Contrato excluído com sucesso!',
      id: id
    };
    this.openConfirm(confirmacao);
  }
  /**
    * Limpa os Filtros de busca
    */
  private LimparFiltros(): void {
    //Limpar Filtros
    this.filtro = {};
    //Listar Ordens de serviço novamente
    this.onlistContratoByFilters();
  }
  closed
  private openSnackBar(message: string, action: string): void {
    this.snackBar.open(message, action, {
      duration: 4000,
      direction: "ltr"
    });
  }
}
