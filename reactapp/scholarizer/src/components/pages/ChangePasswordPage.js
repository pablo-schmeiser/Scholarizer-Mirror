import React, {useState} from "react";
import {useNavigate} from 'react-router-dom'

import '../../App.css'
import './LogIn.css'
import {changePasswordRequest} from "../requests/accountRequests";


function ChangePasswordPage() {
    const [password, setPassword] = useState('');
    const [passwordConfirm, setPasswordConfirm] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    const minLength = 6;

    if (sessionStorage.getItem("token") === null || sessionStorage.getItem("token") === '') {
        console.log("ChangePasswordPage.js: token is null or empty");
        navigate('/');
    }

    const handleSubmit = e => {
        e.preventDefault();
        setSuccess('');

        if (password === "" || passwordConfirm === "") {
            setError('All fields are required.');
        } else if (password.length < minLength) {
            setError("Password must be at least " + minLength + " characters long.");
        } else if (password !== passwordConfirm) {
            setError('Passwords do not match.');
        } else {
            setError('');
        }

        if (error.length === 0 && password !== '' && passwordConfirm !== '') {
            changePasswordRequest(password, setError, setSuccess);
        }
    };


    return (
        <form onSubmit={handleSubmit}>
            <h1>Change Password</h1>
            <label>
                Enter New Password:
                <input type="password" value={password} onChange={e => setPassword(e.target.value)}/>
            </label>
            <br/>
            <label>
                Re-Enter New Password:
                <input type="password" value={passwordConfirm} onChange={e => setPasswordConfirm(e.target.value)}/>
            </label>
            <br/>
            <button type="submit">Change Password</button>
            <br/><br/>
            <span style={{marginLeft: "20px"}}></span>
            {error && !success ? <p id="errorMessage">{error}</p> : ""}
            {success && !error ? <p id="successMessage">{success}</p> : ""}
        </form>
    );
}

export default ChangePasswordPage;