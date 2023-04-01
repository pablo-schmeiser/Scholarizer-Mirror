import React from "react";
import "./ActivationPage.css"
import {Link, useLocation} from "react-router-dom";
import IconCheckmarkCircleOutline from "./IconCheckmarkCircleOutline";
import ErrorOutlineOutlinedIcon from '@mui/icons-material/ErrorOutlineOutlined';
import {activateRequest} from "../requests/activateRequests";


export default function ActivationPage() {
    const [data, setData] = React.useState(false);

    const useQuery = () => new URLSearchParams(useLocation().search);
    const query = useQuery();

    const key = query.get("key");
    console.log(key);
    activateRequest(key, setData);


    return (
        <>

            <div className="activation-page">
                {data ?
                    <>
                        <div className="activation-success-photo">
                            <IconCheckmarkCircleOutline className="App-logo"/>
                        </div>
                        <h1>Congratulations your account has been successfully activated</h1>
                        <Link to="/log-in">Continue to Log-In Page</Link>
                    </>
                    :
                    <>
                        <div className="activation-failure-photo">
                            <ErrorOutlineOutlinedIcon className="App-logo"/>
                        </div>
                        <h1>Sorry, your account could not be activated</h1>
                        <Link to="/sign-up">Continue to Sign-Up Page</Link>
                    </>
                }
            </div>
        </>
    )
}