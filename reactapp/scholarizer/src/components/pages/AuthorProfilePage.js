import React, {useEffect, useState} from "react";
import AuthorProfileHeader from './AuthorProfileHeader.js'
import './AuthorProfilePage.css'
import NewPublicationCardFactory from "../NewPublicationCardFactory";
import {Link as LinkTo, redirect, useNavigate, useParams} from 'react-router-dom'
import {AuthorStatisticsBarChart} from "./AuthorStatisticsBarChart";
import {AuthorStatisticsBarChart2} from "./AuthorStatisticsBarChart2";
import CircularProgress from "@mui/material/CircularProgress";
import {getSuggestedRequest} from "../requests/getSuggestedRequest";
import {BACKEND} from "../../WebAddresses";
import {passAuthorToCompletePage} from "./CompleteAuthorProfilePage";

const PATH = BACKEND;
let globalAuthor = null;

export const passAuthorToPage = (author) => {
    if (author) {
        globalAuthor = author;
        // Save the author data to the local storage
        localStorage.setItem("authorData", JSON.stringify(author));
    }
};

export default function AuthorProfilePage() {
    const navigate = useNavigate();
    const {authorName} = useParams();

    const [view, setView] = useState("publications");
    const [author, setAuthor] = useState(globalAuthor);
    const [loading, setLoading] = useState(false);
    const [offset, setOffset] = useState(0);
    const [redirect, setRedirect] = useState(false);

    if (author !== globalAuthor) {
        setAuthor(globalAuthor);
    }

    if (redirect) {
        console.log("Passed suggestions to complete page:", author);
        setLoading(false);
        navigate(`/authorprofile/complete/${author.name}`);
        setRedirect(false);
    }

    const passSuggestionsToCompletePage = (author) => {
        passAuthorToCompletePage({author});
        setRedirect(true);
    }

    const handleSuggestedScholars = () => {
        setLoading(true);
        getSuggestedRequest(author["author"]["id"], passSuggestionsToCompletePage);
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
                            ? <NewPublicationCardFactory publications={author["author"]["papers"]}
                                                         inputQuery={author["author"]}/> : ""}
                    </div>
                ) : ""}
                {view === 'statistics' ? (
                    <div className='statistics'>
                        {author ? <AuthorStatisticsBarChart2 authors={[author["author"]]}/> : ""}
                        {author ? <AuthorStatisticsBarChart authors={[author["author"]]}/> : ""}
                    </div>
                ) : ""}
            </div>
            <div className="suggested-scholars-tab">
                {loading
                    ? <div className='buffering'>
                        <CircularProgress size="1.5rem"/>
                    </div>
                    : <div className="suggested-scholars-table">
                        <button id="load-suggested-scholars" className="suggested-scholars-button"
                                onClick={handleSuggestedScholars}>
                            Load Suggested Scholars
                        </button>
                    </div>
                }
            </div>
        </div>
    )
}
