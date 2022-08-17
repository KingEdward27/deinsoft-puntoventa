import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Location } from '@angular/common';
import { NgbDateAdapter, NgbDateParserFormatter, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '@/base/util/CustomDate';
import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { CommonService } from '@/base/services/common.service';
import { GenericMasterDetailFormComponent } from '@/base/components/generic-master-detail-form/generic-master-detail-form.component';
import { UpdateParam } from '@/base/components/model/UpdateParam';
import { GenericList2Component } from '@/base/components/generic-list2/generic-list2.component';
import { UtilService } from '@services/util.service';
import { AppService } from '@services/app.service';
import { ActComprobanteService } from '@pages/act-comprobante/act-comprobante.service';


@Component({
  selector: 'rpt-act-venta-form',
  templateUrl: '../../../base/components/generic-list2/generic-list2.component.html',
  providers: [
    { provide: NgbDateAdapter, useClass: CustomAdapter },
    { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter }
  ]

})
export class RptActVentaFormComponent extends GenericList2Component implements OnInit {
  ticket = "";
  prop = {
    "tableName": "act_comprobante",
    "title": "Reporte Ventas",
    "columnsList": [{ tableName: "act_comprobante", columnName: "fecha", filterType: "text" },
    { tableName: "cnf_local", columnName: "nombre", filterType: "text" },
    { tableName: "cnf_tipo_comprobante", columnName: "nombre", filterType: "none" },
    { tableName: "act_comprobante", columnName: "serie", filterType: "text" },
    { tableName: "act_comprobante", columnName: "numero", filterType: "text", type: "number" },
    { tableName: "act_comprobante", columnName: "total", filterType: "text", type: "number" },
    { tableName: "act_comprobante", columnName: "observacion", filterType: "text" },
    { tableName: "cnf_maestro", columnName: "nombres", filterType: "text",alias:"" },
    { tableName: "cnf_forma_pago", columnName: "nombre", filterType: "none" },
    { tableName: "cnf_moneda", columnName: "codigo", filterType: "none" },
    { tableName: "inv_almacen", columnName: "nombre", filterType: "none" },
    { tableName: "act_comprobante", columnName: "envio_pse_mensaje", filterType: "none" }
    ],
    columnsListParams: [],

    "foreignTables": [{ "tableName": "cnf_tipo_comprobante", "idValue": "cnf_tipo_comprobante_id" },
    { "tableName": "cnf_maestro", "idValue": "cnf_maestro_id" },
    { "tableName": "cnf_forma_pago", "idValue": "cnf_forma_pago_id" },
    { "tableName": "cnf_moneda", "idValue": "cnf_moneda_id" },
    { "tableName": "cnf_local", "idValue": "cnf_local_id" },
    { "tableName": "inv_almacen", "idValue": "inv_almacen_id" }],
    "actions": [],
    message :{
      title : {message :"Seleccione la opción de impresión",
              params:"columnsForm->act_comprobante.serie,columnsForm->act_comprobante.numero"},
      icon :"success",
      rows:[
          {columns:[{type:"a",id:"ticket" ,value:"Ticket",icon:"fa fa-arrow-circle-left",action:{name:this.ticketChild}},
                    {type:"a",id:"a4" ,value:"A4",icon:"fa fa-arrow-circle-left",action:{name:this.a4}}]}
                ]
      },
    "orders":["act_comprobante_id desc"]   
  }
  igv: number = 0;
  subtotal: number = 0;
  total: number = 0;
  constructor(private utilServices: UtilService, private httpClientChild: HttpClient, 
    private routers: Router,
    private _location0: Location, public _commonService: CommonService,
    private appService:AppService,
    private actComprobanteService:ActComprobanteService) {
    super(utilServices, httpClientChild, routers,_commonService);
  }
  ngOnInit(): void {
    super.baseEndpoint = this.baseEndpoint;
    this.prop.columnsList[7].tableName = ""
    this.prop.columnsList[7].columnName = "concat(cnf_maestro.apellido_paterno,' ',cnf_maestro.apellido_materno,' ',cnf_maestro.nombres)"
    this.prop.columnsList[7].alias = "cnf_maestro.nombre_completo"
    
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    this.prop.columnsListParams.push({"columnName":"act_comprobante.flag_isventa","value":1});
    this.prop.columnsListParams.push({"columnName":"cnf_local.cnf_empresa_id","value":cnfEmpresa});
    super.properties = this.prop;
    this.properties.actions.push({ name: this.print, icon: "fas fa-print",value:"Imprimir" })
    this.properties.actions.push({ name: this.sendApi, icon: "fas fa-cloud-upload-alt",value:"Enviar Comprobante" })
    this.properties.actions.push({ name: this.print, icon: "fas fa-ban" ,value:"Anular"})

    this.properties.message.rows[0].columns[0].action = { name: this.ticketChild }
    this.properties.message.rows[0].columns[1].action = { name: this.a4 }

    localStorage.setItem("properties", JSON.stringify(this.properties));
    super.ngOnInit();
    // $(document).on('click', '#ticket', function() {
    //   this.ticketChild();
    // });
  }
  loadData() {

  }
  getProductDetail(properties: any, id: any) {
    let forSearch = {
      select: "cnf_producto.cnf_producto_id,cnf_producto.nombre,'','',cnf_producto.precio,''",
      from: "cnf_producto",
      where: { "cnf_producto_id": id }
    };
    properties.details[0].listData.onchange = forSearch;
    properties.details.forEach((element: any) => {

    });
  }
  ticketChild(id: any, param: CommonService) {
    let myMap = new Map();
    myMap.set("id", id);
    myMap.set("tipo", 2);
    let mp = new UpdateParam();


    const convMapDetail: any = {};
    myMap.forEach((val: string, key: string) => {
      convMapDetail[key] = val;
    });
    console.log(convMapDetail);

    mp.map = convMapDetail;
    param.updateParam = mp;
    //this.properties.actions.push({ name: this.print, param: 0, icon: "fas fa-print" })
    param.genericPostRequest("/api/business/getpdflocal", mp, 'blob').subscribe(data => {
      console.log(data);
      if (data.type != 'application/json') {
        var contentType = 'application/pdf';
        var extension = "pdf";

        if (data.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
          contentType = data.type;
          extension = "xlsx";
        }
        const blob = new Blob([data], { type: contentType });
        super.generateAttachment(blob, extension);
      }

    });
  }
  a4(id: any, param: CommonService) {
    let myMap = new Map();
    myMap.set("id", id);
    myMap.set("tipo", 1);
    let mp = new UpdateParam();


    const convMapDetail: any = {};
    myMap.forEach((val: string, key: string) => {
      convMapDetail[key] = val;
    });
    console.log(convMapDetail);

    mp.map = convMapDetail;
    param.genericPostRequest("/api/business/getpdflocal", mp, 'blob').subscribe(data => {
      console.log(data);
      if (data.type != 'application/json') {
        var contentType = 'application/pdf';
        var extension = "pdf";

        if (data.type == "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
          contentType = data.type;
          extension = "xlsx";
        }
        const blob = new Blob([data], { type: contentType });
        super.generateAttachment(blob, extension);
      }

    });
  }
  print(prope: any, item: any,utilService: UtilService) {
    console.log("print...", prope, item,utilService);
    
    let id = item[0]
    prope.message.rows[0].columns[0].param = id;
    prope.message.rows[0].columns[1].param = id;
    utilService.msgConfirmSaveWithButtons(
      prope.message.title.message,
      undefined,
      prope.message.rows)
  }
  sendApi(prope: any, item: any,utilService: UtilService) {
    let id = item[0]
    this.actComprobanteService.sendApi("act_comprobante",id).subscribe(data =>{
      console.log(data);
      
    })
    console.log("print...", prope, item,utilService);
    
    
    // prope.message.rows[0].columns[0].param = id;
    // prope.message.rows[0].columns[1].param = id;
    // utilService.msgConfirmSaveWithButtons(
    //   prope.message.title.message,
    //   undefined,
    //   prope.message.rows)
  }
  // UpdateState(prope: any, item: any,utilService: UtilService) {
  //   let id = item[0]
  //   utilService.confirmOperation(null).then((result) => {
  //     if (result) {
  //       this.actComprobanteDetalleService.delete(e.id.toString()).subscribe(() => {
  //         this.utilService.msgOkDelete();
  //         this.loadData();
  //       }, err => {
  //         if (err.status === 500 && err.error.trace.includes("DataIntegrityViolationException")) {
  //           this.utilService.msgProblemDelete();
  //         }
  //       });
  //     }

  //   });
  //   console.log("print...", prope, item,utilService);
    
    
  // }
}
