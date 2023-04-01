import AuthorProfileHeader from "./pages/AuthorProfileHeader";
import React, { useState } from "react";
import { searchRequest } from "./requests/searchRequests";
import "./ShowmoreButton.css";

export default function AuthorProfileHeaderFactory({authors, updateData, inputQuery, enabled=true}) {
    let [data, setData] = useState(authors); //TODO: update to set and update data of how often the show more button is clicked
    const [query, setQuery] = useState(inputQuery);
    const [offset, setOffset] = useState(0);
    const offsetIncrement = 3;
    const maxResults = 10000;

    const handleShowMore = () => {
        setOffset(offset + offsetIncrement);
        console.log("Offset: ", offset);
        searchRequest(query, "author", (offset + offsetIncrement), updateData);
    }


    return (
        <>
            {authors.map((item) => {
                return (
                    <>
                        <AuthorProfileHeader author={item}/>
                        <div style={{ borderTop: "2px solid #C3C3C3 " }} />
                    </>
                );
            })}
            {!enabled ||
            authors.length === 0 ||
            authors.length < (offset + offsetIncrement) ||
            authors.length + offsetIncrement >= maxResults ? (
                ""
            ) : (
                <button className="show-more" onClick={handleShowMore}>
                    Show more
                </button>
            )}
        </>
    );
}
