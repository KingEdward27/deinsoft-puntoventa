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

export class CnfPlanContratoComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_plan_contrato",
    "title": "Locales",
    "columnsList":[{tableName: "cnf_plan_contrato", columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_plan_contrato", columnName:"precio",filterType:"text"},
                   {tableName: "cnf_plan_contrato", columnName:"dia_vencimiento",filterType:"none"}],
    //"columnsList":["name","address","cnf_company.name","cnf_district.name"],
    "foreignTables":[{"tableName":"cnf_empresa","idValue":"cnf_empresa_id"}],
    "columnsForm":[{tableName: "cnf_plan_contrato", columnName:"nombre",type:"input"},
                   {tableName: "cnf_plan_contrato", columnName:"precio",type:"input"},
                   {tableName: "cnf_plan_contrato", columnName:"dia_vencimiento",type:"input"}
                   
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["cnf_plan_contrato.cnf_plan_contrato_id"] 
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
    this.prop.conditions.push({"columnName":"cnf_plan_contrato.cnf_empresa_id","value":cnfEmpresa});
    super.properties = this.prop;
    console.log(this.prop);
    super.ngOnInit();
    
  }
  save(): void {
    
  }
}

