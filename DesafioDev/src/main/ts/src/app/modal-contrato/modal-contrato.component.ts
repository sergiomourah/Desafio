import { ContratoService } from './../../generated/services';
import { SelectionModel } from '@angular/cdk/collections';
import { PaginationService } from './../pagination.service';
import { MatTableDataSource, MatDialogRef, MatPaginator, MatSort, MatSnackBar } from '@angular/material';
import { Contrato, PageRequest } from './../../generated/entities';
import { Component, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-modal-contrato',
  templateUrl: './modal-contrato.component.html',
  styleUrls: ['./modal-contrato.component.css']
})
export class ModalContratoComponent implements OnInit {

  //Cria a lista de elementos
  ELEMENT_DATA: Contrato[] = [];

  //Pageable
  private pageable: PageRequest;



  //DataSource
  dataSource = new MatTableDataSource<Contrato>(this.ELEMENT_DATA);
  //Declara entidade gestor
  private contrato: Contrato = {};
  constructor(private paginationService: PaginationService,
    public dialogRef: MatDialogRef<ModalContratoComponent>,
    public service: ContratoService,
    private snackBar: MatSnackBar) { }

  displayedColumns = ['select', 'numeroContrato', 'nome'];
  //SelectionModel<Entity>(const allowMultiSelect, const initialSelection)
  selection = new SelectionModel<Contrato>(false, []);  //Pageable

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    //Busca Lista de Contratos
    this.onlistContratoByNumeroContrato();
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
  public emitter(salvar: boolean) {
    if (salvar && this.selection.selected.length > 0){
      this.dialogRef.close(this.selection);
    } else if (!salvar){
      this.dialogRef.close();
    }else{
      this.openSnackBar("Obrigatório selecionar contrato!", "Mensagem");
    }    
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
  private onlistContratoByNumeroContrato(): void {
    this.service.listContratoByFilters(null,
      null,
      null,
      null,
      null,
      null,
      null,
      this.pageable).subscribe((result) => {
        this.ELEMENT_DATA = result.content;
        this.dataSource = new MatTableDataSource<Contrato>(this.ELEMENT_DATA)
        this.ngAfterViewInit();
      }, (error) => {
        alert(error.message);
      });
  }

  private openSnackBar(message: string, action: string): void {
    this.snackBar.open(message, action, {
      duration: 4000,
      direction: "ltr"
    });
  }
}

