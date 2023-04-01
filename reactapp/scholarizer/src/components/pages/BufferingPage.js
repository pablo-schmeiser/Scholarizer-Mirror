import CircularProgress from '@mui/material/CircularProgress';
import React from "react"
import "./BufferingPage.css"
import {exploreRequest} from "../requests/exploreRequests";
import {useNavigate} from "react-router-dom";
import {setGlobalResults} from "./Explore";


export default function BufferingPage(){
    const [exploreResults, setExploreResults] = React.useState([]);
    const navigate = useNavigate();

    const doNavigate = (res) => {
        setGlobalResults(res);
        navigate("/explore");
    }

    if (sessionStorage.getItem("token")) {
        exploreRequest(console.error, doNavigate);
    }

    return(
        <div className='buffering-page'>
            <CircularProgress size="4rem"/>
        </div>
    )
}