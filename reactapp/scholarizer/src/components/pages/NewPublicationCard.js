/* eslint-disable jsx-a11y/anchor-is-valid */
import * as React from 'react';
import Card from '@mui/joy/Card';
import CardOverflow from '@mui/joy/CardOverflow';
import Link from '@mui/joy/Link';
import Typography from '@mui/joy/Typography';
import './NewPublicationCard.css'
import BookmarkButton from "../BookmarkButton";
import ExportButton from "./ExportButton";
import PaperAuthorFactory from "../PaperAuthorFactory";


export default function NewPublicationCard(publication) {
    const limit = 512;


    const limitAbstract = (abstract) => {
        const abstractArray = publication["publication"]["abstractText"].substring(0, limit).split(" ");
        abstractArray.pop();
        return abstractArray.length ? abstractArray.join(" ") + "..." : "No abstract available";
    }


    return (
        <>
            <div className='newpubcard'>
                <Card className="new-publication-card"
                      variant="solid"
                      sx={{
                          width: 750,
                          '--Card-radius': (theme) => theme.vars.radius.xs,
                      }}
                >
                    <div className='new-publication-header'>
                        <div className='new-publication-title'>
                            {publication["publication"]["url"]
                                ? <a href={publication["publication"]["url"]} target="_blank">
                                    {publication["publication"]["title"] /* should get the title of the publication */}
                                </a>
                                : <u>{publication["publication"]["title"]}</u>
                            }
                        </div>
                        <div className="publication-actions">
                            <ExportButton publication={publication.publication}/>
                            <BookmarkButton publication={publication.publication}/>
                        </div>

                    </div>
                    <CardOverflow>
                        <div className='new-abstract'>
                            {
                                limitAbstract(publication["publication"]["abstractText"]) /* should get the abstract of the publication */
                            }
                        </div>
                    </CardOverflow>
                    <div style={{borderTop: "2px solid #C3C3C3 ", marginTop: 10}}></div>
                    <div className='publication-citations'>
                        <Link
                            component="button"
                            underline="none"
                            fontSize="sm"
                            fontWeight="lg"
                            textColor="text.primary"
                        >
                            {publication["publication"]["citations"] + " Citation" + (publication["publication"]["citations"] === 1
                                ? " " : "s ")}
                            {(publication["publication"]["citations"] === publication["publication"]["nonSelfAndCoAuthorCitations"]
                                ? "" : " & " + (publication["publication"]["nonSelfAndCoAuthorCitations"] + " Citation"
                                + (publication["publication"]["nonSelfAndCoAuthorCitations"] === 1
                                    ? "" : "s")) + " w/o Self-& Co-Author Citations")}
                        </Link>
                    </div>

                    <div className='publication-authors'>
                        <Typography fontSize="sm">
                            Authors:
                            <div className="publication-authors">
                                <PaperAuthorFactory paperAuthors={publication["publication"]["authors"]}/>
                            </div>
                        </Typography>
                    </div>
                    <div className='publishing-date'>
                        <Link
                            component="button"
                            underline="none"
                            fontSize="10px"
                            sx={{color: 'text.tertiary', my: 0.5}}
                        >
                            {(publication["publication"]["venueNames"].join(", ") === "N/A" && !publication["publication"]["publicationDate"])
                                ? "" : "Published"}
                            {publication["publication"]["venueNames"].join(", ") === "N/A"
                                ? "" : (" in " + publication["publication"]["venueNames"].join(", "))}
                            {publication["publication"]["publicationDate"]
                                ? (" on " + publication["publication"]["publicationDate"].replaceAll("-", ".") + ".") : ""}
                        </Link>
                    </div>

                </Card>
            </div>
        </>

    );
}
