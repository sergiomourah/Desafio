import { HttpModule } from '@angular/http';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

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
import { MatFormFieldModule, MatInputModule, MatCheckboxModule, MatButtonModule, MatCardModule, MatIconModule, MatNavList, MatListModule, MatSelectModule, MatCardContent } from '@angular/material';
import { BrowserAnimationsModule, NoopAnimationsModule } from '@angular/platform-browser/animations';
import { CovalentExpansionPanelModule } from '@covalent/core/expansion-panel';
import { OrdemdeservicoComponent } from './ordemdeservico/ordemdeservico.component';


@NgModule({
  declarations: [
    AppComponent,
    ContratoComponent,
    OrdemdeservicoComponent
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
    MatSelectModule
  ],
  providers: [{
    provide: BROKER_CONFIGURATION,
    useValue: {
      path: '/broker',
      realTime: false
    },   
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
