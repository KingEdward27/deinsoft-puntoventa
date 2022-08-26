import { ActPagoProgramacion } from "../act-pago-programacion/act-pago-programacion.model";
import * as dayjs from 'dayjs';

export class ActPago {
	id: int = "";
	fecha?: dayjs.Dayjs | null;
	monto!: number;
	actPagoProgramacion: ActPagoProgramacion = new ActPagoProgramacion();
	token?: string = "";
};
