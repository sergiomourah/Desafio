import { OrdemDeServicoService } from './../../../generated/services';
import { Router } from '@angular/router';
import { OrdemDeServico, HistoricoOrdemDeServico, PageRequest } from './../../../generated/entities';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MatTableDataSource, MatPaginator } from '@angular/material';
import { TdDataTableService, TdDataTableSortingOrder, ITdDataTableSortChangeEvent, ITdDataTableColumn } from '@covalent/core';

@Component({
  selector: 'app-ordem-de-servico-detail',
  templateUrl: './ordem-de-servico-detail.component.html',
  styleUrls: ['./ordem-de-servico-detail.component.css']
})
export class OrdemDeServicoDetailComponent implements OnInit {

  //Cria a lista de elementos
  ELEMENT_DATA: HistoricoOrdemDeServico[] = [];

  //Pageable
  private pageable: PageRequest;
  private ordemdeservico: OrdemDeServico = {};
  dataSource: any[] = [];
  solicitacaoPagamentoDataSource: any[] = [];
  constructor(private service: OrdemDeServicoService,
    public router: Router, public activatedRouter: ActivatedRoute) {
    let id = this.activatedRouter.snapshot.params["id"];
    //Buscar Ordem de Serviço
    this.service.findOrdemDeServicoById(id).subscribe((result) => {
      this.ordemdeservico = result;
    }, (error) => {
        alert(error.message);
      });
      //Buscar Histórico
      this.OnListHistoricoByOrdemDeServico(id);
      //Buscar Solicitação de Pagamento
      this.OnListSolicitacaoPagamentoByOrdemDeServicoId(id);  
  }

  ngOnInit() {

  }

    // colunas da tabela
    configWidthColumns: ITdDataTableColumn[] = [
      { name: 'data',  label: 'Data', width: 200 },
      { name: 'status',  label: 'Ação', width: 180 },
      { name: 'user.name',  label: 'Usuário', width: 150 },
      { name: 'observacao',  label: 'Observação', width: 150 },
  ];

  // colunas da tabela
  configWidthColumns1: ITdDataTableColumn[] = [
    { name: 'dataVencimento',  label: 'Data Vencimento', width: 200 },
    { name: 'valorPagamento',  label: 'Valor Pagamento', width: 180 },
];

  private OnListHistoricoByOrdemDeServico(id: any): void {
    this.service.listHistoricoOrdemDeServicoByOrdemDeServicoId(id, this.pageable).subscribe((result) => {
      this.dataSource = result.content;
    }), (error) => {
        alert(error.message);
    };
  }

  private OnListSolicitacaoPagamentoByOrdemDeServicoId(id: any): void {
    this.service.listSolicitacaoPagamentoByOrdemDeServicoId(id, this.pageable).subscribe((result) => {
      this.solicitacaoPagamentoDataSource = result.content;
    }), (error) => {
        alert(error.message);
    };
  }
}
