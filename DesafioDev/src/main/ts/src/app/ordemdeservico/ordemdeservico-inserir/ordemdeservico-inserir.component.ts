import { Component, OnInit } from '@angular/core';
import { OrdemDeServicoService } from '../../../generated/services';
import { OrdemDeServico } from '../../../generated/entities';

@Component({
  selector: 'app-ordemdeservico-inserir',
  templateUrl: './ordemdeservico-inserir.component.html',
  styleUrls: ['./ordemdeservico-inserir.component.css']
})
export class OrdemdeservicoInserirComponent implements OnInit {

  constructor(private service : OrdemDeServicoService,
              private ordemdeservico : OrdemDeServico) { }

  ngOnInit() {
  }

  InsertOrdemDeServico()  {
    this.service.insertOrdemDeServico(this.ordemdeservico).subscribe((ordemdeservico) => {
      //sucesso
      alert("Ordem de Serviço salva com sucesso!");
    }, (error) => {
      //erro
    });
  }

}
