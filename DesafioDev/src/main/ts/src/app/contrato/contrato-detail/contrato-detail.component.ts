import { ITdDataTableColumn } from '@covalent/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ContratoService } from './../../../generated/services';
import { PageRequest, Contrato } from './../../../generated/entities';
import { Component, OnInit } from '@angular/core';
import { HistoricoContrato } from '../../../generated/entities';

@Component({
  selector: 'app-contrato-detail',
  templateUrl: './contrato-detail.component.html',
  styleUrls: ['./contrato-detail.component.css']
})
export class ContratoDetailComponent implements OnInit {

  //Cria a lista de elementos
  ELEMENT_DATA: HistoricoContrato[] = [];

  //Pageable
  private pageable: PageRequest;
  private contrato: Contrato = {};
  dataSource: any[] = [];
  constructor(private service: ContratoService,
    public router: Router, public activatedRouter: ActivatedRoute) {
    let id = this.activatedRouter.snapshot.params["id"];
    //Buscar Ordem de Serviço
    this.service.findContratoById(id).subscribe((result) => {
      this.contrato = result;
    }, (error) => {
        alert(error.message);
      });
      //Buscar Histórico
      this.OnListHistoricoByContrato(id);
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

private OnListHistoricoByContrato(id: any): void {
  this.service.listHistoricoContratoByContratoId(id, this.pageable).subscribe((result) => {
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
