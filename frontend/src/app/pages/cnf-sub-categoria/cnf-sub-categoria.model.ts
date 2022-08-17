import { CnfCategoria } from "../cnf-categoria/cnf-categoria.model";
import { CnfEmpresa } from "../cnf-empresa/cnf-empresa.model";

export class CnfSubCategoria {
	id: int = "";
	nombre: string = "";
	flagEstado: string = "";
	cnfCategoria: CnfCategoria = new CnfCategoria();
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	token?: string = "";
};
