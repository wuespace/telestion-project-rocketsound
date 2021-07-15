import { Dashboard } from '@wuespace/telestion-client-types';
import {
	accLineGraph,
	altitudeGraph,
	amplitudeGraph,
	gyroLineGraph,
	magLineGraph,
	pressureGraph,
	temperatureGraph
} from './widget-props';

export const overviewDashboard: Dashboard = {
	title: 'Overview',
	columns: 12,
	rows: 12,
	widgets: [
		{
			id: 'overview-0',
			widgetName: 'stateWidget',
			width: 2,
			height: 4,
			initialProps: {
				title: 'stateWidget'
			}
		},
		{
			id: 'overview-1',
			widgetName: 'graphWidget',
			width: 2,
			height: 4,
			initialProps: {
				...altitudeGraph,
				title: 'Altitude',
				maxDataSamples: 30,
				isCartesianGrid: true
			}
		},
		{
			id: 'overview-2',
			widgetName: 'graphWidget',
			width: 2,
			height: 4,
			initialProps: {
				...pressureGraph,
				title: 'Pressure',
				maxDataSamples: 30,
				isCartesianGrid: true
			}
		},
		{
			id: 'overview-3',
			widgetName: 'graphWidget',
			width: 2,
			height: 4,
			initialProps: {
				...temperatureGraph,
				title: 'Temperature',
				maxDataSamples: 30,
				isCartesianGrid: true
			}
		},
		{
			id: 'overview-4',
			widgetName: 'mapWidget',
			width: 4,
			height: 8
		},
		{
			id: 'overview-5',
			widgetName: 'graphWidget',
			width: 4,
			height: 4,
			initialProps: {
				...amplitudeGraph,
				title: 'Amplitude',
				maxDataSamples: 60,
				isCartesianGrid: true
			}
		},
		{
			id: 'overview-6',
			widgetName: 'spectrogramWidget',
			width: 4,
			height: 4,
			initialProps: {
				title: 'Spectrogram Widget'
			}
		},
		{
			id: 'overview-7',
			widgetName: '9dofWidget',
			width: 3,
			height: 4,
			initialProps: {
				title: 'Spectrogram Widget'
			}
		},
		{
			id: 'overview-8',
			widgetName: 'graphWidget',
			width: 3,
			height: 4,
			initialProps: {
				...accLineGraph,
				title: 'Accelerometer',
				maxDataSamples: 20
			}
		},
		{
			id: 'overview-9',
			widgetName: 'graphWidget',
			width: 3,
			height: 4,
			initialProps: {
				...gyroLineGraph,
				title: 'Gyroscope',
				maxDataSamples: 20
			}
		},
		{
			id: 'overview-10',
			widgetName: 'graphWidget',
			width: 3,
			height: 4,
			initialProps: {
				...magLineGraph,
				title: 'Magnetometer',
				maxDataSamples: 20
			}
		}
	]
};
