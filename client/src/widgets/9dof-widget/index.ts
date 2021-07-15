import { Widget } from '@wuespace/telestion-client-types';
import { Widget as WidgetRenderer } from './widget';
import { WidgetProps } from './model';

export const widget: Widget<WidgetProps> = {
	name: '9dofWidget',
	title: '9-DOF Widget',
	version: '0.1.0',
	Widget: WidgetRenderer
};

export type { WidgetProps as NineDofWidgetProps } from './model';
