import IconButton from "@mui/joy/IconButton";
import BookmarkIcon from "@mui/icons-material/Bookmark";
import BookmarkBorderRoundedIcon from "@mui/icons-material/BookmarkBorderRounded";
import * as React from "react";
import {addBookmarkRequest, deleteBookmarkRequest} from "./requests/bookmarkRequests";
import {useNavigate} from "react-router-dom";


export default function BookmarkButton({publication}) {
    const [state, setState] = React.useState(publication);
    const [click, setClick] = React.useState(false);
    const navigate = useNavigate();


    const handleClick = () => {
        if (!sessionStorage.getItem("mail") || !sessionStorage.getItem("token")) {
            setClick(false);
            navigate("/log-in");
            return;
        }

        if (click) {
            deleteBookmarkRequest(state);
        } else {
            addBookmarkRequest(state);
        }
        setClick(!click);
    }

    return (
        <div className='bookmark-button'>
            <IconButton variant="plain" color="neutral" size="sm" sx={{ml: 'auto'}} onClick={handleClick}>
                {click ? <BookmarkIcon/> : <BookmarkBorderRoundedIcon/>}
            </IconButton>
        </div>
    );
}