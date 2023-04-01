/* eslint-disable jsx-a11y/anchor-is-valid */
import * as React from 'react';
import Avatar from '@mui/joy/Avatar';
import Box from '@mui/joy/Box';
import Card from '@mui/joy/Card';
import CardOverflow from '@mui/joy/CardOverflow';
import Link from '@mui/joy/Link';
import './AuthorBanner.css'

export default function ProfileCard() {
    return (
        <>
            <Card className="author-card"
                  variant="outlined"
                  sx={{
                      minWidth: 650,
                      maxWidth: 800,
                      '--Card-radius': (theme) => theme.vars.radius.xs,
                      borderWidth: 1,
                  }}
            >

                <div className='author-table'>
                    <div className='author-profile-picture'>
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
                                    width: 120,
                                    height: 120
                                }}
                            />
                        </Box>
                    </div>
                    <div className='author-data'>
                        <div className='author-name'>
                            <CardOverflow>
                                <Link
                                    component="button"
                                    underline="none"
                                    fontSize="sm"
                                    fontWeight="lg"
                                    textColor="text.primary"
                                    marginRight={1}

                                >
                                    Author Name
                                </Link>
                            </CardOverflow>
                        </div>
                        <div className='author-statistics'>
                            <div className='stat-item'>
                                <div className='stat-name'>
                                    <Link
                                        component="button"
                                        underline="none"
                                        fontSize="sm"
                                        fontWeight="lg"
                                        textColor="text.primary"
                                        marginRight={2}
                                    >
                                        Papers Published
                                    </Link>
                                </div>
                                <div className='stat-value'>
                                    100
                                </div>
                            </div>

                            <div className='stat-item'>
                                <div className='stat-name'>
                                    <Link
                                        component="button"
                                        underline="none"
                                        fontSize="sm"
                                        fontWeight="lg"
                                        textColor="text.primary"
                                        marginRight={2}
                                    >
                                        Total Citations
                                    </Link>
                                </div>
                                <div className='stat-value'>
                                    4.2K
                                </div>
                            </div>
                            <div className='stat-item'>
                                <div className='stat-name'>
                                    <Link
                                        component="button"
                                        underline="none"
                                        fontSize="sm"
                                        fontWeight="lg"
                                        textColor="text.primary"
                                        marginRight={2}
                                    >
                                        h-Index
                                    </Link>
                                </div>
                                <div className='stat-value'>
                                    54
                                </div>
                            </div>
                            <div className='stat-item'>
                                <div className='stat-name'>
                                    <Link
                                        component="button"
                                        underline="none"
                                        fontSize="sm"
                                        fontWeight="lg"
                                        textColor="text.primary"
                                        marginRight={2}
                                    >
                                        i10-Index
                                    </Link>
                                </div>
                                <div className='stat-value'>
                                    48
                                </div>
                            </div>

                        </div>
                        <div className='follow-section'>
                            <div className='follow'>
                                <Link
                                    component="button"
                                    underline="none"
                                    fontSize="sm"
                                    fontWeight="lg"
                                    textColor="text.primary"
                                    marginBottom={2}
                                >
                                    Follow
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>


            </Card>
        </>
    );
}