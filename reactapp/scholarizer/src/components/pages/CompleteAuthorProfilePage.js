import React, {useState} from "react";
import AuthorProfileHeader from './AuthorProfileHeader.js'
import './AuthorProfilePage.css'
import SuggestedAuthorCard from './SuggestedAuthorCard.js'
import NewPublicationCardFactory from "../NewPublicationCardFactory";
import {Link as LinkTo} from 'react-router-dom'
import {AuthorStatisticsBarChart} from "./AuthorStatisticsBarChart";
import {AuthorStatisticsBarChart2} from "./AuthorStatisticsBarChart2";
import {difference} from "set-operations";


let globalAuthor = null;

export const passAuthorToCompletePage = (author) => {
    if (author) {
        globalAuthor = author;
        // Save the author data to the local storage
        localStorage.setItem("completeAuthorData", JSON.stringify(author));
    }
};

export default function CompleteAuthorProfilePage() {
    const limit = 3;

    let [view, setView] = useState("publications");
    let [author, setAuthor] = useState(globalAuthor);
    let [coAuthorsIncluded, setCoAuthorsIncluded] = useState(true);

    if (author !== globalAuthor) {
        setAuthor(globalAuthor);
    }


    function generateSuggestedAuthors(key) {
        console.log("AuthorProfilePage.js: generateSuggestedAuthors() called with key: " + key);
        let suggestedAuthors = [];
        for (let i = 0; i < limit; i++) {
            suggestedAuthors.push(generateSuggestedAuthor(key, i));
        }

        return (
            <>
                {
                    suggestedAuthors.map((item) => {
                        return item
                    })
                }
            </>
        )
    }

    function generateFrequentCiters(used) {
        console.log("AuthorProfilePage.js: generateFrequentCiters() and the co authors are" + (used ? " " : " not ") + "used.");
        let suggestedAuthors = [];
        if (used) {
            for (let i = 0; i < limit; i++) {
                suggestedAuthors.push(generateSuggestedAuthor("frequentCiters", i));
            }
        } else {
            suggestedAuthors = difference(author["author"]["frequentCiters"], author["author"]["coAuthors"]);
        }

        return (
            <>
                {
                    suggestedAuthors.map((item) => {
                        return item
                    })
                }
            </>
        )
    }

    function generateSuggestedAuthor(key, i) {
        return author && author["author"] && author["author"][key] && author["author"][key][i]
            ? <SuggestedAuthorCard author={author["author"][key][i]}/> : "";
    }


    return (
        <div className='page-table'>
            <div className='profile-page'>
                <div className='profile-header'>
                    {author && author["author"] ? <AuthorProfileHeader author={author["author"]}/> : ""}
                </div>
                <div style={{borderTop: "2px solid #C3C3C3 "}}></div>


                <div className='pages-menu'>
                    <h1>
                        <div className='menu-items'>
                            <div className='menu-item'>
                                <LinkTo
                                    component="button"
                                    underline="none"
                                    fontSize="sm"
                                    fontWeight="lg"
                                    textColor="text.secondary"
                                    onClick={() => setView("publications")}
                                >
                                    <LinkTo className='link'>
                                        Publications
                                    </LinkTo>
                                </LinkTo>
                            </div>
                            <div className='menu-item'>
                                <LinkTo
                                    component="button"
                                    underline="none"
                                    fontSize="sm"
                                    fontWeight="lg"
                                    textColor="text.secondary"
                                    onClick={() => setView("statistics")}
                                >
                                    <LinkTo className='link'>
                                        Statistics
                                    </LinkTo>
                                </LinkTo>
                            </div>
                        </div>
                    </h1>
                </div>

                {view === 'publications' ? (
                    <div className='publications'>
                        {author && author["author"] && author["author"]["papers"]
                            ? <NewPublicationCardFactory publications={author["author"]["papers"]}/> : ""}
                    </div>
                ) : ""}
                {view === 'statistics' ? (
                    <div className='statistics'>
                        {author ? <AuthorStatisticsBarChart2 authors={[author["author"]]}/> : ""/* TODO: Look at */}
                        {author ? <AuthorStatisticsBarChart authors={[author["author"]]}/> : ""/* TODO: Look at */}
                    </div>
                ) : ""}
            </div>
            <div className="suggested-scholars-tab">
                <div className="suggested-scholars-table">

                    <div className="suggested-scholars-title">
                        Frequent Collaborators
                    </div>
                    <div className='suggested-authors-cards'>
                        {
                            generateSuggestedAuthors("frequentCoAuthors")
                        }
                    </div>

                    <div className="suggested-scholars-title">
                        Frequent Citers
                    </div>
                    <div className='suggested-authors-cards'>
                        {
                            generateFrequentCiters(coAuthorsIncluded)
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}
