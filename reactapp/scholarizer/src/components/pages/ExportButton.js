import IconButton from "@mui/joy/IconButton";
import IosShareIcon from '@mui/icons-material/IosShare';
import {ImQuotesLeft} from 'react-icons/im';
import * as React from "react";


export default function ExportButton({publication}) {
    const [state, setState] = React.useState(publication);
    const [click, setClick] = React.useState(false);


    const handleClick = () => {
        const copyText = state["reference"];
        navigator.clipboard.writeText(copyText);

        alert("Copied the bibTex reference to your clipboard");
    }

    return (
        <div className='bookmark-button'>
            <IconButton variant="plain" color="neutral" size="sm" sx={{ml: 'auto', marginBottom: 0.7}}
                        onClick={handleClick}>
                <ImQuotesLeft/>
            </IconButton>
        </div>
    );
}