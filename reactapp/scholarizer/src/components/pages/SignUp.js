import React, {useState} from 'react';
import '../../App.css'
import './SignUp.css'
import {useNavigate} from "react-router-dom";
import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

function SignUp() {
    const [mail, setMail] = useState('');
    const [password, setPassword] = useState('');
    const [passwordRepeat, setPasswordRepeat] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();
    const minLength = 6;

    const handleSubmit = e => {
        e.preventDefault();
        // Check whether all fields are filled out
        if (mail === "" || password === "" || passwordRepeat === "") {
            setError('All fields are required.');
        } else if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))) {
            setError('Invalid email address.');
        } else if (password.length < minLength) {
            setError("Password must be at least " + minLength + " characters long.");
        } else if (password !== passwordRepeat) {
            setError('Passwords do not match.');
        } else {
            setError('');
        }

        if (error.length === 0 && mail !== '' && password !== '' && passwordRepeat !== ''
            && /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail) && password === passwordRepeat) {
            console.log(`Creating account with mail: ${mail}`);
            doSignUp();
        }
    };

    const handleSuccessfulSignUp = (body) => {
        console.log("SignUp.js: handleSuccessfulSignUp() called");
        console.log("SignUp.js: body: " + body);
        console.log("SignUp.js: email: " + mail);
        setSuccess('Account created successfully. Please check your email for a confirmation link.');
    }

    const doSignUp = function () {
        console.log("SignUp.js: doSignUp() called");
        console.log("SignUp.js: email: " + mail);

        const options = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({mail, password}),
            redirect: 'follow'
        }

        fetch(PATH + 'register', options)
            .then(res => {
                    res.status === 200 ? res.text() : Promise.reject(res.status);
                }
            ).then(res => {
            try {
                handleSuccessfulSignUp();
            } catch {
                throw Error(res);
            }
        }).catch(error => {
            setError(error);
        });
    }

    return (
        <form onSubmit={handleSubmit}>
            <h1>Sign Up</h1>
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
            <label>
                re-enter Password:
                <input type="password" value={passwordRepeat} onChange={e => setPasswordRepeat(e.target.value)}/>
            </label>
            <br/>
            <button type="submit">Sign Up</button>
            {success && !error ? <p id="successMessage">{success}</p> : <p/>}
            {error && !success ? <p id="errorMessage">{error}</p> : <p/>}
        </form>
    );
}

export default SignUp;