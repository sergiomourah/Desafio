import { ContratoDetailComponent } from './contrato/contrato-detail/contrato-detail.component';
import { ContratoInserirComponent } from './contrato/contrato-inserir/contrato-inserir.component';
import { OrdemDeServicoDetailComponent } from './ordemdeservico/ordem-de-servico-detail/ordem-de-servico-detail.component';
import { OrdemdeservicoInserirComponent } from './ordemdeservico/ordemdeservico-inserir/ordemdeservico-inserir.component';
import { OrdemdeservicoComponent } from './ordemdeservico/ordemdeservico.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/compiler/src/core';

import { ContratoComponent } from './contrato/contrato.component';

const routes: Routes = [
  {
    path: '',
    component: OrdemdeservicoComponent
  },
  {
    path: 'contrato',
    pathMatch: 'full',
    component: ContratoComponent
  },
  {
    path: 'contrato/inserir',
    pathMatch: 'full',
    component: ContratoInserirComponent
  },
  {
    path: 'contrato/editar/:id',
    pathMatch: 'full',
    component: ContratoInserirComponent
  },
  {
    path: 'contrato/detail/:id',
    pathMatch: 'full',
    component: ContratoDetailComponent
  },
  {
    path: 'ordemdeservico',
    pathMatch: 'full',
    component: OrdemdeservicoComponent
  },
  {
    path: 'ordemdeservico/inserir',
    pathMatch: 'full',
    component: OrdemdeservicoInserirComponent
  },
  {
    path: 'ordemdeservico/detail/:id',
    pathMatch: 'full',
    component: OrdemDeServicoDetailComponent
  },
  {
    path: 'ordemdeservico/editar/:id',
    pathMatch: 'full',
    component: OrdemdeservicoInserirComponent
  }
];

export const AppRoutingModule: ModuleWithProviders = RouterModule.forRoot(routes);
