import { CnfEmpresa } from "../cnf-empresa/cnf-empresa.model";

export class CnfMarca {
	id: int = "";
	nombre: string = "";
	flagEstado: string = "";
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	token?: string = "";
};
