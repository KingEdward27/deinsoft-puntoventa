import { CnfLocal } from "../cnf-local/cnf-local.model";

export class InvAlmacen {
	id: int = "";
	nombre: string = "";
	flagEstado: string = "";
	cnfLocal: CnfLocal = new CnfLocal();
	token?: string = "";
};
