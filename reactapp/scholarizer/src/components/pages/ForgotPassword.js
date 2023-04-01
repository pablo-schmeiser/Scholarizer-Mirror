import React, {useState} from 'react';
import './ForgotPassword.css';
import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

function ForgotPasswordPage() {
    const [mail, setMail] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const doResetCall = function () {

        const options = {
            method: 'PUT', redirect: 'follow', body: mail
        }

        fetch(PATH + 'account/reset', options)
            .then((res) => res.status === 200 ? res.text() : Promise.reject(res.status))
            .then((res) => {
                setSuccess('Password reset email sent successfully');
            })
            .catch(error => {
                console.log("Login.js: error: " + error);
                setError('Error resetting password please try again');
            });
    };

    const handleSubmit = e => {
        e.preventDefault();

        if (mail === '') {
            setError('Email is required');
        } else if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)) {
            console.log('Valid email');
            setError('');
        } else {
            setError('Invalid email address');
        }

        if (error.length === 0 && mail !== '' && /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)) {
            doResetCall();
        }
    };

    return (<form className="email-form" onSubmit={handleSubmit}>
            <h1>Enter your email address</h1>
            <label>
                <input id="forgotMailInput" type="email" value={mail} onChange={e => setMail(e.target.value)}/>
            </label>
            <br/>
            <button type="submitFP">Submit</button>
            {error && !success ? <p id="errorMessage">{error}</p> : ""}
            {success && !error ? <p id="successMessage">{success}</p> : ""}
        </form>
    );
}

export default ForgotPasswordPage;
