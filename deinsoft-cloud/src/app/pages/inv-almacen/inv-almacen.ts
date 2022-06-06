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

export class InvAlmacenComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "inv_almacen",
    "title": "Locales",
    "columnsList":[{tableName: "inv_almacen", columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_local",columnName:"nombre",filterType:"none"}],
    //"columnsList":["name","address","cnf_company.name","cnf_district.name"],
    "foreignTables":[{"tableName":"cnf_local","idValue":"cnf_local_id"}],
    "columnsForm":[{tableName: "inv_almacen", columnName:"nombre",type:"input"},
                   {tableName: "cnf_local", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_local_id"}
           ],
    //filters sería para filtros adicionales
    "filters":{"inv_almacen.nombre":""},
    "orders":["cnf_local_id"] 
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

