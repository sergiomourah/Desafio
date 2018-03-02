import { PaginationService } from './../pagination.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog, MatPaginator, MatTableDataSource, MatSort } from '@angular/material';

import { Gestor, PageRequest } from './../../generated/entities';
import { SelectionModel } from '@angular/cdk/collections';
import { OrdemDeServicoService } from '../../generated/services';
import { AfterViewInit } from '@angular/core/src/metadata/lifecycle_hooks';


@Component({
  selector: 'app-modal-gestor',
  templateUrl: './modal-gestor.component.html',
  styleUrls: ['./modal-gestor.component.css']
})
export class ModalGestorComponent implements OnInit, AfterViewInit {

  //Cria a lista de elementos
  ELEMENT_DATA: Gestor[] = [];

  //Pageable
  private pageable: PageRequest;



  //DataSource
  dataSource = new MatTableDataSource<Gestor>(this.ELEMENT_DATA);
  //Declara entidade gestor
  private gestor: Gestor = {};
  constructor(private paginationService: PaginationService,
              public dialogRef: MatDialogRef<ModalGestorComponent>,
              public service: OrdemDeServicoService) { 

    //this.pageable = this.paginationService.pageRequest('nome', 'ASC', 15);
  }

  displayedColumns = ['select', 'id', 'nome'];
  //SelectionModel<Entity>(const allowMultiSelect, const initialSelection)
  selection = new SelectionModel<Gestor>(false, []);  //Pageable

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  PageRequest
  ngOnInit() {
    //Busca Lista de Gestores
    this.onListGestorByNome();
  }
  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }
  /** 
   * Aplica o Filtro de consulta
  */
  public applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }
  /** 
   * Emite o para o solicitanteModel o registro selecionado após a confirmação
  */
  public emitter() {
    this.dialogRef.close(this.selection);
  }
 /**
 * Retorna se o numero do elemento selecionado corresponde ao total de linhas
 */
  public isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }
  /** 
   * Seleciona todas as linhas se elas não estão selecionadas, senão limpa a seleção
  */
  masterToggle() {
    if (this.isAllSelected()){
      this.selection.clear(); <Gestor>(this.ELEMENT_DATA);
      this.dataSource.data.forEach(row => this.selection.select(row));
    }        
  }
  /**
   * Lista os   const initialSelection = [];
  const allowMultiSelect = true;Gestores
   */
  public onListGestorByNome() {
    this.service.listGestorByNome(null,
      this.pageable).subscribe((result) => {
        this.ELEMENT_DATA = result.content;
        this.dataSource = new MatTableDataSource<Gestor>(this.ELEMENT_DATA)
        this.ngAfterViewInit();
      }), (error) => {
        alert(error.message);
      }
  }
}
