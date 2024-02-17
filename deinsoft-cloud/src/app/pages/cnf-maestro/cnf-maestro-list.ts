import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-maestro-list',
  template: '<app-generic-list3 [route] = "this.prop.route" [newButton] = "gotoForm" [editButton] = "editButton" [props] = prop [onPreLoadForm]="wa" [onPreSave]="onPreSave"></app-generic-list3>'
})

export class CnfMaestroList2Component implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_maestro",
    "title": "Clientes y Proveedores",
    "columnsList":[{tableName: "cnf_tipo_documento",columnName:"abreviatura",filterType:"none"},
                    {tableName: "cnf_maestro", columnName:"nro_doc",filterType:"text"},
                   {tableName: "cnf_maestro", columnName:"apellido_paterno",filterType:"text"},
                   {tableName: "cnf_maestro", columnName:"apellido_materno",filterType:"text"},
                   {tableName: "cnf_maestro",columnName:"razon_social",filterType:"text"},
                   {tableName: "cnf_maestro",columnName:"direccion",filterType:"text"},
                   {tableName: "cnf_maestro",columnName:"correo",filterType:"text"},
                   {tableName: "cnf_maestro",columnName:"telefono",filterType:"text"},
                   {tableName: "cnf_distrito",columnName:"nombre",filterType:"none"},
                  ],
    //"columnsList":["name","address","cnf_company.name","cnf_district.name"],
    "foreignTables":[{"tableName":"cnf_distrito","idValue":"cnf_distrito_id"},
                {"tableName":"cnf_tipo_documento","idValue":"cnf_tipo_documento_id"}],
    
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["nombres","direccion"],
    "preSave" : [],
    "functions": [],
    "route": "/maestro"
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    public routers: Router,public _commonService:CommonService,private appService:AppService) { 
    //super(utilServices,httpClients,routers,_commonService);
  }
  gotoForm = () => {
    
    this.routers.navigate(["/new-maestro"]);
  }
  editButton= () => {
    
  }
  wa  = (): boolean => {
    console.log("XD");
    this.otroLog();
    return true;
    
  }
  w2 () {
    console.log("w2");
    this.otroLog();
    return true;
    
  }
  onPreSave = (): boolean => {
    console.log("XDDDDD");
    return false;
  }
  ngOnInit(): void {
    //this.prop.preSave =  this.wa();
    //this._commonService.baseEndpoint = this.baseEndpoint;
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    this.prop.preSave.push({columnForm:"cnf_empresa_id",value:cnfEmpresa});
    this.prop.conditions.push({"columnName":"cnf_maestro.cnf_empresa_id","value":cnfEmpresa});
    //this.prop.preSave.push({columnForm:"razon_social",value:"columnsForm.apellido_paterno +' '+columnsForm.apellido_materno +' '+columnsForm.nombres"})
    this.prop.functions.push({func : this.wa});
    
    console.log(this.prop);
    //super.ngOnInit();
  }
  otroLog () {
    
    console.log("ohhh");
  }
  // async save(){
    
  //   let res = await super.preSave();
  //   
  // }
}

