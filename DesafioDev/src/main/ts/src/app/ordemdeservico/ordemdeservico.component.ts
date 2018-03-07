import { OrdemDeServicoService } from './../../generated/services';
import { MsgDialogComponent } from './../msg-dialog/msg-dialog.component';
import { Prioridade, PrioridadeValues, OrdemDeServico, StatusOrdemDeServico, PageRequest, StatusOrdemDeServicoValues, SolicitacaoPagamento } from './../../generated/entities';
import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { MatTableDataSource, MatDialog, MatPaginator, MatSnackBar } from '@angular/material';
import { Output } from '@angular/core/src/metadata/directives';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { TdDataTableService, TdDataTableSortingOrder, ITdDataTableSortChangeEvent, ITdDataTableColumn } from '@covalent/core';
import { OrdemDeServicoSolicitacaoPagamentoComponent } from './ordem-de-servico-solicitacao-pagamento/ordem-de-servico-solicitacao-pagamento.component';
import { ModalMotivoComponent } from '../modal-motivo/modal-motivo.component';
import { TdDialogService } from '@covalent/core';

@Component({
  selector: 'app-ordemdeservico',
  templateUrl: './ordemdeservico.component.html',
  styleUrls: ['./ordemdeservico.component.css']
})
export class OrdemdeservicoComponent implements OnInit {

  //Fitros para busca
  private filtro: any = {};
  private dataSource: OrdemDeServico[];
  private pageable: PageRequest;
  //Status Ordem de Serviço
  private status: any[] = [];
  //Declara Valor Enum Prioridade
  private prioridadevalues: string[] = PrioridadeValues;
  //Declara Valor Enum Status
  private statusvalues: string[] = StatusOrdemDeServicoValues;
  ordemdeservico: OrdemDeServico = {};
  //Retorno Mensagem Confirmação
  private retorno: boolean;

  constructor(private service: OrdemDeServicoService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private _dialogService: TdDialogService,
    private _viewContainerRef: ViewContainerRef) { }

  ngOnInit() {
    //Lista as Ordens de Serviço
    this.onlistOrdemDeServicosByFilters();
  }

  // colunas da tabela
  configWidthColumns: ITdDataTableColumn[] = [
    { name: 'status', label: 'Status', width: 120 },
    { name: 'numeroOrdemDeServico', label: 'Nº OS', width: 80 },
    { name: 'contrato.numeroContrato', label: 'Nº Contrato', width: 120 },
    { name: 'dataAbertura', label: 'Data Abertura', width: 150 },
    { name: 'dataConclusao', label: 'Data Conclusão', width: 150 },
    { name: 'valorOrdemDeServico', label: 'Valor OS', width: 90 },
    { name: 'acao', label: 'Ações', width: 350 },
  ];
  /**
      * Chama as caixa de mensagens
      */
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
            if (confirmacao.method == 'CONCLUIR'){
              this.service.updateOrdemDeServicoToConcluir(confirmacao.data).subscribe((ordemdeservico) => {
                //sucessoclosed
                this.openSnackBar(confirmacao.sucesso, "Mensagem");
              }, (error) => {
                this.openSnackBar(error.message, "erro");
              });
            }
          }
        });
      }
  /**
    * Executa a consulta da ordem de serviço e retorna a lista
    */
  private onlistOrdemDeServicosByFilters(): void {
    this.service.listOrdemDeServicosByFilters(this.filtro.numeroContrato,
      this.filtro.numeroOs,
      this.filtro.nomeCliente != null ?
        "%" + this.filtro.nomeCliente + "%" :
        this.filtro.nomeCliente,
      this.filtro.statusOrdem,
      this.filtro.valorIni,
      this.filtro.valorFin,
      this.filtro.dataAberturaIncancelari,
      this.filtro.dataAberturaFin,
      this.filtro.dataConclusaoIni,
      this.filtro.dataConclusaoFin,
      this.filtro.prioridade,
      this.pageable).subscribe((result) => {
        this.dataSource = result.content;
      }, (error) => {
        alert(error.message);
      });
  }
  /**
    * Homologa Ordem de Serviço
    */
  private OnUpdateOrdemDeServicoToConcluir(ordemDeServico: OrdemDeServico): void {
    let confirmacao: any = {
      msg : 'Deseja concluir a OS selecionada?',
      method: 'CONCLUIR',
      sucesso: 'Ordem de Serviço concluída com sucesso!',
      data: ordemDeServico     
    };
    this.openConfirm(confirmacao);
  }
  /**
    * Solicitar Pagamento Ordem de Serviço
    */
  private OnInsertSolicitacaoPagamento(ordemDeServico: OrdemDeServico): void {

    let solicitacaopagamento: SolicitacaoPagamento = {};
    //Abrir Dialog Solicitação de Pagamento
    let dialogRef = this.dialog.open(OrdemDeServicoSolicitacaoPagamentoComponent, {
      width: '350px',
    });
    //Receber retorno susbcribe da dialog
    dialogRef.afterClosed().subscribe(result => {
      console.log(result.selected[0]);
      solicitacaopagamento = result.selected[0];
      //Inserir referencia da ordem de serviço
      solicitacaopagamento.ordemdeservico = ordemDeServico;
      //Inserir Solicitação de Pagamento na Ordem de Serviço
      this.service.insertSolicitacaoPagamento(solicitacaopagamento).subscribe((solicitacaopagamento) => {
        //sucesso
        this.openSnackBar("Solicitação de pagamento emitida com sucesso!", "Mensagem");
      }, (error) => {
        this.openSnackBar(error.message, "erro");
      });
    });
  }
  /**
    * Homologa Ordem de Serviço
    */
  private OnUpdateOrdemDeServicoToHomologar(ordemDeServico: OrdemDeServico): void {
    /*var cancelar = this.CallDialog('Confirmação', 'Deseja cancelar a OS selecionada?');
    console.log(cancelar);    let retorno: boolean = true;
    if (cancelar === true){
      this.service.updateOrdemDeServicoToCancelar(this.ordemDeServico, "CONTRATO SUSPENSO");
      //sucesso
      this.openSnackBar("Ordem de Serviço cancelada com sucesso!", "Mensagem");
    } */
    console.log(ordemDeServico);
    this.service.updateOrdemDeServicoToHomologar(ordemDeServico).subscribe((ordemdeservico) => {
      //sucessoclosed
      this.openSnackBar("Ordem de Serviço homologada com sucesso!", "Mensagem");
    }, (error) => {
      this.openSnackBar(error.message, "erro");
    });
  }
  /**
    * Aprova Ordem de Serviçovar cancelar = 
    */
  private OnUpdateOrdemDeServicoToAprovar(ordemDeServico: OrdemDeServico): void {
    /*var cancelar = this.CallDialog('Confirmação', 'Deseja cancelar a OS selecionada?');
    console.log(cancelar);
    if (cancelar === true){
      this.service.updateOrdemDeServicoToCancelar(this.ordemDeServico, "CONTRATO SUSPENSO");
      //sucesso
      this.openSnackBar("Ordem de Serviço cancelada com sucesso!", "Mensagem");
    } */
    console.log(ordemDeServico);
    this.service.updateOrdemDeServicoToAprovar(ordemDeServico).subscribe((ordemdeservico) => {
      //sucesso    
      this.openSnackBar("Ordem de Serviço aprovada com sucesso!", "Mensagem");
    }, (error) => {
      this.openSnackBar(error.message, "erro");
    });
  }
  /**
      * Cancela Ordem de Serviço
      */
  private OnUpdateOrdemDeServicoToCancelar(ordemDeServico: OrdemDeServico): void {
    //this.CallDialog('Confirmação', 'Deseja cancelar a OS selecionada?');
    if (this.openConfirm('Deseja cancelar a OS selecionada?')) {
      //Abrir Dialog Motivo Cancelamento
      let dialogRef = this.dialog.open(ModalMotivoComponent, {
        width: '350px',
        data: { title: 'Cancelamento'}
      });
      //Receber retorno susbcribe da dialog
      dialogRef.afterClosed().subscribe(result => {
        //Cancelar Ordem de Serviço
        let motivo: string = result;
        if (motivo != null) {
          this.service.updateOrdemDeServicoToCancelar(ordemDeServico, motivo).subscribe((ordemdeservico) => {
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
  private OnRemoveOrdemDeServico(id: number): void {
    /*var cancelar = this.CallDialog('Confirmação', 'Deseja cancelar a OS selecionada?');
    console.log(cancelar);
    if (cancelar === true){
      this.service.updateOrdemDeServicoToCancelar(this.ordemDeServico, "CONTRATO SUSPENSO");
      //sucesso
      this.openSnackBar("Ordem de Serviço cancelada com sucesso!", "Mensagem");
    } */
    this.service.removeOrdemDeServico(id).subscribe(() => {
      //sucesso
      this.openSnackBar("Ordem de Serviço excluída com sucesso!", "Mensagem");
      this.onlistOrdemDeServicosByFilters();
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
    this.onlistOrdemDeServicosByFilters();
  }
  private openSnackBar(message: string, action: string): void {
    this.snackBar.open(message, action, {
      duration: 4000,
      direction: "ltr"
    });
  }
}
