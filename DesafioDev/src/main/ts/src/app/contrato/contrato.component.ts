import { Component, OnInit } from '@angular/core';

import { Contrato, Cliente } from './../../generated/entities';
import { ContratoService } from './../../generated/services';

@Component({
  selector: 'app-contrato',
  templateUrl: './contrato.component.html',
  styleUrls: ['./contrato.component.css']
})
export class ContratoComponent implements OnInit {

  constructor(private service : ContratoService) { }
  contrato : Contrato = {};
  data : string = "2018-02-17";
  cliente : Cliente = { id: 1, nome: "Sérgio Moura"}

  ngOnInit() {
    
  }


  InsertContrato()  {
    this.contrato.cliente = this.cliente;
    this.contrato.descricao = "CONTRATO ABERTO";
    this.contrato.dataContrato = new Date(this.data);
    console.log(this.contrato);
    this.service.insertContrato(this.contrato).subscribe((contrato) => {
      //sucesso
      alert("Contrato salvo com sucesso!");
    }, (error) => {
      //erro
    });
  }
}
