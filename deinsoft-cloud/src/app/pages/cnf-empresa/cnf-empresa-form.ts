import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-empresa-form',
  template: '<app-generic-form2 [props] = prop [onChanges] = "changes" [route] = "this.prop.route" [onPreLoadForm]="wa" [onPreSave]="onPreSave"></app-generic-form2>'
})

export class CnfEmpresaForm2Component implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_empresa",
    "title": "Empresa",
    "foreignTables":[{"tableName":"cnf_distrito","idValue":"cnf_distrito_id"},
                {"tableName":"cnf_tipo_documento","idValue":"cnf_tipo_documento_id"},
                {"tableName":"cnf_moneda","idValue":"cnf_moneda_id"}],
    "columnsForm":[{tableName: "cnf_empresa", columnName:"nombre",type:"input"},
                {tableName: "cnf_empresa", columnName:"descripcion",type:"input"},
                {tableName: "cnf_tipo_documento", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_tipo_documento_id"},
                {tableName: "cnf_empresa", "columnName":"nro_documento","type":"input"},
                {tableName: "cnf_empresa", "columnName":"direccion","type":"input"},  
                {tableName: "cnf_empresa",columnName:"telefono",type:"input"},
                {tableName: "cnf_region", "columnName":"nombre","type":"select",loadState : 1,loadFor:"cnf_distrito_id",load:{tableName:"cnf_provincia",loadBy:"cnf_region_id"}},
                {tableName: "cnf_provincia", "columnName":"nombre","type":"select",loadState : 0,loadFor:"cnf_distrito_id",load:{tableName:"cnf_distrito",loadBy:"cnf_provincia_id"}},
                {tableName: "cnf_distrito", "columnName":"nombre","type":"select",loadState : 0,loadFor:"cnf_distrito_id",relatedBy:"cnf_distrito_id"},
                
                {tableName: "cnf_empresa",columnName:"flag_compra_rapida","type":"select",loadState : 1, relatedBy :"flag_compra_rapida",
                listData:[]},
                {tableName: "cnf_empresa",columnName:"flag_venta_rapida","type":"select",loadState : 1, relatedBy :"flag_venta_rapida",
                listData:[]},
                {tableName: "cnf_moneda", "columnName":"nombre","type":"select",
                loadState : 1,relatedBy:"cnf_moneda_id"},
        ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["cnf_empresa.nombre","cnf_empresa.direccion"],
    "preSave" : [],
    "functions": [],
    "route": "/",
    "id":""
  }

  changes: any;
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    public routers: Router,public _commonService:CommonService,private appService:AppService) { 
    //super(utilServices,httpClients,routers,_commonService);
  }
  wa  = (): boolean => {
    return true;
  }
  onPreSave = (): boolean => {
    return true;
  }
  onChangeTipoDoc = () => {
    return true;
  }
  ngOnInit(): void {
    //this.prop.preSave =  this.wa();
    //this._commonService.baseEndpoint = this.baseEndpoint;
    // this.prop.columnsForm[11].listData.push([0, "- Seleccione -"]);
    // this.prop.columnsForm[11].listData.push([1, "Venta de productos y servicios"]);
    // this.prop.columnsForm[11].listData.push([2, "Servicio de pago mensual"]); 
    // this.prop.columnsForm[11].listData.push([3, "Colegio"]); 
    // this.prop.columnsForm[11].listData.push([4, "Lavanderia"]); 
    
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    this.prop.id = cnfEmpresa;
    console.log(cnfEmpresa);
    
    // this.prop.columnsForm[11].listData.push([0, "- Seleccione -"]);
    // this.prop.columnsForm[11].listData.push([1, "Venta de productos y servicios"]);
    // this.prop.columnsForm[11].listData.push([2, "Servicio de pago mensual"]); 
    // this.prop.columnsForm[11].listData.push([3, "Colegio"]); 
    // this.prop.columnsForm[11].listData.push([4, "Lavanderia"]); 

    this.prop.columnsForm[9].listData.push([0, "NO"]);
    this.prop.columnsForm[9].listData.push([1, "SI"]);

    this.prop.columnsForm[10].listData.push([0, "NO"]);
    this.prop.columnsForm[10].listData.push([1, "SI"]);

    this.prop.conditions.push({"columnName":"cnf_empresa.cnf_empresa_id","value":cnfEmpresa});
    this.prop.preSave.push({columnForm:"cnf_empresa.cnf_empresa_id",value:cnfEmpresa});
    //this.prop.preSave.push({columnForm:"razon_social",value:"columnsForm.apellido_paterno +' '+columnsForm.apellido_materno +' '+columnsForm.nombres"})
    this.prop.functions.push({func : this.wa});
    
    this.changes = []
    this.changes.push({columnName: "cnf_tipo_documento.cnf_tipo_documento_id", function:this.onChangeTipoDoc})

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

