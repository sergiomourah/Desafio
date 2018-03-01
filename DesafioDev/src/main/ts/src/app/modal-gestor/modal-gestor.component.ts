import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatPaginator, MatTableDataSource } from '@angular/material';

import { Gestor, PageRequest } from './../../generated/entities';
import { SelectionModel } from '@angular/cdk/collections';
import { OrdemDeServicoService } from '../../generated/services';

@Component({
  selector: 'app-modal-gestor',
  templateUrl: './modal-gestor.component.html',
  styleUrls: ['./modal-gestor.component.css']
})
export class ModalGestorComponent implements OnInit {

  private model: any = {
    pageRequest: {
      content: [],
      pageable: {
        page: 0,
        size: 5,
        sort: {
          orders: [{
            direction: "ASC",
            property: "codigo",
            nullHandlingHint: null
          }]
        }
      }
    },
    filter: '',
    gestorSelected: null
  };
  //Fitros para busca
  private filtro: any = {};
  //Pageable
  private pageable: PageRequest;
  //DataSource
  private dataSource: any[] = [];
  //Declara entidade gestor
  private gestor: Gestor = {};
  constructor(public dialogRef: MatDialogRef<ModalGestorComponent>,
             public service: OrdemDeServicoService) { }

  displayedColumns = ['select', 'id', 'nome'];
  selection = new SelectionModel<Gestor>(true, []);

  ngOnInit() {
    //Busca Lista de Gestores
    this.ListarGestor();
  }
  /** 
   * Emite o para o solicitante o registro selecionado após a confirmação
  */
  public emitter() {
    this.dialogRef.close(this.selection);
  }
 /**
 * Retorna se o numero do elemento selecionado corresponde ao total de linhas
 */
  public isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.length;
    return numSelected === numRows;
  }
  /** 
   * Seleciona todas as linhas se elas não estão selecionadas, senão limpa a seleção
  */
  masterToggle() {
    if (this.isAllSelected()){
      this.selection.clear(); 
      this.dataSource.forEach(row => this.selection.select(row));
    } 
        
  }
  /**
   * Lista os Gestores
   */
  public ListarGestor() {
    this.service.listGestorByNome(this.filtro.nomeCliente,
      this.pageable).subscribe((result) => {
        this.dataSource = result.content;
      }), (error) => {
        alert(error.message);
      }
  }
}
