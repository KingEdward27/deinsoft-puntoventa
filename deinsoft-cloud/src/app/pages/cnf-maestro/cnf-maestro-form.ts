import { GenericListComponent } from '@/base/components/generic-list/generic-list.component';
import { UniqueKey } from '@/base/components/model/UniqueKey';
import { CommonService } from '@/base/services/common.service';
import { HttpClient } from '@angular/common/http';
import { Component,OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { AppService } from '@services/app.service';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-cnf-maestro-form',
  template: '<app-generic-form2 [props] = prop [onChanges] = "changes" [route] = "this.prop.route" [onPreLoadForm]="wa" [onPreSave]="onPreSave"></app-generic-form2>'
})

export class CnfMaestroForm2Component implements OnInit{
  //baseEndpoint = environment.apiUrl + '/get-all-cnf-org';
  prop ={
    "tableName": "cnf_maestro",
    "title": "Clientes y Proveedores",
    "foreignTables":[{"tableName":"cnf_distrito","idValue":"cnf_distrito_id"},
                {"tableName":"cnf_tipo_documento","idValue":"cnf_tipo_documento_id"}],
    "columnsForm":[{tableName: "cnf_tipo_documento", "columnName":"nombre","type":"select",loadState : 1,relatedBy:"cnf_tipo_documento_id", required:true},
                    {tableName: "cnf_maestro", columnName:"nro_doc",type:"input"},
                    {tableName: "cnf_maestro", columnName:"apellido_paterno",type:"input", hidden : false, value: ""},
                    {tableName: "cnf_maestro", columnName:"apellido_materno",type:"input", hidden : false, value: ""},
                    {tableName: "cnf_maestro",columnName:"nombres",type:"input", required:true, hidden : false, value: ""},
                    {tableName: "cnf_maestro",columnName:"razon_social",type:"input", required:true , hidden : false, value: ""},
                    {tableName: "cnf_maestro",columnName:"direccion",type:"input"},
                    {tableName: "cnf_maestro",columnName:"correo",type:"input"},
                    {tableName: "cnf_maestro",columnName:"telefono",type:"input"},
                   {tableName: "cnf_region", "columnName":"nombre","type":"select",loadState : 1,loadFor:"cnf_distrito_id",load:{tableName:"cnf_provincia",loadBy:"cnf_region_id"}},
                   {tableName: "cnf_provincia", "columnName":"nombre","type":"select",loadState : 0,loadFor:"cnf_distrito_id",load:{tableName:"cnf_distrito",loadBy:"cnf_provincia_id"}},
                   {tableName: "cnf_distrito", "columnName":"nombre","type":"select",loadState : 0,loadFor:"cnf_distrito_id",relatedBy:"cnf_distrito_id"}
           ],
    //filters sería para filtros adicionales
    "conditions":[],
    "orders":["nombres","direccion"],
    "preSave" : [],
    "functions": [],
    "route": "/maestro"
  }

  changes: any;
  constructor(private utilServices: UtilService,private httpClients:HttpClient,
    public routers: Router,public _commonService:CommonService,private appService:AppService) { 
    //super(utilServices,httpClients,routers,_commonService);
  }
  wa  = (): boolean => {
    console.log("XD");
    this.otroLog();
    let selectValue = (<HTMLInputElement>document.getElementById("cnf_tipo_documento.cnf_tipo_documento_id")).value;
    if (selectValue == "6") {
      this.prop.columnsForm[2].hidden = true;
      this.prop.columnsForm[3].hidden = true;
      this.prop.columnsForm[4].hidden = true;
    }

    return true;
  }
  
  w2 () {
    console.log("w2");
    this.otroLog();
    return true;
    
  }
  onPreSave = (): boolean => {
    let selectValue = (<HTMLInputElement>document.getElementById("cnf_tipo_documento.cnf_tipo_documento_id")).value;
    if (selectValue != "3") {
      
      let fullName = ""
      let apellidoPaterno = (<HTMLInputElement>document.getElementById("cnf_maestro.apellido_paterno")).value;
      let apellidoMaterno = (<HTMLInputElement>document.getElementById("cnf_maestro.apellido_materno")).value;
      let nombres = (<HTMLInputElement>document.getElementById("cnf_maestro.nombres")).value;
      let nroDoc = (<HTMLInputElement>document.getElementById("cnf_maestro.nro_doc")).value;

      if (!nroDoc) {
        (<HTMLInputElement>document.getElementById("cnf_maestro.nro_doc")).value = "00000000"
        this.prop.columnsForm[1].value = "00000000";
      }
      if (apellidoPaterno) {
        fullName = fullName + " " + apellidoPaterno
      }
      if (apellidoMaterno) {
        fullName = fullName + " " + apellidoMaterno
      }
      if (nombres) {
        fullName = fullName + " " + nombres
      }
      console.log(fullName);
      
      this.prop.columnsForm[5].value = fullName.trim();
      //(<HTMLInputElement>document.getElementById("cnf_maestro.razon_social")).value = fullName.trim()
    } else {
      //this.prop.columnsForm[4].value = this.prop.columnsForm[5].value
    }
    if (!this.prop.columnsForm[5].value) {
      this.prop.columnsForm[5].required = true;
    }
    // let uk = new UniqueKey();
    // uk.tableName = "cnf_maestro"
    // uk.columns = ["nro_doc"]
    // this._commonService.validateUnique(uk);
    // let wa = await this._commonService.validateUnique(uk)
    // .catch(err => {
    //     if (err.status === 422) {
    //       console.log("error..");
          
    //       this.error = err.error;
    //     }
    // });
    return true;
  }
  onChangeTipoDoc = () => {
    let selectValue = (<HTMLInputElement>document.getElementById("cnf_tipo_documento.cnf_tipo_documento_id")).value;
    
    if (selectValue == "3") {
      this.prop.columnsForm[2].hidden = true;
      this.prop.columnsForm[3].hidden = true;
      this.prop.columnsForm[4].hidden = true;
      this.prop.columnsForm[5].hidden = false;
    } else {
      this.prop.columnsForm[2].hidden = false;
      this.prop.columnsForm[3].hidden = false;
      this.prop.columnsForm[4].hidden = false;
      this.prop.columnsForm[5].hidden = true;
    }
    return true;
  }
  ngOnInit(): void {
    //this.prop.preSave =  this.wa();
    //this._commonService.baseEndpoint = this.baseEndpoint;
    let cnfEmpresa = this.appService.getProfile().profile.split("|")[1];
    console.log(cnfEmpresa);
    
    this.prop.preSave.push({columnForm:"cnf_empresa_id",value:cnfEmpresa});
    this.prop.conditions.push({"columnName":"cnf_maestro.cnf_empresa_id","value":cnfEmpresa});
    //this.prop.preSave.push({columnForm:"razon_social",value:"columnsForm.apellido_paterno +' '+columnsForm.apellido_materno +' '+columnsForm.nombres"})
    this.prop.functions.push({func : this.wa});
    
    console.log(this.prop);
    
    
    this.changes = []
    this.changes.push({columnName: "cnf_tipo_documento.cnf_tipo_documento_id", function:this.onChangeTipoDoc})
    $('#cnf_tipo_documento.cnf_tipo_documento_id').change(function(){
      alert($(this).val());
    })
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

