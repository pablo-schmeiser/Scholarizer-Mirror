import React, { useState, useEffect } from 'react';
import './SearchResultsPage.css';
import SearchResultFactory from "../SearchResultFactory";
import DataContext from "./DataContext";
import { useParams } from 'react-router-dom';

let globalData;
let globalSearch;
let globalType;

export function setGlobalData(data) {
    globalData = data;
}

function addData(data) {
    globalData.push(...data);
    sessionStorage.setItem("data", JSON.stringify(globalData));
}

export function setGlobalType(type) {
    globalType = type;
}

export function setGlobalSearch(input) {
    globalSearch = input;
}

export default function SearchResultsPage() {
    let [data, setData] = useState(globalData);
    let [query, setQuery] = useState(globalSearch);
    const [count, setCount] = useState(0);
    const [menuVisible, setMenuVisible] = useState(false);
    const [sorting, setSorting] = useState("relevance");
    const { searchparam } = useParams();
    const [storage, setStorage] = useState(() => {
        const dataFromSessionStorage = JSON.parse(sessionStorage.getItem("data"));
        return dataFromSessionStorage || {};
    });

    if (data === undefined) {
        setData(sessionStorage.getItem("data"));
    }

    const updateData = (inputData) => {
        addData(inputData);
        setData(globalData);
        setCount(count + 1);
    }

    console.log("########################################################################################");
    console.log("data size: " + data.length);

    useEffect(() => {
        setData(globalData);
    }, [globalData]);

    const toggleMenu = () => {
        setMenuVisible(!menuVisible);
    };

    return (
        <>
            {/* check box  for filter statistics */}
            <div className="search-results-title">
                Showing Results For: {searchparam}
            </div>
            <div className="search-results-table">
                <div className="publication-cards">
                    <DataContext.Provider value={data}>
                        <SearchResultFactory data={data} query={query} type={globalType} updateData={updateData} />
                    </DataContext.Provider>
                </div>
            </div>
        </>
    )
}
