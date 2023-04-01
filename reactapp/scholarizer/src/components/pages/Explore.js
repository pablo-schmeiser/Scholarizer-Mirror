import React, {useEffect} from "react";
import '../../App.css'
import './Explore.css'
import NewPublicationCardFactory from "../NewPublicationCardFactory";
import {exploreRequest} from "../requests/exploreRequests";

/* The code for a vertical line */
/* <div className="vertical-line" style={{borderLeft: "2px solid #C3C3C3", minHeight:600}}></div> */

export let globalResults = [];

export const setGlobalResults = (results) => {
    globalResults = results;
    // Save the explore results to the local storage
    localStorage.setItem("exploreResults", JSON.stringify(results));
}

export const doRequest = () => {
    exploreRequest(console.error, setGlobalResults);
};

export default function Explore() {
    let [exploreResults, setExploreResults] = React.useState(globalResults);
    console.log(globalResults);
    console.log("results, exploreResults:");
    console.log(exploreResults);

    useEffect(() => {
        if (!exploreResults || exploreResults.length === 0) {
            // Load the explore results from the local storage
            const storedExploreResults = localStorage.getItem("exploreResults");
            if (storedExploreResults) {
                const parsedExploreResults = JSON.parse(storedExploreResults);
                setExploreResults(parsedExploreResults);
                globalResults = parsedExploreResults;
            } else {
                // Fetch the explore results here if it's not available in the local storage
                // (e.g., when visiting the page for the first time or the local storage was cleared)
                doRequest();
                setExploreResults(globalResults);
            }
        }
    }, []);

    return (
        <>
            <div className="explore-title">
                Recommended For You
            </div>
            <div className="page-table">
                <div className="publication-cards">
                    <NewPublicationCardFactory publications={exploreResults} enabled={false}/>
                </div>
            </div>
        </>
    );
}