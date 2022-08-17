import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-numcomprobante',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfNumComprobanteComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_num_comprobante",
    "title": "Numeración de comprobantes",
    "columnsList":[{tableName: "cnf_local",columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_tipo_comprobante",columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_num_comprobante",columnName:"serie",filterType:"text"},
                   {tableName: "cnf_num_comprobante",columnName:"ultimo_nro",filterType:"text"}],
    "foreignTables":[{"tableName":"cnf_tipo_comprobante","idValue":"cnf_tipo_comprobante_id"},
                     {"tableName":"cnf_local","idValue":"cnf_local_id"}],
    "columnsForm":[
      {tableName: "cnf_local", "columnName":"nombre","type":"select",loadState : 1,
                   relatedBy:"cnf_local_id",filters:[]},             
      {tableName: "cnf_num_comprobante", "columnName":"serie","type":"input"},
                   {tableName: "cnf_num_comprobante", "columnName":"ultimo_nro","type":"input"},
                   {tableName: "cnf_tipo_comprobante", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_tipo_comprobante_id"},
                   
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "preSave":[],
    "orders":["cnf_num_comprobante_id desc"]
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    private routers: Router,public _commonService:CommonService,private appService:AppService) { 
    super(utilServices,httpClients,routers,_commonService);
  }
  ngOnInit(): void {
    super.baseEndpoint = this.baseEndpoint;
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    console.log(cnfEmpresa);
    
    this.prop.conditions.push({"columnName":"cnf_num_comprobante.cnf_empresa_id","value":cnfEmpresa});
    this.prop.preSave.push({columnForm:"cnf_empresa_id",value:cnfEmpresa});
    this.prop.columnsForm[0].filters.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    super.properties = this.prop;
    console.log(this.prop);
    super.ngOnInit();
  }
  save(): void {
    console.log("test desde cnf-org");
  }
}

