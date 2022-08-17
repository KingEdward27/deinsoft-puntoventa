import { CnfEmpresa } from "../cnf-empresa/cnf-empresa.model";

export class CnfFormaPago {
	id: int = "";
	nombre: string = "";
	flagEstado: string = "";
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	token?: string = "";
};
