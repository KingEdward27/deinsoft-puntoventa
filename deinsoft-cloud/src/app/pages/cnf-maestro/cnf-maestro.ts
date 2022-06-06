import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-maestro',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfMaestroComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_maestro",
    "title": "Socios de Negocio",
    "columnsList":[{tableName: "cnf_maestro", columnName:"nro_doc",filterType:"text"},
                   {tableName: "cnf_maestro", columnName:"apellido_paterno",filterType:"text"},
                   {tableName: "cnf_maestro", columnName:"apellido_materno",filterType:"text"},
                   {tableName: "cnf_maestro",columnName:"nombres",filterType:"text"},
                   {tableName: "cnf_maestro",columnName:"razon_social",filterType:"text"},
                   {tableName: "cnf_maestro",columnName:"direccion",filterType:"text"},
                   {tableName: "cnf_maestro",columnName:"correo",filterType:"text"},
                   {tableName: "cnf_maestro",columnName:"telefono",filterType:"text"},
                   {tableName: "cnf_distrito",columnName:"nombre",filterType:"none"},
                   {tableName: "cnf_tipo_documento",columnName:"nombre",filterType:"none"}],
    //"columnsList":["name","address","cnf_company.name","cnf_district.name"],
    "foreignTables":[{"tableName":"cnf_distrito","idValue":"cnf_distrito_id"},
                {"tableName":"cnf_tipo_documento","idValue":"cnf_tipo_documento_id"}],
    "columnsForm":[{tableName: "cnf_maestro", columnName:"nro_doc",type:"input"},
                    {tableName: "cnf_maestro", columnName:"apellido_paterno",type:"input"},
                    {tableName: "cnf_maestro", columnName:"apellido_materno",type:"input"},
                    {tableName: "cnf_maestro",columnName:"nombres",type:"input"},
                    {tableName: "cnf_maestro",columnName:"razon_social",type:"input"},
                    {tableName: "cnf_maestro",columnName:"direccion",type:"input"},
                    {tableName: "cnf_maestro",columnName:"correo",type:"input"},
                    {tableName: "cnf_maestro",columnName:"telefono",type:"input"},
                   {tableName: "cnf_tipo_documento", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_tipo_documento_id"},
                   {tableName: "cnf_region", "columnName":"nombre","type":"select",loadState : 1,loadFor:"cnf_distrito_id",load:{tableName:"cnf_provincia",loadBy:"cnf_region_id"}},
                   {tableName: "cnf_provincia", "columnName":"nombre","type":"select",loadState : 0,loadFor:"cnf_distrito_id",load:{tableName:"cnf_distrito",loadBy:"cnf_provincia_id"}},
                   {tableName: "cnf_distrito", "columnName":"nombre","type":"select",loadState : 0,loadFor:"cnf_distrito_id",relatedBy:"cnf_distrito_id"}
           ],
    //filters sería para filtros adicionales
    "filters":{"cnf_maestro.nombres":"","cnf_maestro.direccion":""},
    "orders":["nombres","direccion"]
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

