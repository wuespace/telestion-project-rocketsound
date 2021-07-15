import { Dashboard } from '@wuespace/telestion-client-types';
import { altitudeGraph, pressureGraph, temperatureGraph } from './widget-props';

export const systemStateDashboard: Dashboard = {
	title: 'System State',
	columns: 12,
	rows: 12,
	widgets: [
		{
			id: 'system-state-0',
			widgetName: 'stateWidget',
			width: 4,
			height: 3,
			initialProps: {
				title: 'stateWidget'
			}
		},
		{
			id: 'system-state-1',
			widgetName: 'graphWidget',
			width: 4,
			height: 6,
			initialProps: {
				...pressureGraph,
				title: 'Pressure',
				maxDataSamples: 60,
				isCartesianGrid: true
			}
		},
		{
			id: 'system-state-2',
			widgetName: 'mapWidget',
			width: 4,
			height: 12,
			initialProps: {
				title: 'GPS Data'
			}
		},
		{
			id: 'system-state-3',
			widgetName: '9dof-widget',
			width: 4,
			height: 3,
			initialProps: {
				title: 'Current values'
			}
		},
		{
			id: 'system-state-4',
			widgetName: 'graphWidget',
			width: 4,
			height: 6,
			initialProps: {
				...altitudeGraph,
				title: 'Altitude',
				maxDataSamples: 60,
				isCartesianGrid: true
			}
		},
		{
			id: 'system-state-5',
			widgetName: 'graphWidget',
			width: 4,
			height: 6,
			initialProps: {
				...temperatureGraph,
				title: 'Temperature',
				maxDataSamples: 60,
				isCartesianGrid: true
			}
		}
	]
};
