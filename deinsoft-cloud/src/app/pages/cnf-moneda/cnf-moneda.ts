import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-moneda',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfMonedaComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_moneda",
    "title": "Moneda",
    "columnsList":[{tableName: "cnf_moneda",columnName:"codigo",filterType:"text"},
                   {tableName: "cnf_moneda",columnName:"nombre",filterType:"none"}],
    "columnsForm":[{tableName: "cnf_moneda", "columnName":"codigo","type":"input"},
                   {tableName: "cnf_moneda", "columnName":"nombre","type":"input"}
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["codigo","nombre"]
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

