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
  cliente : Cliente = { id: 1, nome: "Sérgio Moura"}

  ngOnInit() {
    
  }
}
