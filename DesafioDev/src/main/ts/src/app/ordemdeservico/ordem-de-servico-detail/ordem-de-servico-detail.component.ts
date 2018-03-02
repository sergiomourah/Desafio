import { Router } from '@angular/router';
import { OrdemDeServico } from './../../../generated/entities';
import { Component, OnInit } from '@angular/core';
import { OrdemDeServicoService } from '../../../generated/services';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ordem-de-servico-detail',
  templateUrl: './ordem-de-servico-detail.component.html',
  styleUrls: ['./ordem-de-servico-detail.component.css']
})
export class OrdemDeServicoDetailComponent implements OnInit {

  private ordemdeservico: OrdemDeServico = {};
  constructor(private service: OrdemDeServicoService,
              public router: Router, public activatedRouter: ActivatedRoute) 
              { 
                this.activatedRouter.snapshot.params["id"];
                this.service.findOrdemDeServicoById(this.activatedRouter.snapshot.params["id"]).subscribe((result) =>
                  {
                    this.ordemdeservico = result;
                  }, (error)=>
                  {
                    alert(error.message);
                  });
              }

  ngOnInit() {

  }
}
