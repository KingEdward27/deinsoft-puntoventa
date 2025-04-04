import { CnfLocal } from "./cnf-local.model";

export class ActCaja {
	id: number = 0;
	nombre: string = "";
	estado: string = "";
	token?: string = "";
	cnfLocal:CnfLocal = new CnfLocal();
};
