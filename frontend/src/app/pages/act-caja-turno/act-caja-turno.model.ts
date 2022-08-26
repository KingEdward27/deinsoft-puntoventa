import { SegUsuario } from "../seg-usuario/seg-usuario.model";

export class ActCajaTurno {
	id: int = "";
	fechaApertura!: localdatetime;
	fechaCierre!: localdatetime;
	estado: string = "";
	segUsuario: SegUsuario = new SegUsuario();
	token?: string = "";
};
