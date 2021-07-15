import { Widget } from '@wuespace/telestion-client-types';
import { Widget as WidgetRenderer } from './widget';
import { WidgetProps } from './model';

export const widget: Widget<WidgetProps> = {
	name: 'gpsDetailsWidget',
	title: 'GPS Details Widget',
	version: '0.1.0',
	Widget: WidgetRenderer
};

export type { WidgetProps as GPSDetailsWidgetProps } from './model';
