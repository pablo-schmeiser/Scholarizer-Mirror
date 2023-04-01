import React, {useState} from "react";
import AuthorProfileHeader from './AuthorProfileHeader.js'
import './AuthorProfileStatisticsPage.css'
import AuthorPagesMenu from './AuthorPagesMenu.js'
import SuggestedAuthorCard from './SuggestedAuthorCard.js'
import NewPublicationCard from './NewPublicationCard.js'
import StatisticsTable from "./StatisticsTable.js";
import {AuthorStatisticsBarChart} from "./AuthorStatisticsBarChart.js";

function AuthorProfileStatistics() {

    return (
        <h1>
            Statistics
        </h1>
    )
}

export default function AuthorProfileStatisticsPage() {
    const [view, setView] = React.useState("statistics")
    const handleViewChange = () => {
        setView(view === "publications" ? "statistics" : "publications");
    };

    return (
        <>
            <div className='page-table'>
                <div className='profile-page'>
                    <div className='profile-header'>
                        <AuthorProfileHeader/>
                    </div>
                    <div style={{borderTop: "2px solid #C3C3C3 "}}></div>


                    <div className='pages-menu'>
                        <AuthorPagesMenu
                            switchView={(view) => setView(view)}
                        />
                    </div>

                    {view === 'publications' && (
                        <div className='publications'>

                            <NewPublicationCard></NewPublicationCard>
                            <NewPublicationCard></NewPublicationCard>
                            <NewPublicationCard></NewPublicationCard>
                            <NewPublicationCard></NewPublicationCard>

                        </div>
                    )}
                    {view === 'statistics' && (
                        <>
                            <div className={'statistics-table'}>
                                <StatisticsTable/>
                            </div>
                            <div className={'bar-chart'}>
                                <AuthorStatisticsBarChart></AuthorStatisticsBarChart>
                            </div>
                        </>
                    )}
                </div>
                <div className="suggested-scholars-tab">
                    <div className="suggested-scholars-table">

                        <div className="suggested-scholars-title">
                            Frequent Collaborators
                        </div>
                        <div className='suggested-authors-cards'>
                            <SuggestedAuthorCard></SuggestedAuthorCard>
                            <SuggestedAuthorCard></SuggestedAuthorCard>
                        </div>
                    </div>

                </div>
            </div>

        </>
    )
}
