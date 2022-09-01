import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-tipocomprobante',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfTipoComprobanteComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_tipo_comprobante",
    "title": "Tipo de comprobante",
    "columnsList":[{tableName: "cnf_tipo_comprobante",columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_tipo_comprobante",columnName:"codigo",filterType:"text"},
                   {tableName: "cnf_tipo_comprobante",columnName:"codigo_sunat",filterType:"text"},
                   {tableName: "cnf_tipo_comprobante",columnName:"flag_electronico"}],
    "columnsForm":[{tableName: "cnf_tipo_comprobante", "columnName":"nombre","type":"input"},
                   {tableName: "cnf_tipo_comprobante", "columnName":"codigo","type":"input"},
                   {tableName: "cnf_tipo_comprobante", "columnName":"codigo_sunat","type":"input"},
                   {tableName: "cnf_tipo_comprobante",columnName:"flag_electronico",type:"input"}
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["cnf_tipo_comprobante_id desc"]
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
    
  }
}

