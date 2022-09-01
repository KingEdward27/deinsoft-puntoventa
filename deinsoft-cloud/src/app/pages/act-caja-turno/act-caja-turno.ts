import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';
@Component({
  selector: 'app-act-caja-turno',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class ActCajaTurnoComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "act_caja_turno",
    "title": "Turnos de Caja",
    "columnsList":[
      {tableName: "act_caja", columnName:"nombre",filterType:"text"},
      {tableName: "seg_usuario", columnName:"nombre",filterType:"text"},
      {tableName: "act_caja_turno", columnName:"fecha_apertura",filterType:"text"},
      {tableName: "act_caja_turno", columnName:"monto_apertura",filterType:"text"},
      {tableName: "act_caja_turno", columnName:"fecha_cierre",filterType:"text"},
      {tableName: "act_caja_turno", columnName:"monto_cierre",filterType:"text"}
                ],
    "foreignTables":[
      {"tableName":"act_caja","idValue":"act_caja_id"},
      {"tableName":"seg_usuario","idValue":"seg_usuario_id"}],
    "columnsForm":[
      {tableName: "act_caja", columnName:"nombre",type:"select",loadState : 1,relatedBy:"act_caja_id",filters:[]},
      {tableName: "seg_usuario", columnName:"nombre",type:"select",loadState : 1,relatedBy:"seg_usuario_id",filters:[]},
      {tableName: "act_caja_turno", columnName:"fecha_apertura",type:"date"},
      {tableName: "act_caja_turno", columnName:"monto_apertura",type:"input"},
      {tableName: "act_caja_turno", columnName:"fecha_cierre",type:"date"},
      {tableName: "act_caja_turno", columnName:"monto_cierre",type:"input"}
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "preSave" : [{columnForm:"estado",value:"1"}],
    "orders":["act_caja_turno_id desc"]
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    private routers: Router,public _commonService:CommonService,private appService:AppService) { 
    super(utilServices,httpClients,routers,_commonService);
  }
  ngOnInit(): void { 
    super.baseEndpoint = this.baseEndpoint;
    let user = this.appService.getProfile()
    let cnfEmpresa = user.profile.split("|")[1];
    
    // this.prop.preSave.push({columnForm:"cnf_empresa_id",value:cnfEmpresa});
    
    this.prop.columnsForm[0].filters.push({"columnName":"act_caja.cnf_empresa_id","value":cnfEmpresa});
    this.prop.columnsForm[1].filters.push({"columnName":"seg_usuario.seg_usuario_id","value":user.id});
    this.prop.conditions.push({"columnName":"act_caja.cnf_empresa_id","value":cnfEmpresa});
    super.properties = this.prop;
    console.log(this.prop);
    super.ngOnInit();
  }
  save(): void {
    
  }
}

