import { ActContrato } from "../act-contrato/act-contrato.model";
import { SegUsuario } from "../seg-usuario/seg-usuario.model";
import * as dayjs from 'dayjs';

export class ActContratoCorte {
	id: int = "";
	id: int = "";
	fecha?: dayjs.Dayjs | null;
	flgEstado: string = "";
	segUsusarioId!: number;
	actContrato: ActContrato = new ActContrato();
	segUsuario: SegUsuario = new SegUsuario();
	token?: string = "";
};
