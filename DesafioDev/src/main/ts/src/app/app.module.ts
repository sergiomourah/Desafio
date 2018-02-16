import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { GeneratedModule } from '../generated/generated.module';
import { BROKER_CONFIGURATION } from '../generated/services-wrapper';
import { ContratoComponent } from './contrato/contrato.component';


@NgModule({
  declarations: [
    AppComponent,
    ContratoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    GeneratedModule,
    HttpModule,
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
