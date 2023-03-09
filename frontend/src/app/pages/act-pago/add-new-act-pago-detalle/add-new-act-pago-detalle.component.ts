import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ActPago } from '../act-pago.model';
import { ActPagoDetalle } from '../../act-pago-detalle/act-pago-detalle.model';
import { ActPagoDetalleService } from '../../act-pago-detalle/act-pago-detalle.service';
import {Location} from '@angular/common';
import Swal from 'sweetalert2';
import { UtilService } from 'src/app/services/util/util.service';
import { CustomAdapter, CustomDateParserFormatter } from 'src/app/util/CustomDate';
import { NgbCalendar, NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { ActPagoProgramacion } from '../../act-pago-programacion/act-pago-programacion.model';
import { ActPagoProgramacionService } from '../../act-pago-programacion/act-pago-programacion.service';
@Component({
  selector: 'app-add-new-act-pago-detalle',
  templateUrl: './add-new-act-pago-detalle.component.html'
,
  providers: [
    {provide: NgbDateAdapter, useClass: CustomAdapter},
    {provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter}
  ]
})
export class AddNewActPagoDetalleComponent implements OnInit {
  public selectedOptionActPagoDetalle:any;
  public data!: Array<any>;
  id: string = "";
  error: any;
  model:ActPagoDetalle = new ActPagoDetalle();
  chargingsb:boolean = true;
  isDataLoaded :Boolean = false;
  actPago:ActPago = new ActPago();
  listActPagoDetalle:ActPagoDetalle[] = [];
  selectDefaultActPagoProgramacion: any = { id: 0, name: "- Seleccione -" };   actPagoProgramacion: ActPagoProgramacion = new ActPagoProgramacion();
  loadingActPagoProgramacion: boolean = false;
  listActPagoProgramacion:any;
  constructor(private actPagoDetalleService : ActPagoDetalleService,private router: Router,private _location: Location,private route: ActivatedRoute,private actPagoProgramacionService: ActPagoProgramacionService,private utilService:UtilService ) {}
  
  ngOnInit(): void {
    this.loadData();
  }
  loadData(){
    this.getListActPagoProgramacion();
    return this.route.paramMap.subscribe(params => {
      this.model.actPago.id = Number(params.get('idParent'));
      this.id = params.get('id')!;
      if(!this.id){
        this.isDataLoaded = true;
      }
      if(this.id){
        this.actPagoDetalleService.getData(this.id).subscribe(data => {
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
    this.actPagoDetalleService.save(this.model).subscribe(m => {
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
getListActPagoProgramacion() {
    this.loadingActPagoProgramacion = true;
    console.log(this.chargingsb);
    return this.actPagoProgramacionService.getAllDataCombo().subscribe(data => {
      this.listActPagoProgramacion = data;
      this.loadingActPagoProgramacion = false;
    })

  }
  compareActPagoProgramacion(a1: ActPagoProgramacion, a2: ActPagoProgramacion): boolean {
    if (a1 === undefined && a2 === undefined) {
      return true;
    }

    return (a1 === null || a2 === null || a1 === undefined || a2 === undefined)
      ? false : a1.id === a2.id;
  }
  
}

