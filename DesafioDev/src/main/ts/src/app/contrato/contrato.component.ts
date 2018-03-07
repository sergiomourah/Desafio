import { ModalMotivoComponent } from './../modal-motivo/modal-motivo.component';
import { MsgDialogComponent } from './../msg-dialog/msg-dialog.component';
import { ITdDataTableColumn } from '@covalent/core';
import { Component, OnInit } from '@angular/core';

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
    private snackBar: MatSnackBar) {

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
  /**
      * Chama as caixa de mensagens
      */
  private CallDialog(title: string, texto: string): void {
    //Sempre inicia como false;
    this.retorno = false;
    let dialogRef = this.dialog.open(MsgDialogComponent, {
      width: '250px',
      data: { name: title, msg: texto }
    });
    dialogRef.afterClosed().subscribe(result => { this.retorno = result; });
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
    /*var cancelar = this.CallDialog('Confirmação', 'Deseja cancelar a OS selecionada?');
    console.log(cancelar);    let retorno: boolean = true;
    if (cancelar === true){
      this.service.updateOrdemDeServicoToCancelar(this.ordemDeServico, "CONTRATO SUSPENSO");
      //sucesso
      this.openSnackBar("Ordem de Serviço cancelada com sucesso!", "Mensagem");
    } */
    console.log(contrato);
    this.service.updateContratoToEncerrar(contrato).subscribe((contrato) => {
      //sucesso
      this.openSnackBar("Contrato encerrado com sucesso!", "Mensagem");
    }, (error) => {
      this.openSnackBar(error.message, "erro");
    });
  }
  /**
    * Suspender Contrato 
    */
  private OnUpdateContratoToReabrir(contrato: Contrato): void {
    /*var cancelar = this.CallDialog('Confirmação', 'Deseja cancelar a OS selecionada?');
    console.log(cancelar);
    if (cancelar === true){
      this.service.updateOrdemDeServicoToCancelar(this.ordemDeServico, "CONTRATO SUSPENSO");
      //sucesso
      this.openSnackBar("Ordem de Serviço cancelada com sucesso!", "Mensagem");
    } */
    console.log(contrato);
    this.service.updateContratoToReabrir(contrato).subscribe((contrato) => {
      //sucesso    
      this.openSnackBar("Contrato reaberto com sucesso!", "Mensagem");
    }, (error) => {
      this.openSnackBar(error.message, "erro");
    });
  }
  /**
      * Cancela Ordem de Serviço
      */
  private OnUpdateContratoToSuspender(contrato: Contrato): void {
    this.CallDialog('Confirmação', 'Deseja cancelar a OS selecionada?');
    console.log(this.retorno);
    if (true) {
      this.service.updateContratoToSuspender(contrato, "CONTRATO SUSPENSO").subscribe((ordemdeservico) => {
        //sucesso
        this.openSnackBar("Contrato suspenso com sucesso!", "Mensagem");
      }, (error) => {
        this.openSnackBar(error.message, "erro");
      });

      //Abrir Dialog Motivo Cancelamento
      let dialogRef = this.dialog.open(ModalMotivoComponent, {
        width: '350px',
        data: { title: 'Cancelamento' }
      });
      //Receber retorno susbcribe da dialog
      dialogRef.afterClosed().subscribe(result => {
        //Cancelar Ordem de Serviço
        let motivo: string = result;
        if (motivo != null) {
          this.service.updateContratoToSuspender(contrato, motivo).subscribe((ordemdeservico) => {
            //sucesso
            this.openSnackBar("Ordem de Serviço cancelada com sucesso!", "Mensagem");
          }, (error) => {
            this.openSnackBar(error.message, "erro");
          });
        }
      });

    }
  }
  /**
    * Remove Ordem de Serviço
    */
  private OnRemoveContrato(id: number): void {
    /*var cancelar = this.CallDialog('Confirmação', 'Deseja cancelar a OS selecionada?');
    console.log(cancelar);
    if (cancelar === true){
      this.service.updateOrdemDeServicoToCancelar(this.ordemDeServico, "CONTRATO SUSPENSO");
      //sucesso
      this.openSnackBar("Ordem de Serviço cancelada com sucesso!", "Mensagem");
    } */
    this.service.removeContrato(id).subscribe(() => {
      //sucesso
      this.openSnackBar("Contrato excluído com sucesso!", "Mensagem");
      this.onlistContratoByFilters();
    }, (error) => {
      this.openSnackBar(error.message, "erro");
    });
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
