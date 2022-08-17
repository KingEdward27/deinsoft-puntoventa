import { CnfEmpresa } from "../cnf-empresa/cnf-empresa.model";

export class CnfLocal {
	id: int = "";
	nombre: string = "";
	direccion: string = "";
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	token?: string = "";
};
