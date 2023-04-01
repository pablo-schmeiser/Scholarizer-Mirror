import React, {useState, useEffect} from 'react'
import {Link, useNavigate} from 'react-router-dom'
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faTimes, faBars, faHome} from "@fortawesome/free-solid-svg-icons";
import ScholarizerLogo from './images/ScholarizerLogo.png'
import {MdSearch, MdExplore} from 'react-icons/md'
import {IoIosBookmarks} from 'react-icons/io'
import {BsFillPersonFill} from 'react-icons/bs'
import CircularProgress from '@mui/material/CircularProgress';

import './Navbar.css'
import {setGlobalData, setGlobalSearch} from "./pages/SearchResultsPage";
import {doRequest, globalResults, setGlobalResults} from "./pages/Explore";
import {BACKEND} from "../WebAddresses";

const PATH = BACKEND;

function Navbar() {
    const [menuVisible, setMenuVisible] = useState(false);
    const [searchValue, setSearchValue] = useState('');
    let [data, setData] = useState(null);
    let [type, setType] = useState(null);
    const navigate = useNavigate();
    const [searchActive, setSearchActive] = useState(false);
    const [offset, setOffset] = useState(0);

    const [click, setClick] = useState(false);
    const [button, setButton] = useState(true);

    const handleSuccessfulSearch = (body) => {
        data = body;
        setGlobalData(data);
        setGlobalSearch(searchValue);
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
            + (type ? ("&type=" + type) : "") + ("&offset=" + offset), requestOptions)
            .then(res => res.text())
            .then(body => {
                try {
                    const data = JSON.parse(body);
                    setGlobalData(data);
                    setGlobalSearch(searchValue);
                    navigate(`/searchresults/${searchValue}`);
                    setSearchActive(false);
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
    };

    const doExplore = function (click = false) {
        const options = {
            method: 'GET',
            headers: {
                'Authentication': sessionStorage.getItem('token')
            },
            redirect: 'follow'
        }

        return fetch(PATH + 'recommended', options)
            .then((res) => res.text())
            .then((body) => {
                    try {
                        const data = JSON.parse(body);
                        setGlobalResults(data);
                        if (click) {
                            navigate(`/explore`);
                        }
                        setSearchActive(false);
                    } catch {
                        throw Error(body);
                    }
                }
            ).catch(error => {
                    console.log("Explore.js: error: " + error);
                }
            );
    }


    const handleClick = () => setClick(!click);
    const closeMobileMenu = () => setClick(false);

    const logInOut = () => {
        if (sessionStorage.getItem("mail") && sessionStorage.getItem("token")) {
            sessionStorage.removeItem("token");
            sessionStorage.removeItem("mail");
        }
        closeMobileMenu();
    }

    const showButton = () => {
        if (window.innerWidth <= 960) {
            setButton(false);
        } else {
            setButton(true);
        }
    };

    const explore = () => {
        closeMobileMenu();
        doRequest();
    }

    useEffect(() => {
        showButton();
    }, []);

    window.addEventListener('resize', showButton);

    return (
        <>
            <nav className='navbar'>
                <div className='navbar-container'>
                    <div className='logo'>
                        <Link to="/" className="navbar-logo">
                            <img src={ScholarizerLogo} width={120} height={60} alt="Scholarizer"/>
                        </Link>
                    </div>
                    <div className='Search'>
                        <input type="search" placeholder="Search for Author or Publication"
                               value={searchValue} onChange={event => setSearchValue(event.target.value)}
                               onKeyDown={handleKeyDown}/>
                        {!searchActive ? (
                            <button onChange={event => setSearchValue(event.target.value)} onClick={handleSearchClick}>
                                <div className='navbar-search'>
                                    <MdSearch fontSize="1.5rem"/>
                                </div>
                            </button>) : (<button>
                            <div className='navbar-buffering'>
                                <CircularProgress size="1.3rem"/>
                            </div>
                        </button>)}
                    </div>
                    <div className='menu-icon' onClick={handleClick}>
                        {click ? <FontAwesomeIcon icon={faTimes}/> : <FontAwesomeIcon icon={faBars}/>}
                    </div>
                    <ul className={click ? 'nav-menu active' : 'nav-menu'}>
                        {(sessionStorage.getItem("mail") && sessionStorage.getItem("token"))
                            ? <>
                                <li className='nav-item'>
                                    <Link to='/buffering' className='nav-links' onClick={closeMobileMenu}>
                                        <MdExplore fontSize="1 rem"/>
                                        Explore
                                    </Link>
                                </li>
                                <li className='nav-item'>
                                    <Link to='/bookmarks' className='nav-links' onClick={closeMobileMenu}>
                                        <IoIosBookmarks fontSize="1 rem"/>
                                        Bookmarks
                                    </Link>
                                </li>
                            </> : ""}
                        <li className='nav-item'>
                            <Link to={(sessionStorage.getItem("mail") && sessionStorage.getItem("token"))
                                ? '/profile' : '/log-in'} className='nav-links' onClick={closeMobileMenu}>
                                <BsFillPersonFill fontSize="1 rem"/>
                                {(sessionStorage.getItem("mail") && sessionStorage.getItem("token"))
                                    ? sessionStorage.getItem("mail").split("@")[0] : "Profile"}
                            </Link>
                        </li>
                        <li className='nav-item'>
                            <Link to={(sessionStorage.getItem("mail") && sessionStorage.getItem("token"))
                                ? '/' : '/log-in'} className='nav-links' onClick={logInOut}>
                                {(sessionStorage.getItem("mail") && sessionStorage.getItem("token"))
                                    ? "Log Out" : "Log In"}
                            </Link>
                        </li>
                    </ul>
                </div>
            </nav>
        </>
    );
}

export default Navbar