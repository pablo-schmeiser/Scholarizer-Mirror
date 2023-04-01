import PaperAuthor from "./PaperAuthor";

export default function PaperAuthorFactory({paperAuthors}) {
    let authors = [];
    for (let i = 0; i < paperAuthors.length; i++) {
        if (i !== paperAuthors.length - 1) {
            authors.push(<>{" " + paperAuthors[i]["name"] + ","}</>);
        } else {
            authors.push(<>{" " + paperAuthors[i]["name"]}</>);
        }
    }


    return (
        <>
            {authors
                .map((item) => {
                    return item
                })
            }
        </>
    )
}