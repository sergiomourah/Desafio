import { OrdemDeServicoEditComponent } from './ordemdeservico/ordem-de-servico-edit/ordem-de-servico-edit.component';

import { PaginationService } from './pagination.service';
import { RouterModule } from '@angular/router';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { OrdemdeservicoInserirComponent } from './ordemdeservico/ordemdeservico-inserir/ordemdeservico-inserir.component';
import { HttpModule } from '@angular/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { GeneratedModule } from '../generated/generated.module';
import { BROKER_CONFIGURATION } from '../generated/services-wrapper';
import { ContratoComponent } from './contrato/contrato.component';

import { CovalentLayoutModule } from '@covalent/core/layout';
import { CovalentStepsModule  } from '@covalent/core/steps';
/* any other core modules */
// (optional) Additional Covalent Modules imports
import { CovalentHttpModule } from '@covalent/http';
import { CovalentHighlightModule } from '@covalent/highlight';
import { CovalentMarkdownModule } from '@covalent/markdown';
import { CovalentDynamicFormsModule } from '@covalent/dynamic-forms';
import { MatFormFieldModule, MatInputModule, MatCheckboxModule, MatButtonModule, MatCardModule, MatIconModule, MatNavList, MatListModule, MatSelectModule, MatCardContent, MatDatepickerModule, MatNativeDateModule, MatTableModule, MatPaginatorModule, MatDialogModule, MatSnackBarModule, MatSortModule, MatTabsModule, MatAutocompleteModule } from '@angular/material';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { CovalentExpansionPanelModule } from '@covalent/core/expansion-panel';
import { OrdemdeservicoComponent } from './ordemdeservico/ordemdeservico.component';
import { MsgDialogComponent } from './msg-dialog/msg-dialog.component';
import { ModalGestorComponent } from './modal-gestor/modal-gestor.component';
import { OrdemDeServicoDetailComponent } from './ordemdeservico/ordem-de-servico-detail/ordem-de-servico-detail.component';
import { ViewChild } from '@angular/core/src/metadata/di';
import {
  TdDataTableService,
  TdDataTableSortingOrder,
  ITdDataTableSortChangeEvent,
  ITdDataTableColumn,
  CovalentSearchModule,
  CovalentDataTableModule,
  CovalentPagingModule,
  TdDialogService
} from '@covalent/core';


@NgModule({
  declarations: [
    AppComponent,
    ContratoComponent,
    OrdemdeservicoComponent,
    OrdemdeservicoInserirComponent,
    MsgDialogComponent,
    ModalGestorComponent,
    OrdemDeServicoDetailComponent,
    OrdemDeServicoEditComponent
  ],
  entryComponents: [
    MsgDialogComponent,
    ModalGestorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,    FormsModule,
    GeneratedModule,
    HttpModule,
    FormsModule,    
    MatFormFieldModule,
    MatInputModule,    FormsModule,

    BrowserAnimationsModule,
    NoopAnimationsModule,
    CovalentLayoutModule,
    CovalentStepsModule,
    // (optional) Additional Covalent Modules imports
    CovalentHttpModule.forRoot(),
    CovalentHighlightModule,
    CovalentMarkdownModule,
    CovalentDynamicFormsModule,
    CovalentExpansionPanelModule,
    MatButtonModule,
    MatCheckboxModule,
    MatCardModule,
    MatIconModule,
    MatListModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatTableModule,
    MatPaginatorModule,
    MatDialogModule,
    RouterModule,
    MatSnackBarModule,
    MatSortModule,
    MatFormFieldModule,
    MatTabsModule,
    MatIconModule,
    MatCardModule,
    MatSelectModule,
    MatAutocompleteModule,
    CovalentSearchModule,
    CovalentDataTableModule,
    CovalentPagingModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule    
  ],
  providers: [
    {
    provide: BROKER_CONFIGURATION,
    useValue: {
      path: '/broker',
      realTime: false
    }
  },
  {provide: MAT_DATE_LOCALE, useValue: 'en-GB'},
  PaginationService
],
  bootstrap: [AppComponent]
})
export class AppModule { }