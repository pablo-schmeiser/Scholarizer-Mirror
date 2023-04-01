import React from 'react';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';
import {Bar} from 'react-chartjs-2';

ChartJS.register(
    CategoryScale,
    LinearScale,
    BarElement,
    Title,
    Tooltip,
    Legend
);

let globalAuthors = [];

export const options = {
    responsive: true,
    plugins: {
        legend: {
            position: 'top',
        },
        title: {
            display: true,
            text: 'Indicies Statistics',
        },
    },
};

const labels = ['h-Index', 'i10-Index', 'h-Index w/o self citations', 'i10-Index w/o self-citation', 'h w/o Co-Authors Citations',
    'i10-Index w/o Co-Authors Citations', 'h-Index w/o Self & Co-Authors Citations', 'i10 w/o Self & Co-Authors Citations'];

export function CompareBarChartIndicies({authors}) {
    globalAuthors = authors;

    const updatedData = {
        labels,
        datasets: [
            {
                label: globalAuthors.length > 0 ? globalAuthors[0].name : "No Data",
                data: globalAuthors.length > 0 ? [globalAuthors[0]["indices"][0]["value"], globalAuthors[0]["indices"][1]["value"], globalAuthors[0]["indices"][2]["value"], globalAuthors[0]["indices"][3]["value"],
                    globalAuthors[0]["indices"][4]["value"], globalAuthors[0]["indices"][5]["value"], globalAuthors[0]["indices"][6]["value"], globalAuthors[0]["indices"][7]["value"]] : [],
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
            },
            {
                label: globalAuthors.length > 1 ? globalAuthors[1].name : "No Data",
                data: globalAuthors.length > 1 ? [globalAuthors[1]["indices"][0]["value"], globalAuthors[1]["indices"][1]["value"], globalAuthors[1]["indices"][2]["value"], globalAuthors[1]["indices"][3]["value"],
                    globalAuthors[1]["indices"][4]["value"], globalAuthors[1]["indices"][5]["value"], globalAuthors[1]["indices"][6]["value"], globalAuthors[1]["indices"][7]["value"]] : [],
                borderColor: 'rgb(107, 130, 161)',
                backgroundColor: 'rgba(107, 130, 161, 0.5)',
            },
            {
                label: globalAuthors.length > 2 ? globalAuthors[2].name : "No Data",
                data: globalAuthors.length > 2 ? [globalAuthors[2]["indices"][0]["value"], globalAuthors[2]["indices"][1]["value"], globalAuthors[2]["indices"][2]["value"], globalAuthors[2]["indices"][3]["value"],
                    globalAuthors[2]["indices"][4]["value"], globalAuthors[2]["indices"][5]["value"], globalAuthors[2]["indices"][6]["value"], globalAuthors[2]["indices"][7]["value"]] : [],
                borderColor: 'rgb(0, 162, 0)',
                backgroundColor: 'rgba(0, 162, 0, 0.5)',
            },
            {
                label: globalAuthors.length > 3 ? globalAuthors[3].name : "No Data",
                data: globalAuthors.length > 3 ? [globalAuthors[3]["indices"][0]["value"], globalAuthors[3]["indices"][1]["value"], globalAuthors[3]["indices"][2]["value"], globalAuthors[3]["indices"][3]["value"],
                    globalAuthors[3]["indices"][4]["value"], globalAuthors[3]["indices"][5]["value"], globalAuthors[3]["indices"][6]["value"], globalAuthors[3]["indices"][7]["value"]] : [],
                borderColor: 'rgb(152, 161, 107)',
                backgroundColor: 'rgba(151, 161, 107, 0.5)',
            },
        ],
    };

    return <Bar options={options} data={updatedData}/>;
}
