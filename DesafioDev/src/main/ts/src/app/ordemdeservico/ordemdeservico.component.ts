import { PaginationService } from './../pagination.service';
import { OrdemDeServicoService } from './../../generated/services';
import { Prioridade, PrioridadeValues, OrdemDeServico, StatusOrdemDeServico, PageRequest, StatusOrdemDeServicoValues, SolicitacaoPagamento, Pageable, Page } from './../../generated/entities';
import { Component, OnInit, ViewContainerRef } from '@angular/core';
import { MatTableDataSource, MatDialog, MatPaginator, MatSnackBar } from '@angular/material';
import { Output } from '@angular/core/src/metadata/directives';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { TdDataTableService, TdDataTableSortingOrder, ITdDataTableSortChangeEvent, ITdDataTableColumn, IPageChangeEvent } from '@covalent/core';
import { OrdemDeServicoSolicitacaoPagamentoComponent } from './ordem-de-servico-solicitacao-pagamento/ordem-de-servico-solicitacao-pagamento.component';
import { ModalMotivoComponent } from '../modal-motivo/modal-motivo.component';
import { TdDialogService } from '@covalent/core';

@Component({
  selector: 'app-ordemdeservico',
  templateUrl: './ordemdeservico.component.html',
  styleUrls: ['./ordemdeservico.component.css']
})
export class OrdemdeservicoComponent implements OnInit {

  // Objeto pageable
  private pageable: Page<OrdemDeServico>;

  //Fitros para busca
  private filtro: any = {};
  private dataSource: OrdemDeServico[];
  //Status Ordem de Serviço
  private status: any[] = [];
  //Declara Valor Enum Prioridade
  private prioridadevalues: string[] = PrioridadeValues;
  //Declara Valor Enum Status
  private statusvalues: string[] = StatusOrdemDeServicoValues;
  ordemdeservico: OrdemDeServico = {};
  //Retorno Mensagem Confirmação
  private retorno: boolean;

  private sortOrder: TdDataTableSortingOrder = TdDataTableSortingOrder.Descending;
  private sortBy: string = 'dataAbertura';

  constructor(
    private paginationService: PaginationService,
    private service: OrdemDeServicoService,
    private dialog: MatDialog,
    private snackBar: MatSnackBar,
    private _dialogService: TdDialogService,
    private _viewContainerRef: ViewContainerRef) {
    this.pageable = this.paginationService.pageRequest('dataAbertura', 'ASC', 15);
  }

  ngOnInit() {
    //Lista as Ordens de Serviço
    this.onlistOrdemDeServicosByFilters();
  }

  // colunas da tabela
  configWidthColumns: ITdDataTableColumn[] = [
    { name: 'status', label: 'Status', sortable: true, width: 120 },
    { name: 'numeroOrdemDeServico', label: 'Nº Ordem de Serviço', sortable: true, width: 80 },
    { name: 'contrato.numeroContrato', label: 'Nº Contrato', sortable: true, width: 120 },
    { name: 'dataAbertura', label: 'Data Abertura', sortable: true, width: 150 },
    { name: 'dataConclusao', label: 'Data Conclusão', sortable: true, width: 150 },
    { name: 'valorOrdemDeServico', label: 'Valor Ordem de Serviço', sortable: true, width: 90 },
    { name: 'acao', label: 'Ações', width: 350 },
  ];
/**
      * Faz a navegação pela paginas
      */
  page(pagingEvent: IPageChangeEvent): void {
    console.log(pagingEvent)
    this.pageable.pageable.page = pagingEvent.page - 1;
    this.pageable.pageable.size = pagingEvent.pageSize;
    this.onlistOrdemDeServicosByFilters();
  }
/**
      * Reordena a lista da table ao clicar na coluna
      */
  sort(event ){
    this.pageable.pageable.sort = this.paginationService.sort(event);
    this.onlistOrdemDeServicosByFilters();
  }

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
        //Concluir Ordem de Serviço
        if (confirmacao.method == 'CONCLUIR') {
          this.service.updateOrdemDeServicoToConcluir(confirmacao.data).subscribe((ordemdeservico) => {
            //sucesso
            this.openSnackBar(confirmacao.sucesso, "Mensagem");
            //Atualiza a Table
            this.onlistOrdemDeServicosByFilters();
          }, (error) => {
            this.openSnackBar(error.message, "erro");
          });
        } //Homologar Ordem de Serviço
        else if (confirmacao.method == 'HOMOLOGAR') {
          this.service.updateOrdemDeServicoToHomologar(confirmacao.data).subscribe((ordemdeservico) => {
            //sucesso
            this.openSnackBar(confirmacao.sucesso, "Mensagem");
            //Atualiza a Table
            this.onlistOrdemDeServicosByFilters();
          }, (error) => {
            this.openSnackBar(error.message, "erro");
          });
        } //Aprovar Ordem de Serviço
        else if (confirmacao.method == 'APROVAR') {
          this.service.updateOrdemDeServicoToAprovar(confirmacao.data).subscribe((ordemdeservico) => {
            //sucesso
            this.openSnackBar(confirmacao.sucesso, "Mensagem");
            //Atualiza a Table
            this.onlistOrdemDeServicosByFilters();
          }, (error) => {
            this.openSnackBar(error.message, "erro");
          })
        } //Cancelar Ordem de Serviço
        else if (confirmacao.method == 'CANCELAR') {
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
              this.service.updateOrdemDeServicoToCancelar(confirmacao.data, motivo).subscribe((ordemdeservico) => {
                //sucesso
                this.openSnackBar(confirmacao.sucesso, "Mensagem");
                //Atualiza a Table
                this.onlistOrdemDeServicosByFilters();
              }, (error) => {
                this.openSnackBar(error.message, "erro");
              });
            }
          });
        } //Remover Ordem de Serviço
        else if (confirmacao.method == 'EXCLUIR') {
          this.service.removeOrdemDeServico(confirmacao.id).subscribe(() => {
            //sucesso
            this.openSnackBar(confirmacao.sucesso, "Mensagem");
            //Atualiza a Table
            this.onlistOrdemDeServicosByFilters();
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
    if ( this.filtro.dataAberturaIni != null && this.filtro.dataAberturaIni != undefined )
    {
      this.filtro.dataAberturaIni.setHours( 0 );
      this.filtro.dataAberturaIni.setMinutes( 0 );
      this.filtro.dataAberturaIni.setSeconds( 0 );
    }

    if (this.filtro.dataAberturaFin && this.filtro.dataAberturaFin != undefined )
    {
      this.filtro.dataAberturaFin.setHours( 23 );
      this.filtro.dataAberturaFin.setMinutes( 59 );
      this.filtro.dataAberturaFin.setSeconds( 59 );
    }
    if ( this.filtro.dataConclusaoIni != null && this.filtro.dataConclusaoIni != undefined )
    {
      this.filtro.dataConclusaoIni.setHours( 0 );
      this.filtro.dataConclusaoIni.setMinutes( 0 );
      this.filtro.dataConclusaoIni.setSeconds( 0 );
    }

    if (this.filtro.dataConclusaoFin && this.filtro.dataConclusaoFin != undefined )
    {
      this.filtro.dataConclusaoFin.setHours( 23 );
      this.filtro.dataConclusaoFin.setMinutes( 59 );
      this.filtro.dataConclusaoFin.setSeconds( 59 );
    }
    this.service.listOrdemDeServicosByFilters(this.filtro.numeroContrato,
      this.filtro.numeroOs,
      this.filtro.nomeCliente != null ?
        "%" + this.filtro.nomeCliente + "%" :
        this.filtro.nomeCliente,
      this.filtro.statusOrdem,
      this.filtro.valorIni,
      this.filtro.valorFin,
      this.filtro.dataAberturaIni,
      this.filtro.dataAberturaFin,
      this.filtro.dataConclusaoIni,
      this.filtro.dataConclusaoFin,
      this.filtro.prioridade,
      this.pageable.pageable).subscribe((result) => {
        "Ordem de Serviço excluída com sucesso!"
        this.dataSource = result.content;
        this.pageable.totalElements = result.totalElements;
        console.log(result.totalElements);
      }, (error) => {
        console.log(error.message);
      });
  }
  /**
    * Homologa Ordem de Serviço
    */
  private OnUpdateOrdemDeServicoToConcluir(ordemDeServico: OrdemDeServico): void {
    let confirmacao: any = {
      msg: 'Deseja concluir a Ordem de Serviço selecionada?',
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
    let confirmacao: any = {
      msg: 'Deseja homologar a Ordem de Serviço selecionada?',
      method: 'HOMOLOGAR',
      sucesso: 'Ordem de Serviço homologada com sucesso!',
      data: ordemDeServico
    };
    this.openConfirm(confirmacao);
  }
  /**
    * Aprova Ordem de Serviço
    */
  private OnUpdateOrdemDeServicoToAprovar(ordemDeServico: OrdemDeServico): void {
    let confirmacao: any = {
      msg: 'Deseja aprovar a Ordem de Serviço selecionada?',
      method: 'APROVAR',
      sucesso: 'Ordem de Serviço aprovada com sucesso!',
      data: ordemDeServico
    };
    this.openConfirm(confirmacao);
  }
  /**
      * Cancela Ordem de Serviço
      */
  private OnUpdateOrdemDeServicoToCancelar(ordemDeServico: OrdemDeServico): void {
    let confirmacao: any = {
      msg: 'Deseja cancelar a Ordem de Serviço selecionada?',
      method: 'CANCELAR',
      sucesso: 'Ordem de Serviço cancelada com sucesso!',
      data: ordemDeServico
    };
    this.openConfirm(confirmacao);
  }
  /**
    * Remove Ordem de Serviço
    */
  private OnRemoveOrdemDeServico(id: number): void {
    let confirmacao: any = {
      msg: 'Deseja excluir a Ordem de Serviço selecionada?',
      method: 'EXCLUIR',
      sucesso: 'Ordem de Serviço excluída com sucesso!',
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
    this.onlistOrdemDeServicosByFilters();
  }
  /**
    * Exibe a mensagem de sucesso/erro na snackbar
    */
  private openSnackBar(message: string, action: string): void {
    this.snackBar.open(message, action, {
      duration: 4000,
      direction: "ltr"
    });
  }
}
