import Link from "@mui/joy/Link";
import React, {useState} from "react";
import {addFollowRequest, deleteFollowRequest} from "./requests/followRequests";
import {useNavigate} from "react-router-dom";


export default function FollowButton({author}) {
    const [state, setState] = useState(author);
    const [click, setClick] = useState(false);
    const navigate = useNavigate();

    const handleClick = () => {
        if (!sessionStorage.getItem("mail") || !sessionStorage.getItem("token")) {
            navigate("/log-in");
            setClick(false);
            return;
        }

        if (click) {
            deleteFollowRequest(state, setClick);
        } else {
            addFollowRequest(state, setClick);
        }
    }

    return (
        <Link
            component="button"
            underline="none"
            fontSize="sm"
            fontWeight="lg"
            textColor="text.primary"
            marginBottom={1}
        >
            <div className='follow-button' onClick={handleClick}>
                {click ? "Unfollow" : "Follow"}
            </div>
        </Link>
    );
}