import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
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
                   {tableName: "cnf_empresa",columnName:"nombre",filterType:"none"},
                   {tableName: "cnf_local",columnName:"impresora_nombre",filterType:"none"}],
    //"columnsList":["name","address","cnf_company.name","cnf_district.name"],
    "foreignTables":[{"tableName":"cnf_empresa","idValue":"cnf_empresa_id"}],
    "columnsForm":[{tableName: "cnf_empresa", "columnName":"nombre","type":"select",
                    loadState : 1,relatedBy:"cnf_empresa_id",filters:[]},
                   {tableName: "cnf_local", columnName:"nombre",type:"input"},
                   {tableName: "cnf_local", columnName:"direccion",type:"input"},
                   {tableName: "cnf_local", columnName:"impresora_nombre",type:"input"}
                   
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["cnf_local.cnf_local_id"] 
  }
  constructor(private utilServices: UtilService,
    private httpClients:HttpClient,private routers: Router,
    public _commonService:CommonService,
    private appService:AppService) { 
    super(utilServices,httpClients,routers,_commonService);
  }
  ngOnInit(): void {
    super.baseEndpoint = this.baseEndpoint;
    let user = this.appService.getProfile();
    console.log(user);
    
    let cnfEmpresa = user.profile.split("|")[1];
    console.log(cnfEmpresa);
    
    this.prop.conditions.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    this.prop.columnsForm[0].filters.push({"columnName":"cnf_empresa.cnf_empresa_id","value":cnfEmpresa});
    super.properties = this.prop;
    console.log(this.prop);
    super.ngOnInit();
    
  }
  save(): void {
    
  }
}

