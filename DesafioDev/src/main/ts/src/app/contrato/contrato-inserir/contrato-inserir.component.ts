import { ModalClienteComponent } from './../../modal-cliente/modal-cliente.component';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog, MatSnackBar } from '@angular/material';
import { ContratoService } from './../../../generated/services';
import { Contrato, StatusContrato, StatusContratoValues } from './../../../generated/entities';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-contrato-inserir',
  templateUrl: './contrato-inserir.component.html',
  styleUrls: ['./contrato-inserir.component.css']
})
export class ContratoInserirComponent implements OnInit {

  contrato: Contrato = {};
  constructor(private service: ContratoService,
    private dialog: MatDialog,
    private router: Router,
    private snackBar: MatSnackBar,
    public activatedRouter: ActivatedRoute) {
    let id = this.activatedRouter.snapshot.params["id"];
    //Buscar Ordem de Serviço
    this.service.findContratoById(id).subscribe((result) => {
      this.contrato = result;
    }, (error) => {
      console.log("Inserindo dados");
    });
  }

  ngOnInit() {
    this.contrato.cliente = {};
  }

  private onInsertContrato() : void {
    this.service.insertContrato(this.contrato).subscribe((contrato) => {
      //sucesso
      this.openSnackBar("Contrato salvo com sucesso!", "Mensagem");
      this.router.navigate(['/contrato']);
    }, (error) => {
      this.openSnackBar(error.message, "erro");
    });
  }

  private openDialog() : void {
    let dialogRef = this.dialog.open(ModalClienteComponent, {
      height: '600px',
      width: '800px'
    });

    dialogRef.afterClosed().subscribe(result => {
      this.contrato.cliente = result.selected[0];
    });
  }

  private openSnackBar(message: string, action: string) : void {
    this.snackBar.open(message, action, {
      duration: 4000,
      direction: "ltr"
    });
  }
}
