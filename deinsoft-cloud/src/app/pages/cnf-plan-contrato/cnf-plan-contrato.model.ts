import { CnfEmpresa } from '../../business/model/cnf-empresa.model';


export class CnfPlanContrato {
	id: number = 0;
	nombre: string = "";
	precio: number = 0;
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	token?: string = "";
};
