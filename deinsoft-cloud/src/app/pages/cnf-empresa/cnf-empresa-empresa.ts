import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';
import { AppService } from '../../services/app.service';

@Component({
  selector: 'app-cnf-empresa',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfEmpresaEmpresaComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_empresa",
    "title": "Empresa",
    "columnsList":[{tableName: "cnf_empresa", columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_empresa", columnName:"descripcion",filterType:"text"},
                   {tableName: "cnf_empresa", columnName:"nro_documento",filterType:"text"},
                   {tableName: "cnf_empresa",columnName:"direccion",filterType:"text"},
                   {tableName: "cnf_empresa",columnName:"telefono",filterType:"text"},
                   {tableName: "cnf_distrito",columnName:"nombre",filterType:"none"},
                   {tableName: "cnf_tipo_documento",columnName:"nombre",filterType:"none"}],
    //"columnsList":["name","address","cnf_company.name","cnf_district.name"],
    "foreignTables":[{"tableName":"cnf_distrito","idValue":"cnf_distrito_id"},
                     {"tableName":"cnf_tipo_documento","idValue":"cnf_tipo_documento_id"}],
    "columnsForm":[{tableName: "cnf_empresa", columnName:"nombre",type:"input"},
                   {tableName: "cnf_empresa", columnName:"descripcion",type:"input"},
                   {tableName: "cnf_tipo_documento", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_tipo_documento_id"},
                   {tableName: "cnf_empresa", "columnName":"nro_documento","type":"input"},
                   {tableName: "cnf_empresa", "columnName":"direccion","type":"input"},  
                   {tableName: "cnf_empresa",columnName:"telefono",type:"input"},
                   {tableName: "cnf_region", "columnName":"nombre","type":"select",loadState : 1,loadFor:"cnf_distrito_id",load:{tableName:"cnf_provincia",loadBy:"cnf_region_id"}},
                   {tableName: "cnf_provincia", "columnName":"nombre","type":"select",loadState : 0,loadFor:"cnf_distrito_id",load:{tableName:"cnf_distrito",loadBy:"cnf_provincia_id"}},
                   {tableName: "cnf_distrito", "columnName":"nombre","type":"select",loadState : 0,loadFor:"cnf_distrito_id",relatedBy:"cnf_distrito_id"},
                   {tableName: "cnf_empresa",columnName:"ruta_pse",type:"input"},
                   {tableName: "cnf_empresa",columnName:"token",type:"input"},
                   {tableName: "cnf_empresa",columnName:"perfil_empresa","type":"select",loadState : 1, relatedBy :"perfil_empresa",
                   listData:[]},
                   {tableName: "cnf_empresa",columnName:"flag_compra_rapida","type":"select",loadState : 1, relatedBy :"flag_compra_rapida",
                   listData:[]},
                   {tableName: "cnf_empresa",columnName:"flag_venta_rapida","type":"select",loadState : 1, relatedBy :"flag_venta_rapida",
                   listData:[]},
                   {tableName: "cnf_moneda", "columnName":"nombre","type":"select",
                   loadState : 1,relatedBy:"cnf_moneda_id"},
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "preSave" : [
      {columnForm:"estado",value:"1"}
    ],
    "orders":["cnf_empresa.nombre","cnf_empresa.direccion"]
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,private routers: Router,public _commonService:CommonService,
    private appService:AppService) { 
    super(utilServices,httpClients,routers,_commonService);
  }
  ngOnInit(): void {
    super.baseEndpoint = this.baseEndpoint;
    // this.prop.columnsForm[11].listData.push([0, "- Seleccione -"]);
    // this.prop.columnsForm[11].listData.push([1, "Venta de productos y servicios"]);
    // this.prop.columnsForm[11].listData.push([2, "Servicio de pago mensual"]); 
    // this.prop.columnsForm[11].listData.push([3, "Colegio"]); 
    // this.prop.columnsForm[11].listData.push([4, "Lavanderia"]); 

    let user = this.appService.getProfile();
    let cnfEmpresa = user.profile.split("|")[1];
    console.log(cnfEmpresa);
    
    this.prop.conditions.push({"columnName":"cnf_empresa.cnf_empresa_id","value":cnfEmpresa});
    super.properties = this.prop;
    console.log(this.prop);
    super.ngOnInit();
  }
  save(): void {
    
  }
}

