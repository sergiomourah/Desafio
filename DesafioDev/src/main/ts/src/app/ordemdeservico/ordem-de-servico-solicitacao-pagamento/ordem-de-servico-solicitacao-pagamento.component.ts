import { SelectionModel } from '@angular/cdk/collections';
import { MatDialogRef } from '@angular/material';
import { Component, OnInit } from '@angular/core';
import { SolicitacaoPagamento } from '../../../generated/entities';

@Component({
  selector: 'app-ordem-de-servico-solicitacao-pagamento',
  templateUrl: './ordem-de-servico-solicitacao-pagamento.component.html',
  styleUrls: ['./ordem-de-servico-solicitacao-pagamento.component.css']
})
export class OrdemDeServicoSolicitacaoPagamentoComponent implements OnInit {

  solicitacaopagamento: SolicitacaoPagamento = {};
  constructor(public dialogRef: MatDialogRef<OrdemDeServicoSolicitacaoPagamentoComponent>) { }

  selection = new SelectionModel<SolicitacaoPagamento>(false, []);
  ngOnInit() {
  }
   /** 
   * Emite uma solicitação de pagamento 
  */
  public emitter() {
    this.selection.select(this.solicitacaopagamento);
    this.dialogRef.close(this.selection);
  }
}
