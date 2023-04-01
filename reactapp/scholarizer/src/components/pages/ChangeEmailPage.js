import React, {useState} from "react";
import {useNavigate} from 'react-router-dom'

import '../../App.css'
import './LogIn.css'
import {changeEmailRequest} from "../requests/accountRequests";


function ChangeEmailPage() {
    const [mail, setMail] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    if (sessionStorage.getItem("token") === null || sessionStorage.getItem("token") === '') {
        console.log("ChangePasswordPage.js: token is null or empty");
        navigate('/');
    }

    const handleSuccess = (message) => {
        setSuccess(message);
        sessionStorage.setItem("mail", mail);
    }

    const handleSubmit = e => {
        e.preventDefault();
        setSuccess('');

        if (mail === "") {
            setError('All fields are required.');
        } else if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))) {
            setError("Invalid email address.");
        } else {
            setError('');
        }

        if (error.length === 0 && mail !== '') {
            changeEmailRequest(mail, setError, handleSuccess);
        }
    };


    return (
        <form onSubmit={handleSubmit}>
            <h1>Change Email</h1>
            <label>
                Enter New Email:
                <input type="email" value={mail} onChange={e => setMail(e.target.value)}/>
            </label>
            <br/>
            <button type="submit">Change Email</button>
            <br/><br/>
            <span style={{marginLeft: "20px"}} />
            {error && !success ? <p id="errorMessage">{error}</p> : ""}
            {success && !error ? <p id="successMessage">{success}</p> : ""}
        </form>
    );
}

export default ChangeEmailPage;