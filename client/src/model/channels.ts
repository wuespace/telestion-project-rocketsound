import { JsonSerializable } from '@wuespace/telestion-client-types';

/**
 * Channel to save data to the database
 */
export const MONGODB_SAVE =
	'de.wuespace.telestion.services.database.MongoDatabaseService/in#save';

/**
 * Channel where the database publishes the recently saved data
 */
export const MONGODB_NEW =
	'de.wuespace.telestion.services.database.MongoDatabaseService/out#save';

export const MONGODB_FIND =
	'de.wuespace.telestion.services.database.MongoDatabaseService/in#find';

export const NineDOF =
	MONGODB_NEW + '/de.wuespace.telestion.project.rocketsound.messages.base.NineDofData';
export const FlightState =
	MONGODB_NEW + '/de.wuespace.telestion.project.rocketsound.messages.base.FlightState';
export const GpsData =
	MONGODB_NEW + '/de.wuespace.telestion.project.rocketsound.messages.base.GpsData';
export const Amplitude =
	MONGODB_NEW + '/de.wuespace.telestion.project.rocketsound.messages.base.Amplitude';
export const Spectrum =
	MONGODB_NEW + '/de.wuespace.telestion.project.rocketsound.messages.sound.Spectrum';
export const BaroData =
	MONGODB_NEW + '/de.wuespace.telestion.project.rocketsound.messages.base.BaroData';
export const Velocity =
	MONGODB_NEW + '/de.wuespace.telestion.project.rocketsound.messages.base.Velocity';

export interface DataMessage<T extends JsonSerializable, C extends string>
	extends Record<string, JsonSerializable> {
	dataType: C;
	result: T[];
	className: 'de.wuespace.telestion.services.database.DbResponse';
}
