import { Widget } from '@wuespace/telestion-client-types';
import { Widget as WidgetRenderer } from './widget';

export const widget: Widget = {
	name: 'placeholderWidget',
	title: 'Placeholder Widget',
	version: '0.1.0',
	Widget: WidgetRenderer
};
