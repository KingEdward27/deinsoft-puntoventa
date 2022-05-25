import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { GenericListComponent } from 'src/app/base/components/generic-list/generic-list.component';
import { UtilService } from 'src/app/services/util.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-cnf-forma-pago',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfFormaPagoComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_forma_pago",
    "title": "Marcas",
    "columnsList":[{tableName: "cnf_forma_pago", columnName:"nombre",filterType:"text"}
                ],
    "columnsForm":[{tableName: "cnf_forma_pago", "columnName":"nombre","type":"input"}
           ],
    //filters sería para filtros adicionales
    "filters":{"cnf_forma_pago.nombre":""},
    "orders":["nombre"]
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,private routers: Router) { 
    super(utilServices,httpClients,routers);
  }
  ngOnInit(): void {
    super.baseEndpoint = this.baseEndpoint;
    super.properties = this.prop;
    console.log(this.prop);
    super.ngOnInit();
  }
  save(): void {
    console.log("test desde cnf-org");
  }
}

