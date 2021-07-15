import { Dashboard } from '@wuespace/telestion-client-types';

export const recoveryDashboard: Dashboard = {
	title: 'Recovery',
	columns: 12,
	rows: 12,
	widgets: [
		{
			id: 'recovery-0',
			widgetName: 'stateWidget',
			width: 2,
			height: 4,
			initialProps: {
				title: 'stateWidget'
			}
		},
		{
			id: 'recovery-1',
			widgetName: 'mapWidget',
			width: 10,
			height: 12
		},
		{
			id: 'recovery-2',
			widgetName: 'gpsDetailsWidget',
			width: 2,
			height: 5,
			initialProps: {
				title: 'GPS Details'
			}
		}
	]
};
