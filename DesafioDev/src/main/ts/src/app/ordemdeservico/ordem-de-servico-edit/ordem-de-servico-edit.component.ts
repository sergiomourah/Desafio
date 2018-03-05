import { ActivatedRoute } from '@angular/router';
import { OrdemDeServico } from './../../../generated/entities';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrdemDeServicoService } from '../../../generated/services';

@Component({
  selector: 'app-ordem-de-servico-edit',
  templateUrl: './ordem-de-servico-edit.component.html',
  styleUrls: ['./ordem-de-servico-edit.component.css']
})
export class OrdemDeServicoEditComponent implements OnInit {

  private ordemdeservico: OrdemDeServico = {};
  constructor(private service: OrdemDeServicoService,
    public router: Router, public activatedRouter: ActivatedRoute) {
    let id = this.activatedRouter.snapshot.params["id"];
    //Buscar Ordem de Serviço
    this.service.findOrdemDeServicoById(id).subscribe((result) => {
      this.ordemdeservico = result;
    }, (error) => {
        alert(error.message);
      });
  }

  ngOnInit() {
  }

}
