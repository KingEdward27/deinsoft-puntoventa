import { CnfTipoDocumento } from "../cnf-tipo-documento/cnf-tipo-documento.model";
import { CnfEmpresa } from "../cnf-empresa/cnf-empresa.model";
import { CnfDistrito } from "../cnf-distrito/cnf-distrito.model";

export class CnfMaestro {
	id: int = "";
	nroDoc: string = "";
	nombres: string = "";
	apellidoPaterno: string = "";
	apellidoMaterno: string = "";
	razonSocial: string = "";
	direccion: string = "";
	correo: string = "";
	telefono: string = "";
	fechaRegistro!: localdatetime;
	flagEstado: string = "";
	cnfTipoDocumento: CnfTipoDocumento = new CnfTipoDocumento();
	cnfEmpresa: CnfEmpresa = new CnfEmpresa();
	cnfDistrito: CnfDistrito = new CnfDistrito();
	token?: string = "";
};
