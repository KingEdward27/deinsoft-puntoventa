import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { GenericListComponent } from 'src/app/base/components/generic-list/generic-list.component';
import { UtilService } from 'src/app/services/util.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-cnf-numcomprobante',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfNumComprobanteComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_num_comprobante",
    "title": "Numeración de comprobantes",
    "columnsList":[{tableName: "cnf_local",columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_tipo_comprobante",columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_num_comprobante",columnName:"serie",filterType:"text"},
                   {tableName: "cnf_num_comprobante",columnName:"ultimo_nro",filterType:"text"}],
    "foreignTables":[{"tableName":"cnf_tipo_comprobante","idValue":"cnf_tipo_comprobante_id"},
                     {"tableName":"cnf_local","idValue":"cnf_local_id"}],
    "columnsForm":[{tableName: "cnf_num_comprobante", "columnName":"serie","type":"input"},
                   {tableName: "cnf_num_comprobante", "columnName":"ultimo_nro","type":"input"},
                   {tableName: "cnf_tipo_comprobante", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_tipo_comprobante_id"},
                   {tableName: "cnf_local", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_local_id"}
           ],
    //filters sería para filtros adicionales
    "filters":{"cnf_num_comprobante.codigo":"","cnf_num_comprobante.descripcion":""},
    "orders":["cnf_num_comprobante_id desc"]
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

