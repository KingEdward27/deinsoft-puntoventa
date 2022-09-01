import * as dayjs from 'dayjs';
import { ActPagoProgramacion } from './act-pago-programacion.model';

export class ActPago {
	id: number = 0;
	fecha?: dayjs.Dayjs | null;
	monto!: number;
	actPagoProgramacion: ActPagoProgramacion = new ActPagoProgramacion();
	token?: string = "";
};
