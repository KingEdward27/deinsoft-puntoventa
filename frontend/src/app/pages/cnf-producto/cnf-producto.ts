import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { GenericListComponent } from 'src/app/base/components/generic-list/generic-list.component';
import { UtilService } from 'src/app/services/util.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-cnf-producto',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfProductoComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_producto",
    "title": "Productos",
    "columnsList":[{tableName: "cnf_producto", columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_producto", columnName:"precio",filterType:"text"},
                   {tableName: "cnf_producto", columnName:"codigo",filterType:"text"},
                   {tableName: "cnf_marca",columnName:"nombre",filterType:"none"},
                   {tableName: "cnf_unidad_medida",columnName:"nombre",filterType:"none"},
                   {tableName: "cnf_sub_categoria",columnName:"nombre",filterType:"none"}],
    //"columnsList":["name","address","cnf_company.name","cnf_district.name"],
    "foreignTables":[{"tableName":"cnf_marca","idValue":"cnf_marca_id"},
                     {"tableName":"cnf_unidad_medida","idValue":"cnf_unidad_medida_id"},
                     {"tableName":"cnf_sub_categoria","idValue":"cnf_sub_categoria_id"}],
    "columnsForm":[{tableName: "cnf_producto", columnName:"nombre",type:"input"},
                   {tableName: "cnf_producto", columnName:"precio",type:"input"},
                   {tableName: "cnf_producto", columnName:"codigo",type:"input"},
                   {tableName: "cnf_marca", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_marca_id"},
                   {tableName: "cnf_unidad_medida", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_unidad_medida_id"},
                   {tableName: "cnf_sub_categoria", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_sub_categoria_id"}
           ],
    //filters sería para filtros adicionales
    "filters":{"cnf_producto.nombre":"","cnf_producto.direccion":""},
    "orders":["cnf_producto_id","direccion"] 
  }
  constructor(private utilServices: UtilService,private httpClients:HttpClient,private routers: Router) { 
    super(utilServices,httpClients,routers);
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

