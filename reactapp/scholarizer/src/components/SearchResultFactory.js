import AuthorProfileHeaderFactory from "./AuthorProfileHeaderFactory";
import NewPublicationCardFactory from "./NewPublicationCardFactory";
import React, {useEffect, useState} from "react";

export default function SearchResultFactory(data) {
    let papers = [];
    let authors = [];

    const [storage, setStorage] = useState(() => {
        const dataFromSessionStorage = JSON.parse(sessionStorage.getItem("data"));
        return dataFromSessionStorage || {};
    });

    if (storage["data"] !== undefined) {
        console.log("storage size: ", storage["data"].length);
    }
    console.log("factory data size: ", data["data"].length);

    useEffect(() => {
        if (data["data"]) {
            sessionStorage.setItem("data", JSON.stringify(data));
        }
    }, [data]);

    if (data["data"] !== undefined) {
        for (const element of data["data"]) {
            if (element["type"] === "paper") {
                papers.push(element);
            } else if (element["type"] === "author") {
                authors.push(element);
            } else {
                console.log("Error: Item type not recognized");
            }
        }
    } else {
        for (const element of storage["data"]) {
            if (element["type"] === "paper") {
                papers.push(element);
            } else if (element["type"] === "author") {
                authors.push(element);
            } else {
                console.log("Error: Item type not recognized");
            }
        }
    }

    sortByCitations(authors, false);
    sortByCitations(papers, false);
    preferContained(papers, data.query, false);

    function sortByCitations(elements, reverse = false) {
        elements.sort((a, b) => {
            return reverse ? a["citations"] - b["citations"] : b["citations"] - a["citations"];
        });
    }

    function sortPapersByDate(papers, reverse = false) {
        papers.sort((a, b) => {
            if (!a["publicationDate"] && !b["publicationDate"]) return 0;
            if (!a["publicationDate"]) return 1;
            if (!b["publicationDate"]) return -1;

            const dateA = new Date(a["publicationDate"]);
            const dateB = new Date(b["publicationDate"]);
            return reverse ? dateA - dateB : dateB - dateA;
        });
    }

    function sortPapersAlphabetically(papers, reverse = false) {
        papers.sort((a, b) => {
            return a["title"].localeCompare(b["title"]);
        });
        if (reverse) papers.reverse();
    }

    function sortAuthorsAlphabetically(authors, reverse = false) {
        authors.sort((a, b) => {
            return a["name"].localeCompare(b["name"]);
        });
        if (reverse) authors.reverse();
    }

    function preferContained(elements, query, abstract = false) {
        let titleContained = [];
        let abstractContained = [];
        let notContained = [];

        for (const element of elements) {
            if (element["title"].toLowerCase().includes(query.toLowerCase())) {
                titleContained.push(element);
            } else if (abstract && element["abstractText"].toLowerCase().includes(query.toLowerCase())) {
                abstractContained.push(element);
            } else {
                notContained.push(element);
            }
        }
        return titleContained.concat(abstractContained).concat(notContained);
    }

    return (
        <>
            <AuthorProfileHeaderFactory authors={authors} updateData={data.updateData} inputQuery={data.query} />
            <NewPublicationCardFactory publications={papers} updateData={data.updateData} inputQuery={data.query}/>
        </>
    );
}
