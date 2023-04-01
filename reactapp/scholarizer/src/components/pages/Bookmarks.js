import React from "react";
import '../../App.css'
import './Bookmarks.css'
import NewPublicationCardFactory from "../NewPublicationCardFactory";
import {getBookmarksRequest} from "../requests/bookmarkRequests";


export default function Bookmarks() {
    const [isLoaded, setIsLoaded] = React.useState(false);
    const [bookmarks, setBookmarks] = React.useState([]);

    if (!isLoaded && sessionStorage.getItem("token")) {
        getBookmarksRequest(setBookmarks);
        setIsLoaded(true);
    }

    return (
        <>
            <div className="bookmark-title">
                Bookmarks
            </div>
            <div className="page-table">
                <div className="publication-cards">
                    <NewPublicationCardFactory publications={bookmarks}/>
                </div>
            </div>
        </>
    )
}