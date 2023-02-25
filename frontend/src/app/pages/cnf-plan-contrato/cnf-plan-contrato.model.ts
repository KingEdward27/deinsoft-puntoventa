import { CnfEmpresa } from "../cnf-empresa/cnf-empresa.model";

export class CnfPlanContrato {
	id: number = 0;
	nombre: string = "";
	precio!: number;
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	token?: string = "";
};
