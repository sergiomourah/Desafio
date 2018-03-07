import { SelectionModel } from '@angular/cdk/collections';
import { ContratoService } from './../../generated/services';
import { PaginationService } from './../pagination.service';
import { MatTableDataSource, MatDialogRef, MatPaginator, MatSort } from '@angular/material';
import { Cliente, PageRequest } from './../../generated/entities';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-modal-cliente',
  templateUrl: './modal-cliente.component.html',
  styleUrls: ['./modal-cliente.component.css']
})
export class ModalClienteComponent implements OnInit {

  //Cria a lista de elementos
  ELEMENT_DATA: Cliente[] = [];

  //Pageable
  private pageable: PageRequest;



  //DataSource
  dataSource = new MatTableDataSource<Cliente>(this.ELEMENT_DATA);
  //Declara entidade gestor
  private cliente: Cliente = {};
  constructor(private paginationService: PaginationService,
    public dialogRef: MatDialogRef<ModalClienteComponent>,
    public service: ContratoService) { }

  displayedColumns = ['select', 'id', 'nome'];
  //SelectionModel<Entity>(const allowMultiSelect, const initialSelection)
  selection = new SelectionModel<Cliente>(false, []);  //Pageable

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    //Busca Lista de Gestores
    this.onListClienteByNome();
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
      this.selection.clear(); 
      this.dataSource.data.forEach(row => this.selection.select(row));
    }        
  }
  /**
   * Lista os   const initialSelection = [];
  const allowMultiSelect = true;Gestores
   */
  public onListClienteByNome() {
    this.service.listClienteByNome(null,
      this.pageable).subscribe((result) => {
        this.ELEMENT_DATA = result.content;
        this.dataSource = new MatTableDataSource<Cliente>(this.ELEMENT_DATA)
        this.ngAfterViewInit();
      }), (error) => {
        alert(error.message);
      }
  }
}
