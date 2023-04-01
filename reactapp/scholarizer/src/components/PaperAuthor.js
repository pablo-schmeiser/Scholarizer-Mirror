import React from 'react';
import { useNavigate } from 'react-router-dom';
import { passAuthorToPage } from "./pages/AuthorProfilePage";
import './PaperAuthor.css'

function PaperAuthor({ author }) {
    const navigate = useNavigate();

    const navigateToAuthor = () => {
        passAuthorToPage(author);
        navigate(`/authorprofile/${encodeURIComponent(author.name)}`);
    };

    return (
        <a>
            {" " + author.name}
        </a>
    );
}

export default PaperAuthor;
