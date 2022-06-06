import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';
@Component({
  selector: 'app-cnf-categoria',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfCategoriaComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_categoria",
    "title": "Categorías",
    "columnsList":[{tableName: "cnf_categoria", columnName:"nombre",filterType:"text"}
                ],
    "columnsForm":[{tableName: "cnf_categoria", "columnName":"nombre","type":"input"}
           ],
    //filters sería para filtros adicionales
    "filters":{"cnf_categoria.nombre":""},
    "orders":["nombre"]
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,private routers: Router,public _commonService:CommonService) { 
    super(utilServices,httpClients,routers,_commonService);
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

