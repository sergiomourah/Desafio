import { OrdemDeServicoService } from './../../generated/services';
import { MsgDialogComponent } from './../msg-dialog/msg-dialog.component';
import { Prioridade, PrioridadeValues, OrdemDeServico, StatusOrdemDeServico, PageRequest, StatusOrdemDeServicoValues } from './../../generated/entities';
import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatDialog, MatPaginator } from '@angular/material';
import { Output } from '@angular/core/src/metadata/directives';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';

@Component({
  selector: 'app-ordemdeservico',
  templateUrl: './ordemdeservico.component.html',
  styleUrls: ['./ordemdeservico.component.css']
})
export class OrdemdeservicoComponent implements OnInit {

  //Fitros para busca
  private filtro: any = {};
  private dataSource: any[] = [];
  private pageable: PageRequest;
  //Status Ordem de Serviço
  private status: any[] = [];

  //Declara Valor Enum Prioridade
  private prioridadevalues: string[] = PrioridadeValues;
  //Declara Valor Enum Status
  private statusvalues: string[] = StatusOrdemDeServicoValues;
  //Cria array dos campos de display da table
  private displayedColumns = ['status', 'numeroOrdemDeServico', 'numeroContrato', 'dataAbertura', 'dataConclusao', 'valorOrdemDeServico'];

  private ordemdeservico: OrdemDeServico = {};

  constructor(private service : OrdemDeServicoService, 
              private dialog: MatDialog) { }

  ngOnInit() {
    //Lista as Ordens de Serviço
    this.onlistOrdemDeServicosByFilters();
  }

  CallDialog() {
    this.dialog.open(MsgDialogComponent, {
      data: {
        animal: 'panda'
      }
    });
  }
   /**
     * Executa a consulta da ordem de serviço e retorna a lista
     */
  private onlistOrdemDeServicosByFilters() : void {
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
                                             this.pageable).subscribe((result) => {
      this.dataSource = result.content;
    }, (error) => {
     alert(error.message);
    });
  }
   /**
     * Limpa os Filtros de busca
     */
  private LimparFiltros() : void
  {
    //Limpar Filtros
     this.filtro = {};
     //Listar Ordens de serviço novamente
     this.onlistOrdemDeServicosByFilters();
  }
}
