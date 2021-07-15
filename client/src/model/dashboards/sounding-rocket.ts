import { Dashboard } from '@wuespace/telestion-client-types';
import { amplitudeGraph, velocityGraph } from './widget-props';

export const soundingRocketDashboard: Dashboard = {
	title: 'Sounding Rocket',
	columns: 12,
	rows: 12,
	widgets: [
		{
			id: 'sounding-rocket-0',
			widgetName: 'stateWidget',
			width: 2,
			height: 4,
			initialProps: {
				title: 'stateWidget'
			}
		},
		{
			id: 'sounding-rocket-1',
			widgetName: 'graphWidget',
			width: 10,
			height: 4,
			initialProps: {
				...amplitudeGraph,
				title: 'Amplitude',
				maxDataSamples: 100,
				isCartesianGrid: true
			}
		},
		{
			id: 'sounding-rocket-2',
			widgetName: 'placeholderWidget',
			width: 2,
			height: 8,
			initialProps: {
				title: 'Placeholder'
			}
		},
		{
			id: 'sounding-rocket-3',
			widgetName: 'spectrogramWidget',
			width: 10,
			height: 4,
			initialProps: {
				title: 'Spectrogram Widget'
			}
		},
		{
			id: 'sounding-rocket-4',
			widgetName: 'graphWidget',
			width: 10,
			height: 4,
			initialProps: {
				...velocityGraph,
				title: 'Velocity',
				maxDataSamples: 100,
				isCartesianGrid: true
			}
		}
	]
};
