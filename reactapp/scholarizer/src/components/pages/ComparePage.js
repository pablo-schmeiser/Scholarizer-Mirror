import React, { useState, useEffect } from 'react';
import './ComparePage.css';
import CompareAuthorsList from './CompareAuthorsList';
import { CompareBarChartCitations } from './CompareBarChartCitations';
import { CompareBarChartIndicies } from './CompareBarChartIndicies';

const localStorageKey = 'globalAuthors';

export function setGlobalAuthors(authors) {
    localStorage.setItem(localStorageKey, JSON.stringify(authors));
}

export const passAuthorToComparison = ({ author }) => {
    let newAuthors = JSON.parse(localStorage.getItem(localStorageKey)) || [];
    newAuthors.push(author);
    setGlobalAuthors(newAuthors);
};

export default function ComparePage() {
    const [authors, setAuthors] = useState(
        JSON.parse(localStorage.getItem(localStorageKey)) || []
    );
    const [refresh, setRefresh] = useState(false);

    useEffect(() => {
        const handleStorageChange = (e) => {
            if (e.key === localStorageKey) {
                setAuthors(JSON.parse(e.newValue));
            }
        };

        window.addEventListener('storage', handleStorageChange);
        return () => window.removeEventListener('storage', handleStorageChange);
    }, []);

    const handleRefresh = () => {
        setRefresh(!refresh);
        setAuthors(JSON.parse(localStorage.getItem(localStorageKey)) || []);
    };

    return (
        <>
            <div className='compare-page-table'>
                <div className='compare-page-title'>Compare Authors</div>

                <div className='compare-authors-list'>
                    <CompareAuthorsList authors={authors} refreshFunc={handleRefresh} />
                </div>
                <div className='compare-barcharts'>
                    <div className='citations-barchart'>
                        <CompareBarChartCitations authors={authors} />
                    </div>
                    <div className='indicies-barchart'>
                        <CompareBarChartIndicies authors={authors} />
                    </div>
                </div>
                <div className='color-key'>
                    <div className='total-stats-key'>Total Statistics</div>
                    <div className='no-self-key'>Statistics w/o Self Citations</div>
                    <div className='no-coauthors-key'>Statistics w/o Co-Author Citations</div>
                    <div className='no-self-no-coauthors-key'>
                        Statistics w/o Self & Co-Author Citations
                    </div>
                </div>
            </div>
        </>
    );
}
