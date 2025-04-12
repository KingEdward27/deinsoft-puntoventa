import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import Chart, { ArcElement, DoughnutController, Legend, Tooltip } from 'chart.js/auto';
import { ActContratoService } from '../act-contrato/act-contrato.service';
import { AppService } from '@services/app.service';
import { ActComprobanteService } from '@pages/act-comprobante/act-comprobante.service';
import { ParamBean } from '../reports/base/ParamReports';
import { CommonReportFormComponent, MyBaseComponentDependences } from '@pages/reports/base/common-report.component';
import { NgbDateAdapter, NgbDateParserFormatter } from '@ng-bootstrap/ng-bootstrap';
import { CustomAdapter, CustomDateParserFormatter } from '../../base/util/CustomDate';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss'],
    providers: [
        { provide: NgbDateAdapter, useClass: CustomAdapter },
        { provide: NgbDateParserFormatter, useClass: CustomDateParserFormatter },
        MyBaseComponentDependences
    ]
})
export class DashboardComponent extends CommonReportFormComponent implements OnInit {

    public chart: any;
    public chart2: any;
    listData: any = {"totalClientes":"0"};
    // map.put("totalClientes", listClientes.size());
    //     map.put("totalVentas", listVentas.size());
    //     map.put("totalMontoVentas", sumVentas);
    //     map.put("totalCompras", listCompras.size());
    //     map.put("totalMontoCompras", sumCompras);
    //     map.put("totalCaja", totalGanancia);
    //     map.put("moneda", moneda);
    //     map.put("listTopProducts", listTopProducts);
    //     map.put("listTopClients", listTopClients);
    cantidadContratos: any
    cantidadDeudores: any
    ratioEndeudamiento: any
    totalMontoPagos: any
    empresaPrincipal: any;
    model: ParamBean = new ParamBean();
    constructor(public deps: MyBaseComponentDependences) {
        super(deps);
    }

    async ngOnInit() {
        if (this.chart) {
            this.chart.destroy();
          }
          
        this.empresaPrincipal = await this.deps.appService.getProfile().empresaPrincipal;
        super.ngOnInit();
        this.selectThisMonth();
        this.getListData();
    }
    selectThisMonth() {
        let year = new Date().getFullYear();
        let month = new Date().getMonth()+1 ;
        let day = new Date(year, month, 1).getDate();

        // this.model.fechaDesde = this.deps.dateAdapter
        //     .toModel({ year: year, month: month, day: day });
    }

    getListData() {
        console.log(this.empresaPrincipal.perfilEmpresa);
        //this.param.cnf
        if (this.empresaPrincipal.perfilEmpresa == 1) {
            this.deps.actComprobanteService.getDashboard(this.model).subscribe(data => {
                this.listData = data;
                console.log(data);
                // this.chart?.destroy()
                this.chart2?.destroy()

                // const canvas = document.getElementById('MyChart') as HTMLCanvasElement;
                // console.log(canvas);
                // if (canvas) {
                    
                    //const ctx = canvas.getContext('2d');
                    setTimeout(() => {
                        // const ctx = this.canvasRef.nativeElement.getContext('2d');
                        // console.log(ctx);
                        // if (ctx) {
                    
                            const labels = this.listData.listTopProducts.map(item => item.nombre);
                            const precios = this.listData.listTopProducts.map(item => item.precio);
                            this.chart = new Chart("MyChart", {
                                type: 'doughnut',
                                data: {
                                labels: labels,
                                datasets: [
                                    {
                                    label: 'Precios',
                                    data: precios,
                                    backgroundColor: [
                                        '#FF6384',
                                        '#36A2EB',
                                        '#FFCE56',
                                        '#4BC0C0'
                                    ],
                                    borderWidth: 1
                                    }
                                ]
                                },
                                options: {
                                responsive: true,
                                aspectRatio: 4,
                                plugins: {
                                    legend: {
                                    position: 'right',
                                    labels: {
                                        generateLabels: (chart) => {
                                        const dataset = chart.data.datasets[0];
                                        return chart.data.labels!.map((label, i) => {
                                            const value = dataset.data[i] as number;
                                            const backgroundColor = dataset.backgroundColor?.[i] as string;
                        
                                            return {
                                            text: `${label}: S/. ${value}`,
                                            fillStyle: backgroundColor,
                                            strokeStyle: backgroundColor,
                                            index: i
                                            };
                                        });
                                        }
                                    }
                                    },
                                    tooltip: {
                                        callbacks: {
                                            label: (tooltipItem) => {
                                            const label = tooltipItem.label;
                                            const value = tooltipItem.raw;
                                            return `${label}: S/. ${value}`;
                                            }
                                        }
                                    }
                                }
                                }
                            });
                        // }
                    });
                    
                // }
                

                // const NUMBER_CFG = {count: this.listData.listTopProducts.lenght, min: 0, max: 2000};
                // this.chart = new Chart("MyChart", {
                //     type: 'polarArea', //this denotes tha type of chart

                //     data: {// values on X-Axis
                //         // labels: ['Enero', 'Febrero', 'Marzo', 'Abril',
                //         //     'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre'],
                //         datasets: this.listData.listTopProducts,
                //         // datasets: [
                //         //     {
                //         //         label: "Total adeudo",
                //         //         data: ['467', '576', '572', '79', '92',
                //         //             '574', '573', '576', '574', '573', '576', '576'],
                //         //         backgroundColor: 'blue'
                //         //     },
                //         //     {
                //         //         label: "Total recaudo",
                //         //         data: ['542', '542', '536', '327', '17',
                //         //             '0.00', '538', '541', '17',
                //         //             '0.00', '538', '541'],
                //         //         backgroundColor: 'limegreen'
                //         //     }
                //         // ]
                //     },
                //     options: {
                //         aspectRatio: 2,
                //         plugins: {
                //             legend: {
                //                 position: 'bottom'
                //             },
                //             title: {
                //                 display: true,
                //                 text: 'Cumplimiento de pago de contratos'
                //             }
                //         },
                //         scales: {
                //             y: {
                //                 max: 20000,
                //                 min: 0,
                //                 ticks: {
                //                     stepSize: 0.5
                //                 }
                //             }
                //         }
                //     }

                // });
            })
        }

        if (this.empresaPrincipal.perfilEmpresa == 2) {
            console.log(this.listData);
            
            let cnfEmpresa = this.deps.appService.getProfile().profile.split("|")[1];
            this.deps.actContratoService.getDashboard(cnfEmpresa).subscribe(data => {
                console.log(data);
                this.listData = data;
                this.cantidadContratos = data.cantidadContratos;
                this.cantidadDeudores = data.cantidadDeudores;
                this.ratioEndeudamiento = data.ratioEndeudamiento;
                this.totalMontoPagos = data.totalMontoPagos;
            })
            // this.chart?.destroy()
            // this.chart2?.destroy()
            // const NUMBER_CFG = {count: 12, min: 0, max: 2000};
            // this.chart = new Chart("MyChart", {
            //     type: 'bar', //this denotes tha type of chart

            //     data: {// values on X-Axis
            //         labels: ['Enero', 'Febrero', 'Marzo', 'Abril',
            //             'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            //         datasets: [
            //             {
            //                 label: "Total adeudo",
            //                 data: ['467', '576', '572', '79', '92',
            //                     '574', '573', '576', '574', '573', '576', '576'],
            //                 backgroundColor: 'blue'
            //             },
            //             {
            //                 label: "Total recaudo",
            //                 data: ['542', '542', '536', '327', '17',
            //                     '0.00', '538', '541', '17',
            //                     '0.00', '538', '541'],
            //                 backgroundColor: 'limegreen'
            //             }
            //         ]
            //     },
            //     options: {
            //         aspectRatio: 2,
            //         plugins: {
            //             legend: {
            //                 position: 'bottom'
            //             },
            //             title: {
            //                 display: true,
            //                 text: 'Cumplimiento de pago de contratos'
            //             }
            //         },
            //         scales: {
            //             y: {
            //                 max: 20000,
            //                 min: 0,
            //                 ticks: {
            //                     stepSize: 0.5
            //                 }
            //             }
            //         }
            //     }

            // });
            
            // this.chart2 = new Chart("MyChart2", {
            //     type: 'line', //this denotes tha type of chart
            //     data: {// values on X-Axis
            //         labels: ['Enero', 'Febrero', 'Marzo', 'Abril',
            //             'Mayo', 'Junio', 'Julio', 'Agosto', 'Setiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            //         datasets: [
            //             {
            //                 label: "Sales",
            //                 data: ['467', '576', '572', '79', '92',
            //                     '574', '573', '576', '574', '573', '576', '576'],
            //                 backgroundColor: 'blue',
            //                 stack: 'combined',
            //                 type: 'bar'
            //             },
            //             {
            //                 label: "Profit",
            //                 data: ['542', '542', '536', '327', '17',
            //                     '0.00', '538', '541', '17',
            //                     '0.00', '538', '541'],
            //                 backgroundColor: 'limegreen',
            //                 stack: 'combined'
            //             }
            //         ]
            //     },
            //     options: {
            //         aspectRatio: 2,
            //         plugins: {
            //             legend: {
            //                 position: 'bottom'
            //             },
            //             title: {
            //                 display: true,
            //                 text: 'Chart.js Line Chart'
            //             }
            //         }, scales: {
            //             y: {
            //                 stacked: true
            //             }
            //         }
            //     }

            // });
        }
    }

}
