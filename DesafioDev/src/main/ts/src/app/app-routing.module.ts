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
  }
];

export const AppRoutingModule: ModuleWithProviders = RouterModule.forRoot(routes);
