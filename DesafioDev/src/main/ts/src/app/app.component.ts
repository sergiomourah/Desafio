import { Component } from '@angular/core';
import { AppService } from './app.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  getContrato(){
    var appserv: AppService
    appserv.InsertContrato()
    alert("Contrato incluído com sucesso!");
  }
}
