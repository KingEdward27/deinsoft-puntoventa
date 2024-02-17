import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { CnfTipoSistema } from '../cnf-tipo-sistema.model';
import { CnfMenuTipoSistema } from '../../cnf-menu-tipo-sistema/cnf-menu-tipo-sistema.model';
import { CnfMenuTipoSistemaService } from '../../cnf-menu-tipo-sistema/cnf-menu-tipo-sistema.service';
import {Location} from '@angular/common';
import Swal from 'sweetalert2';
import { UtilService } from 'src/app/services/util/util.service';
import { SegMenu } from '../../seg-menu/seg-menu.model';
import { SegMenuService } from '../../seg-menu/seg-menu.service';
@Component({
  selector: 'app-add-new-cnf-menu-tipo-sistema',
  templateUrl: './add-new-cnf-menu-tipo-sistema.component.html'

})
export class AddNewCnfMenuTipoSistemaComponent implements OnInit {
  public selectedOptionCnfMenuTipoSistema:any;
  public data!: Array<any>;
  id: string = "";
  error: any;
  model:CnfMenuTipoSistema = new CnfMenuTipoSistema();
  chargingsb:boolean = true;
  isDataLoaded :Boolean = false;
  cnfTipoSistema:CnfTipoSistema = new CnfTipoSistema();
  listCnfMenuTipoSistema:CnfMenuTipoSistema[] = [];
  selectDefaultSegMenu: any = { id: 0, name: "- Seleccione -" };   segMenu: SegMenu = new SegMenu();
  loadingSegMenu: boolean = false;
  listSegMenu:any;
  constructor(private cnfMenuTipoSistemaService : CnfMenuTipoSistemaService,private router: Router,private _location: Location,private route: ActivatedRoute,private segMenuService: SegMenuService,private utilService:UtilService ) {}
  
  ngOnInit(): void {
    this.loadData();
  }
  loadData(){
    this.getListSegMenu();
    return this.route.paramMap.subscribe(params => {
      this.model.cnfTipoSistema.id = Number(params.get('idParent'));
      this.id = params.get('id')!;
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.cnfMenuTipoSistemaService.getData(this.id).subscribe(data => {
          this.model = data;
          console.log(this.model);
          this.isDataLoaded = true;
        });
      }
      
    })
  }
  back(){
    this._location.back();
  }
  public save(): void {
    this.cnfMenuTipoSistemaService.save(this.model).subscribe(m => {
      console.log(m);
      this.utilService.msgOkSave();
      this._location.back();
    }, err => {
      if(err.status === 400){
        this.error = err.error;
        console.log(this.error);
      }
    });
  }
getListSegMenu() {
    this.loadingSegMenu = true;
    console.log(this.chargingsb);
    return this.segMenuService.getAllDataCombo().subscribe(data => {
      this.listSegMenu = data;
      this.loadingSegMenu = false;
    })

  }
  compareSegMenu(a1: SegMenu, a2: SegMenu): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  
}

