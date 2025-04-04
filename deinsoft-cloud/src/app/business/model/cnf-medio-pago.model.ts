import { CnfEmpresa } from "./cnf-empresa.model";

export class CnfMedioPago {
	id: number = 0;
	nombre: string = "";
	flagEstado: string = "";
	amtToPay:number=0;
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
};
