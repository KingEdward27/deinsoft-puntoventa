import { CnfPais } from "../cnf-pais/cnf-pais.model";

export class CnfRegion {
	id: int = "";
	nombre: string = "";
	cnfPais: CnfPais = new CnfPais();
	token?: string = "";
};
