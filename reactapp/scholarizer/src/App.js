import './App.css';
import React, {useEffect, useState} from "react";
import Navbar from './components/Navbar';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import LandingPage from './components/pages/LandingPage';
import Explore from './components/pages/Explore';
import SignUp from './components/pages/SignUp';
import LogIn from './components/pages/LogIn';
import Profile from './components/pages/Profile';
import Bookmarks from './components/pages/Bookmarks';
import AuthorProfilePage from './components/pages/AuthorProfilePage';
import AuthorProfileStatisticsPage from './components/pages/AuthorProfileStatisticsPage'
import SearchResultsPage from './components/pages/SearchResultsPage';
import ComparePage from './components/pages/ComparePage';
import DataContext from "./components/pages/DataContext";
import ActivationPage from "./components/pages/ActivationPage";
import ChangePasswordPage from "./components/pages/ChangePasswordPage";
import ResetPasswordPage from "./components/pages/ResetPasswordPage";
import Footer from "./components/Footer";
import BufferingPage from "./components/pages/BufferingPage";
import ChangeEmailPage from "./components/pages/ChangeEmailPage";
import ForgotPasswordPage from "./components/pages/ForgotPassword";
import CompleteAuthorProfilePage from "./components/pages/CompleteAuthorProfilePage";


function App() {
    const [data, setData] = useState(null)
    const [email, setEmail] = useState((sessionStorage.getItem("email") ? sessionStorage.getItem("email") : ""));
    const [token, setToken] = useState((sessionStorage.getItem("password") ? sessionStorage.getItem("password") : ""));

    // Show the name "ScholariZer" in the tab of the browser
    useEffect(() => {
        document.title = "ScholariZer";
    }, []);

    return (
        <div className="App">
            <DataContext.Provider value={{data, setData}}>
                <Router>
                    <Navbar/>
                        <div style={{borderTop: "2px solid #C3C3C3 ", marginLeft: 20, marginRight: 20}}></div>
                    <Routes>
                        <Route path='/' exact element={<LandingPage/>}/>
                        <Route path='/explore' element={<Explore/>}/>
                        <Route path='/sign-up' element={<SignUp/>}/>
                        <Route path='/log-in' element={<LogIn/>}/>
                        <Route path='/profile' element={<Profile/>}/>
                        <Route path='/bookmarks' element={<Bookmarks/>}/>
                        <Route path="/authorprofile/:authorName" element={<AuthorProfilePage/>}/>
                        <Route path="/authorprofile/complete/:authorName" element={<CompleteAuthorProfilePage/>}/>
                        <Route path='/authorprofilestatistics' element={<AuthorProfileStatisticsPage/>}/>
                        <Route path='/searchresults/:searchTerm' element={<SearchResultsPage/>}/>
                        <Route path='/compare' element={<ComparePage/>}/>
                        <Route path='/forgotpassword' element={<ForgotPasswordPage/>}/>
                        <Route path='/activation' element={<ActivationPage/>}/>
                        <Route path='/reset' element={<ResetPasswordPage/>}/>
                        <Route path='/change-password' element={<ChangePasswordPage/>}/>
                        <Route path='/change-email' element={<ChangeEmailPage/>}/>
                        <Route path='/buffering' element={<BufferingPage/>}/>
                    </Routes>

                </Router>
                <div className="footer">
                    <div style={{borderTop: "2px solid #C3C3C3 ", marginLeft: 20, marginRight: 20}}></div>
                    <Footer/>
                </div>
            </DataContext.Provider>
        </div>

    );

}

export default App;
