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
import {faker} from '@faker-js/faker';

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
    indexAxis: 'y',
    elements: {
        bar: {
            borderWidth: 2,
        },
    },
    responsive: true,
    plugins: {
        legend: {
            position: 'right',
        },
        title: {
            display: true,
            text: 'Scholar Index Statistics',
        },
    },
};

const labels = ['h-Index', 'i10-Index'];

export function AuthorStatisticsBarChart({authors}) {
    globalAuthors = authors;
    console.log(globalAuthors);
    console.log(globalAuthors[0]["papers"].length);
    console.log("total citations:");
    console.log(globalAuthors[0]["totalCitations"]);

    const updatedData = {
        labels,
        datasets: [
            {
                label: 'Total Statistics',
                data: globalAuthors.length > 0 ? [
                    globalAuthors[0]["indices"][0]["value"], globalAuthors[0]["indices"][1]["value"]] : [],
                borderColor: 'rgb(255, 99, 132)',
                backgroundColor: 'rgba(255, 99, 132, 0.5)',
            },
            {
                label: 'w/o Self-Citations',
                data: globalAuthors.length > 0 ? [globalAuthors[0]["indices"][2]["value"],
                    globalAuthors[0]["indices"][3]["value"]] : [],
                borderColor: 'rgb(107, 130, 161)',
                backgroundColor: 'rgba(107, 130, 161, 0.5)',
            },
            {
                label: 'w/o Co-Author Citations',
                data: globalAuthors.length > 0 ? [globalAuthors[0]["indices"][4]["value"],
                    globalAuthors[0]["indices"][5]["value"]] : [],
                borderColor: 'rgb(0, 162, 0)',
                backgroundColor: 'rgba(0, 162, 0, 0.5)',
            },
            {
                label: 'w/o Self & Co-Author Citations',
                data: globalAuthors.length > 0 ? [globalAuthors[0]["indices"][6]["value"],
                    globalAuthors[0]["indices"][7]["value"]] : [],
                borderColor: 'rgb(152, 161, 107)',
                backgroundColor: 'rgba(151, 161, 107, 0.5)',
            },
        ],
    };

    console.log(updatedData);
    return <Bar options={options} data={updatedData}/>;

}