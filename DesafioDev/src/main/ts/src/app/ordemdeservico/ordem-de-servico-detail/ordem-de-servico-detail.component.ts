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
  }

  ngOnInit() {

  }

    // colunas da tabela
    configWidthColumns: ITdDataTableColumn[] = [
      { name: 'data',  label: 'Data', width: 200, format: (value) =>{ { return this.formatarData(value)}}},
      { name: 'status',  label: 'Ação', width: 180 },
      { name: 'user.name',  label: 'Usuário', width: 150 },
      { name: 'observacao',  label: 'Observação', width: 150 },
  ];

  private OnListHistoricoByOrdemDeServico(id: any): void {
    this.service.listHistoricoOrdemDeServicoByOrdemDeServicoId(id, this.pageable).subscribe((result) => {
      this.dataSource = result.content;
      console.log(this.dataSource);
    }), (error) => {
        alert(error.message);
    };
  }

  // metodo para formatar a data
  public formatarData(data): String
  {
   var d = new Date(data),
       mes = '' + (d.getMonth() + 1),
       dia = '' + d.getDate(),
       ano = d.getFullYear();
   if (mes.length < 2) mes = '0' + mes;
   if (dia.length < 2) dia = '0' + dia;
   return [dia, mes, ano].join('/'); // "join" é o caracter para separar a formatação da data, neste caso, a barra (/)
}
}
