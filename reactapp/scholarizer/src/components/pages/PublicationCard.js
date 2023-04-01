/* eslint-disable jsx-a11y/anchor-is-valid */
import * as React from 'react';
import Avatar from '@mui/joy/Avatar';
import Box from '@mui/joy/Box';
import Card from '@mui/joy/Card';
import CardOverflow from '@mui/joy/CardOverflow';
import Link from '@mui/joy/Link';
import IconButton from '@mui/joy/IconButton';
import Typography from '@mui/joy/Typography';
import BookmarkBorderRoundedIcon from '@mui/icons-material/BookmarkBorderRounded';
import './PublicationCard.css'

export default function PublicationCard() {
    return (
        <Card className="publication-card"
              variant="outlined"
              sx={{
                  minWidth: 650,
                  maxWidth: 800,
                  '--Card-radius': (theme) => theme.vars.radius.xs,

              }}
        >
            <Box sx={{display: 'flex', alignItems: 'center', pb: 1.5, gap: 1}}>
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
                            background:
                                'linear-gradient(45deg, #f09433 0%,#e6683c 25%,#dc2743 50%,#cc2366 75%,#bc1888 100%)',
                        },
                    }}
                >
                    <Avatar
                        size="sm"
                        src="/static/logo.png"
                        sx={{p: 0.5, border: '2px solid', borderColor: 'background.body'}}
                    />
                </Box>
                <div className='publication-author'>
                    <Typography fontWeight="lg">
                        <Link>
                            Author
                        </Link>
                    </Typography>
                </div>
                <IconButton variant="plain" color="neutral" size="sm" sx={{ml: 'auto'}}>
                    <BookmarkBorderRoundedIcon/>
                </IconButton>
            </Box>
            <div className='publication-title'>
                <Link>
                    This Part is For The Title
                </Link>
            </div>
            <CardOverflow>
                <div className='abstract'>
                    This part is meant for the abstract This part is meant for the abstract This part is meant for the
                    abstract
                    This part is meant for the abstract This part is meant for the abstract This part is meant for the
                    abstract
                    This part is meant for the abstract
                </div>
            </CardOverflow>
            <div style={{borderTop: "2px solid #C3C3C3 ", marginTop: 10}}></div>
            <Link
                component="button"
                underline="none"
                fontSize="sm"
                fontWeight="lg"
                textColor="text.primary"
            >
                4.2K Citations
            </Link>
            <Typography fontSize="sm">
                Author:
                <Link
                    component="button"
                    color="neutral"
                    fontWeight="lg"
                    textColor="text.primary"
                    marginLeft={1}
                >
                    Author Name
                </Link>{' '}
            </Typography>
            <Typography fontSize="sm">
                Co-Authors:
                <Link
                    component="button"
                    color="neutral"
                    fontWeight="lg"
                    textColor="text.primary"
                    marginLeft={1}
                >
                    Co-Author1,

                </Link>{' '}
                <Link
                    component="button"
                    color="neutral"
                    fontWeight="lg"
                    textColor="text.primary"
                    marginLeft={1}
                >
                    Co-Author2

                </Link>{' '}

            </Typography>

            <Link
                component="button"
                underline="none"
                fontSize="10px"
                sx={{color: 'text.tertiary', my: 0.5}}
            >
                Published On: 12.20.2019
            </Link>
        </Card>

    );
}