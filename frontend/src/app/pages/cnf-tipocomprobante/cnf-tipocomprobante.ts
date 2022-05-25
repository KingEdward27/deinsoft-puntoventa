import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { GenericListComponent } from 'src/app/base/components/generic-list/generic-list.component';
import { UtilService } from 'src/app/services/util.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-cnf-tipocomprobante',
  templateUrl: '../../base/components/generic-list/generic-list.component.html'
})

export class CnfTipoComprobanteComponent extends GenericListComponent implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_tipo_comprobante",
    "title": "Tipo de comprobante",
    "columnsList":[{tableName: "cnf_tipo_comprobante",columnName:"nombre",filterType:"text"},
                   {tableName: "cnf_tipo_comprobante",columnName:"codigo",filterType:"text"},
                   {tableName: "cnf_tipo_comprobante",columnName:"codigo_sunat",filterType:"text"}],
    "columnsForm":[{tableName: "cnf_tipo_comprobante", "columnName":"nombre","type":"input"},
                   {tableName: "cnf_tipo_comprobante", "columnName":"codigo","type":"input"},
                   {tableName: "cnf_tipo_comprobante", "columnName":"codigo_sunat","type":"input"}
           ],
    //filters sería para filtros adicionales
    "filters":{"cnf_tipo_comprobante.codigo":"","cnf_tipo_comprobante.descripcion":""},
    "orders":["cnf_tipo_comprobante_id desc"]
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

