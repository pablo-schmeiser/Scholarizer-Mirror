import NewPublicationCard from "./pages/NewPublicationCard";
import React, {useEffect, useState} from "react";
import "./ShowmoreButton.css";
import { searchRequest } from "./requests/searchRequests";

export default function NewPublicationCardFactory({publications,updateData,inputQuery,enabled = true,
                                                  }) {
    let [data, setData] = useState(publications);
    const [query, setQuery] = useState(inputQuery);
    const [offset, setOffset] = useState(0);
    const offsetIncrement = 20;
    const maxResults = 10000;

    useEffect(() => {
        setData(publications);
    }, [publications]);

    const handleShowMore = () => {
        setOffset(offset + offsetIncrement);
        searchRequest(query, "paper", offset + offsetIncrement, updateData);
    };

    return (
        <>
            {data.map((item) => {
                return (
                    <>
                        <NewPublicationCard publication={item} />
                        <div style={{ borderTop: "2px solid #C3C3C3 " }} />
                    </>
                );
            })}
            {!enabled ||
            publications.length === 0 ||
            publications.length < offset + offsetIncrement ||
            publications.length + offsetIncrement >= maxResults ? (
                ""
            ) : (
                <button className="showmore" onClick={handleShowMore}>
                    Show more
                </button>
            )}
        </>
    );
}
