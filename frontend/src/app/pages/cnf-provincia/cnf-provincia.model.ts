import { CnfRegion } from "../cnf-region/cnf-region.model";

export class CnfProvincia {
	id: int = "";
	nombre: string = "";
	cnfRegion: CnfRegion = new CnfRegion();
	token?: string = "";
};
