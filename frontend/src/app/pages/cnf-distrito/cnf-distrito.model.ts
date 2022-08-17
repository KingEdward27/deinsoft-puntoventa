import { CnfProvincia } from "../cnf-provincia/cnf-provincia.model";

export class CnfDistrito {
	id: int = "";
	nombre: string = "";
	value: string = "";
	cnfProvincia: CnfProvincia = new CnfProvincia();
	token?: string = "";
};
