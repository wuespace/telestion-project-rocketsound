import { ChannelAddress, GenericProps } from '@wuespace/telestion-client-types';
import { SpectrumColor } from '../../model/spectrum-color';

export interface WidgetProps extends GenericProps {
	/**
	 * The title of the widget.
	 */
	title: string;

	channel: ChannelAddress;

	stroke: SpectrumColor;

	xLabel: string;

	yLabel: string;

	minValue: number;

	maxValue: number;
}
