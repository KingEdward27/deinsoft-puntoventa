import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';
@Component({
  selector: 'app-inv-tipo-mov',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class InvTipoMovComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "inv_tipo_mov_almacen",
    "title": "Tipo de Movimiento",
    "columnsList":[{tableName: "inv_tipo_mov_almacen", columnName:"nombre",filterType:"text"},
                   {tableName: "inv_tipo_mov_almacen",columnName:"codigo_sunat",filterType:"text"},
                   {tableName: "inv_tipo_mov_almacen",columnName:"naturaleza",filterType:"text"}],

    "columnsForm":[{tableName: "inv_tipo_mov_almacen", columnName:"nombre",type:"input"},
                   {tableName: "inv_tipo_mov_almacen", columnName:"codigo_sunat",type:"input"},
                   {tableName: "inv_tipo_mov_almacen", columnName:"naturaleza",type:"input"}
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["inv_tipo_mov_almacen.inv_tipo_mov_almacen_id desc"] 
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    private routers: Router,public _commonService:CommonService,private appService:AppService) { 
    super(utilServices,httpClients,routers,_commonService);
  }
  ngOnInit(): void {
    super.baseEndpoint = this.baseEndpoint;
    // let cnfEmpresa =  this.appService.getProfile().profile.split("|")[1];
    // this.prop.conditions.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    // this.prop.columnsForm[1].filters.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    super.properties = this.prop;
    console.log(this.prop);
    super.ngOnInit();
  }
  save(): void {
    
  }
}

