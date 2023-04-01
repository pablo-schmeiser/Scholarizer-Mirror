import React from 'react';
import {
    Chart as ChartJS,
    RadialLinearScale,
    PointElement,
    LineElement,
    Filler,
    Tooltip,
    Legend,
} from 'chart.js';
import {Radar} from 'react-chartjs-2';

ChartJS.register(
    RadialLinearScale,
    PointElement,
    LineElement,
    Filler,
    Tooltip,
    Legend
);

export const data = {
    labels: ['Total Citations', 'h-Index', 'i10-Index'],
    datasets: [
        {
            label: 'Total Statistics',
            data: [72, 30, 50],
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgba(255, 99, 132, 1)',
            borderWidth: 1,
        },
        {
            label: 'Statistics w/o Self-Citations',
            data: [50, 25, 48],
            backgroundColor: 'rgba(0, 255, 0, 0.2)',
            borderColor: 'rgba(0, 255, 0, 0.2)',
            borderWidth: 1,
        },
    ],
};

export function ComparisonRadarChart() {
    return <Radar data={data}/>;
}
