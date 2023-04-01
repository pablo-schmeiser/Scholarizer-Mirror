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
            text: 'Citations Statistics',
        },
    },
};

const labels = ['Total Citations', 'w/o Self Citations', 'w/o Co-Authors Citations', 'w/o Self & Co-Author Citations'];

export function CompareBarChartCitations({authors}) {
    console.log("CompareBarChartCitations called");
    console.log(authors);
    globalAuthors = authors;
    console.log("globalAuthors:");
    console.log(globalAuthors);

    const updatedData = {
        labels,
        datasets: [
            {
                label: globalAuthors.length > 0 ? globalAuthors[0].name : "No Data",
                data: globalAuthors.length > 0 ? [globalAuthors[0]["citations"], globalAuthors[0]["nonSelfCitations"], globalAuthors[0]["nonCoAuthorCitations"], globalAuthors[0]["nonSelfAndCoAuthorCitations"]] : [],
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
            },
            {
                label: globalAuthors.length > 1 ? globalAuthors[1].name : "No Data",
                data: globalAuthors.length > 1 ? [globalAuthors[1]["citations"], globalAuthors[1]["nonSelfCitations"], globalAuthors[1]["nonCoAuthorCitations"], globalAuthors[1]["nonSelfAndCoAuthorCitations"]] : [],
                borderColor: 'rgb(107, 130, 161)',
                backgroundColor: 'rgba(107, 130, 161, 0.5)',
            },
            {
                label: globalAuthors.length > 2 ? globalAuthors[2].name : "No Data",
                data: globalAuthors.length > 2 ? [globalAuthors[2]["citations"], globalAuthors[2]["nonSelfCitations"], globalAuthors[2]["nonCoAuthorCitations"], globalAuthors[2]["nonSelfAndCoAuthorCitations"]] : [],
                borderColor: 'rgb(0, 162, 0)',
                backgroundColor: 'rgba(0, 162, 0, 0.5)',
            },
            {
                label: globalAuthors.length > 3 ? globalAuthors[3].name : "No Data",
                data: globalAuthors.length > 3 ? [globalAuthors[3]["citations"], globalAuthors[3]["nonSelfCitations"], globalAuthors[3]["nonCoAuthorCitations"], globalAuthors[3]["nonSelfAndCoAuthorCitations"]] : [],
                borderColor: 'rgb(152, 161, 107)',
                backgroundColor: 'rgba(151, 161, 107, 0.5)',
            },
        ],
    };

    console.log("data:");
    console.log(updatedData);

    return <Bar options={options} data={updatedData}/>;
}
