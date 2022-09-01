import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-unidadmedida',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfUnidadMedidaComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_unidad_medida",
    "title": "Unidad Medida",
    "columnsList":[{tableName: "cnf_unidad_medida", columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_unidad_medida", columnName:"codigo_sunat",filterType:"text"}
                ],
    "columnsForm":[{tableName: "cnf_unidad_medida", "columnName":"nombre","type":"input"},
                   {tableName: "cnf_unidad_medida", "columnName":"codigo_sunat","type":"input"}
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["nombre"]
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

