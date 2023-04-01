import React, {useState} from "react";
import {useNavigate} from 'react-router-dom'

import '../../App.css'
import './LogIn.css'
import {passwordResetRequest} from "../requests/passwordResetRequest";


function ResetPasswordPage() {
    const [password, setPassword] = useState('');
    const [passwordConfirm, setPasswordConfirm] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    const minLength = 6;
    const urlParams = new URLSearchParams(window.location.search);

    const token = urlParams.get('token');
    if (token === null || token === '') {
        console.log("ResetPasswordPage.js: token is null or empty");
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
            passwordResetRequest(password, token, setError, setSuccess);
        }
    };


    return (
        <form onSubmit={handleSubmit}>
            <h1>Reset Password</h1>
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
            <button type="submit">Reset Password</button>
            <br/><br/>
            <span style={{marginLeft: "20px"}}></span>
            {error && !success ? <p id="errorMessage">{error}</p> : ""}
            {success && !error ? <p id="successMessage">{success}</p> : ""}
        </form>
    );
}

export default ResetPasswordPage;