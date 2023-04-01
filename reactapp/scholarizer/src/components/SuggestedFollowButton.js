import Link from "@mui/joy/Link";
import * as React from "react";
import {addFollowRequest, deleteFollowRequest} from "./requests/followRequests";


export default function SuggestedFollowButton({author}) {
    const [state, setState] = React.useState(author);
    const [click, setClick] = React.useState(false);

    function handleClick() {
        console.log("handleClick()");
        console.log(author);
        if (click) {
            console.log("deleteFollowRequest(state)");
            deleteFollowRequest(state, setClick);
        } else {
            console.log("addFollowRequest(state)");
            addFollowRequest(state, setClick, true);
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
            onClick={handleClick}
        >
            {click ? "-" : "+"}
        </Link>
    );
}