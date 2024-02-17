import { CnfEmpresa } from '../../business/model/cnf-empresa.model';


export class CnfPlanContrato {
	id: number = 0;
	nombre: string = "";
	precioInstalacion = 0;
	precio: number = 0;
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	diaVencimiento: number = 0;
	token?: string = "";
};
