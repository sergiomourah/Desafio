import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';

@Component({
  selector: 'app-modal-motivo',
  templateUrl: './modal-motivo.component.html',
  styleUrls: ['./modal-motivo.component.css']
})
export class ModalMotivoComponent {

  constructor(public dialogRef: MatDialogRef<ModalMotivoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

  }

  ngOnInit() {
  }

  /** 
* Emite a resposta da confirmação
*/
  public emitter(motivo: string) {
    this.dialogRef.close(motivo);
  }
}
