import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-distrito',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfDistritoComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_distrito",
    "title": "Distritos",
    "columnsList":[{tableName: "cnf_distrito", columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_provincia", columnName:"nombre",filterType:"text"}
                ],
    "foreignTables":[{"tableName":"cnf_provincia","idValue":"cnf_provincia_id"}],
    "columnsForm":[{tableName: "cnf_distrito", "columnName":"nombre","type":"input"},
                   {tableName: "cnf_provincia", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_provincia_id"}
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["cnf_distrito.nombre"]
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

