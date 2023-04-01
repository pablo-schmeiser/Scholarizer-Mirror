import React, {Component} from 'react'

import {SimpleDropdown} from 'react-js-dropdavn'
import 'react-js-dropdavn/dist/index.css'

const data = [
    {label: 'by relevance', value: "relevance"},
    {label: 'by citation count', value: "citations"},
    {label: 'by publication date', value: "date"},
    {label: 'alphabetically', value: "alphabetical"}
]

export default function Filter({funcToCall}) {
    const handleChange = (event) => {
        funcToCall(event.target.value);
    };

    return (
        <SimpleDropdown
            options={data}
            placeholder="Sort by"
            defaultOption={data[0]}
            clearable
            onChange={handleChange}
            configs={
                {position: {y: 'bottom', x: 'center'}}
            }
        />
    );
}