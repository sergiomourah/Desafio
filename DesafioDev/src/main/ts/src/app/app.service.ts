import { ContratoService } from './../generated/services';
import { Contrato } from './../generated/entities';
import { Injectable } from '@angular/core';

@Injectable()
export class AppService {

  constructor() 
  { 
     
  }
  InsertContrato() {
    var service :ContratoService;
    var contrato:Contrato;
    contrato.id = 1;
    contrato.descricao = "CONTRATO ABERTO";
    contrato.dataContrato = new Date();
    service.insertContrato(contrato)
  }
}
