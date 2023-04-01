/* eslint-disable jsx-a11y/anchor-is-valid */
import * as React from 'react';
import Avatar from '@mui/joy/Avatar';
import Box from '@mui/joy/Box';
import Card from '@mui/joy/Card';
import CardOverflow from '@mui/joy/CardOverflow';
import './SuggestedAuthorCard.css'
import {Link as LinkTo} from 'react-router-dom'
import SuggestedFollowButton from "../SuggestedFollowButton";
import {requestAuthorFromId} from "../requests/requestAuthorFromId";

export default function SuggestedAuthorCard({author}) {
    function handleProfileClick() {
        if (author) {
            console.log("handleProfileClick() called:");
            console.log(author);
            requestAuthorFromId(author["id"]);
        }
    }

    return (
        <>
            <div className='suggested-scholar-card'>
                <Card className="suggested-author-card"
                      variant="plain"
                      sx={{
                          width: 250,
                          '--Card-radius': (theme) => theme.vars.radius.xs,
                          borderWidth: 1
                      }}
                >

                    <div className='suggested-author-table'>
                        <div className='suggested-author-profile-picture'>
                            <Box
                                sx={{
                                    position: 'relative',
                                    '&:before': {
                                        content: '""',
                                        position: 'absolute',
                                        top: 0,
                                        left: 0,
                                        bottom: 0,
                                        right: 0,
                                        m: '-2px',
                                        borderRadius: '50%',
                                    },
                                }}
                            >
                                <Avatar
                                    size="lg"
                                    src="/static/logo.png"
                                    sx={{
                                        p: 0.1,
                                        border: '2px solid',
                                        borderColor: 'background.body',
                                        width: 50,
                                        height: 50
                                    }}
                                />
                            </Box>
                        </div>
                        <div className='suggested-author-data'>
                            <div className='suggested-author-name'>
                                <CardOverflow>
                                    <LinkTo className='suggested-author-name'
                                            component="button"
                                            underline="none"
                                            fontSize="sm"
                                            fontWeight="lg"
                                            textColor="text.primary"
                                            marginRight={1}
                                            onClick={handleProfileClick}
                                            to={"/authorprofile"}
                                    >
                                        {author && author["name"] ? author["name"] : ""}
                                    </LinkTo>
                                </CardOverflow>
                            </div>
                            {/*<div className='suggested-author-statistics'>
                    <CardOverflow>
                        <Link
                            component="button"
                            underline="none"
                            fontSize="sm"
                            fontWeight="lg"
                            textColor="text.primary"
                            marginRight={2}
                        >
                            University of California
                        </Link>
                    </CardOverflow>
                </div>*/}
                            <div className='suggested-follow-section'>
                                <div className='suggested-follow'>
                                    <SuggestedFollowButton author={author}/>
                                </div>
                            </div>
                        </div>
                    </div>


                </Card>
            </div>
        </>
    );
}