import React, {useState} from "react";
import {Link, useNavigate} from 'react-router-dom'

import '../../App.css'
import './LogIn.css'
import {BACKEND} from "../../WebAddresses";


//import React, { useState } from 'react';

const PATH = BACKEND;


function LogIn() {
    const [mail, setMail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();


    const handleSubmit = e => {
        e.preventDefault();

        if (mail === "" || password === "") {
            setError('All fields are required.');
        } else if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))) {
            setError('Invalid email address.');
        } else {
            setError('');
        }

        if (error.length === 0 && mail !== '' && password !== ''
            && /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)) {
            doLogin();
        }
    };

    const handleSuccessfulLogin = (body) => {
        console.log("Login.js: handleSuccessfulLogin() called");
        console.log("Login.js: body: " + body);
        console.log("Login.js: mail: " + mail);
        sessionStorage.setItem('token', body);
        sessionStorage.setItem('mail', mail);
        navigate("/");
        //TODO: Add func to load data from backend
        //doExplore(false);
    }

    const doLogin = function () {

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({mail, password}),
            redirect: 'follow'
        }

        fetch(PATH + 'login', options)
            .then((res) => res.status === 200 ? res.text() : Promise.reject(res.status))
            .then((text) => {
                    try {
                        console.log("Login.js: token: " + text);
                        handleSuccessfulLogin(text);
                    } catch {
                        console.log("Login.js: internal error: " + error);
                        setError('Error logging in please try again');
                    }
                }
            ).catch(error => {
            console.log("Login.js: error: " + error);
            setError('Error logging in please try again');
        });
    };


    return (
        <form onSubmit={handleSubmit}>
            <h1>Login</h1>
            <label>
                Email:
                <input type="email" value={mail} onChange={e => setMail(e.target.value)}/>
            </label>
            <br/>
            <label>
                Password:
                <input type="password" value={password} onChange={e => setPassword(e.target.value)}/>
            </label>
            <br/>
            <button type="submit">Login</button>
            <br/><br/>
            <Link to="/sign-up">Create account</Link>
            <span style={{marginLeft: "20px"}}></span>
            <Link to="/forgotpassword">Forgot password?</Link>
            {error && <p>{error}</p>}
        </form>
    );
}

export default LogIn;