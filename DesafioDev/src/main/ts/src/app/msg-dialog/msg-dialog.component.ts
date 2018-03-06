import { Component, OnInit, Inject } from '@angular/core';

import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-msg-dialog',
  templateUrl: './msg-dialog.component.html',
  styleUrls: ['./msg-dialog.component.css']
})
export class MsgDialogComponent {

  constructor( public dialogRef: MatDialogRef<MsgDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) 
    { 

    }

      /** 
   * Emite a resposta da confirmação
  */
  public emitter(retorno: boolean) {
    this.dialogRef.close(retorno);
  }
}
