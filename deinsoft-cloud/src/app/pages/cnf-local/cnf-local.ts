import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-empresa',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfLocalComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_local",
    "title": "Locales",
    "columnsList":[{tableName: "cnf_local", columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_local", columnName:"direccion",filterType:"text"},
                   {tableName: "cnf_empresa",columnName:"nombre",filterType:"none"}],
    //"columnsList":["name","address","cnf_company.name","cnf_district.name"],
    "foreignTables":[{"tableName":"cnf_empresa","idValue":"cnf_empresa_id"}],
    "columnsForm":[{tableName: "cnf_local", columnName:"nombre",type:"input"},
                   {tableName: "cnf_local", columnName:"direccion",type:"input"},
                   {tableName: "cnf_empresa", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_empresa_id"}
           ],
    //filters sería para filtros adicionales
    "filters":{"cnf_local.nombre":"","cnf_local.direccion":""},
    "orders":["cnf_local_id","direccion"] 
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

