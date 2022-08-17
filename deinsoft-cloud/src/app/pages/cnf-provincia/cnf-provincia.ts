import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-provincia',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfProvinciaComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_provincia",
    "title": "Provincias",
    "columnsList":[{tableName: "cnf_provincia", columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_region", columnName:"nombre",filterType:"text"}
                ],
    "foreignTables":[{"tableName":"cnf_region","idValue":"cnf_region_id"}],           
    "columnsForm":[{tableName: "cnf_provincia", "columnName":"nombre","type":"input"},
                   {tableName: "cnf_region", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_region_id"} 
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["cnf_provincia.nombre"]
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

