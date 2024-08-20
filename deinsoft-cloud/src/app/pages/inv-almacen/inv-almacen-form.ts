import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';
@Component({
  selector: 'app-inv-almacen-form',
  template: '<app-generic-form2 [props] = prop  [route] = "this.prop.route"  [onPreLoadForm]="onPreLoad" ></app-generic-form2>'
})

export class InvAlmacenFormComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "inv_almacen",
    "title": "Almacenes",
    "foreignTables":[{"tableName":"cnf_local","idValue":"cnf_local_id"}],
    "columnsForm":[{tableName: "inv_almacen", columnName:"nombre",type:"input"},
                   {tableName: "cnf_local", "columnName":"nombre","type":"select",
                   loadState : 1,relatedBy:"cnf_local_id",filters:[]}
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["inv_almacen.inv_almacen_id"],
    "route": "/almacen",
    "preSave" : [],
    "functions": []
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    private routers: Router,public _commonService:CommonService,private appService:AppService) { 
    // super(utilServices,httpClients,routers,_commonService);
  }
  ngOnInit(): void {
    console.log("init ngOnInit");
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    console.log(cnfEmpresa);
    this.prop.conditions.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    this.prop.columnsForm[1].filters.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    this.prop.functions.push({func : this.onPreLoad});
  }
  save(): void {
    
  }
  onPreLoad  = (): boolean => {
    // super.baseEndpoint = this.baseEndpoint;
    console.log("init onPreLoad");
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    this.prop.conditions.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    this.prop.columnsForm[1].filters.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    console.log("onPreLoad",cnfEmpresa);
    
    return true;
  }
}

