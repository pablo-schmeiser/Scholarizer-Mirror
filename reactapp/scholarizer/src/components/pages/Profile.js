import React from "react";
import '../../App.css'
import './Profile.css'
import Typography from '@mui/joy/Typography';
import NewInterestsTable from "./NewInterestsTable";
import ProfileCard from "./ProfileCard";


export default function Profile() {
    //TODO: Connect to backend to get user data

    return (<div className="profile">
            <div className='title'>
                <Typography fontWeight="md">USER PROFILE</Typography>
            </div>
            <div className="profile-card">
                <ProfileCard mail={sessionStorage.getItem("mail") ? sessionStorage.getItem("mail") : ""}/>
            </div>
            <div style={{borderTop: "2px solid #C3C3C3 ", marginLeft: 70, marginRight: 70}}></div>
            <div className="interests-card">
                <NewInterestsTable/>
            </div>
        </div>

    );
}