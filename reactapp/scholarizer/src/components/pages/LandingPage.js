import {useState} from 'react'
import {MdSearch} from 'react-icons/md'
import ScholarizerLogo from '../images/ScholarizerLogo.png'
import './LandingPage.css'
import {useNavigate} from "react-router-dom";
import {
    setGlobalData,
    setGlobalSearch,
    setGlobalType
} from "./SearchResultsPage";
import CircularProgress from '@mui/material/CircularProgress';

import DataContext from './DataContext';
import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;


function LandingPage() {
    const [menuVisible, setMenuVisible] = useState(false);
    const [searchValue, setSearchValue] = useState('');
    const [searchActive, setSearchActive] = useState(false);
    let [data, setData] = useState(null);
    let [type, setType] = useState(null);
    let [author, setAuthor] = useState(null);
    let [paper, setPaper] = useState(null);
    const [offset, setOffset] = useState(0);
    const navigate = useNavigate();

    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        setPaper(data.get("paper"));
        setAuthor(data.get("author"));
        setMenuVisible(false); // Add this line to close the filter-window
    }

    const handleSuccessfulSearch = (body) => {
        data = body;
        setGlobalData(data);
        setGlobalSearch(searchValue);
        setGlobalType(author === "author" && paper === "paper"
            ? null : (author === null
                ? paper : author)
        );
        navigate(`/searchresults/${searchValue}`);  //added the searchValue to the URL
        setSearchActive(false);
    };

    const handleKeyDown = (event) => {
        if (event.keyCode === 13) {
            setSearchValue(event.target.value);
            setSearchActive(true);
            doSearch();
        }
    };

    const handleSearchClick = () => {
        setSearchActive(true);
        doSearch();
    };

    const doSearch = function () {
        const requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };
        fetch(PATH + 'search?query=' + searchValue.replaceAll(" ", "+")
            + ((author !== "author" || paper !== "paper")
                ? ("&type=" + (author === null
                    ? paper : author))
                : "")
            + ("&offset=" + offset), requestOptions)
            .then(res => res.text())
            .then(body => {
                try {
                    handleSuccessfulSearch(JSON.parse(body))
                } catch {
                    throw Error(body);
                }
            })
            .catch(
                error => {
                    console.log('error', error);
                    setSearchActive(false);
                }
            );
    }

    const toggleMenu = () => {
        setMenuVisible(!menuVisible);
    };


    return (
        <DataContext.Provider value={data}>
            {sessionStorage.getItem('token') ? (
                <div style={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    padding: "10px",
                }}>
                    <h4>Welcome to Scholarizer!</h4>
                </div>
            ) : ""
            }
            <div className='container'>
                <div className='row'>
                    <div className='main-logo'>
                        <img src={ScholarizerLogo} width={300} height={150} alt="Scholarizer"/>
                    </div>
                    <div className='SearchBar'>
                        <div className='HomeSearch'>
                            <input type="home-search" placeholder="Search for Author or Publication" width={800}
                                   value={searchValue} onChange={event => setSearchValue(event.target.value)}
                                   onKeyDown={handleKeyDown}/>
                            {!searchActive ? (<button onChange={event => setSearchValue(event.target.value)}
                                                      onClick={handleSearchClick}>
                                <div className='home-search'>
                                    <MdSearch fontSize="1.5rem"/>
                                </div>
                            </button>) : (<button>
                                <div className='buffering'>
                                    <CircularProgress size="1.5rem"/>
                                </div>
                            </button>)}
                        </div>
                    </div>
                    <div className='advanced-search-button'>
                        <button className='advanced-search-button-button' onClick={toggleMenu}>Advanced Search</button>
                        {menuVisible && (
                            <div className='advanced-search-menu'>
                                <div className='filter-options-container'>
                                    <div className={'filter-options'}>
                                        <form onSubmit={handleSubmit} name="searchType">
                                            <label>
                                                <input className="filter-option" type="checkbox" name="author"
                                                       value="author"/> Author
                                            </label>
                                            <label>
                                                <input className="filter-option" type="checkbox" name="paper"
                                                       value="paper"/> Paper
                                            </label>
                                            <input className="advanced-search-button-button" type="submit"
                                                   value="Apply Filters"/>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </DataContext.Provider>
    );
}

export default LandingPage;
