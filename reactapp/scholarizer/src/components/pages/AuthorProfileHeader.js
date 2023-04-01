/* eslint-disable jsx-a11y/anchor-is-valid */
import React, { useState } from 'react';
import Avatar from '@mui/joy/Avatar';
import Box from '@mui/joy/Box';
import Card from '@mui/joy/Card';


import {Link as LinkTo} from 'react-router-dom'

import './AuthorProfileHeader.css'
import {passAuthorToComparison} from "./ComparePage";
import {passAuthorToPage} from "./AuthorProfilePage";
import FollowButton from "../FollowButton";
import StatisticsTable from "./StatisticsTable";

export default function AuthorProfileHeader({author}) {
    const [menuVisible, setMenuVisible] = useState(false);
    let [type, setType] = useState(null);


    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);

        setType(data.get("type"));
    };

    const handleCompare = () => {
        passAuthorToComparison({author});
    };

    const handleProfileClick = () => {
        passAuthorToPage({author});
    };

    const toggleMenu = () => {
        setMenuVisible(!menuVisible);
    };

    const generateIndex = ({i}) => {
        return (
            <div className='stat-item'>
                <div className='stat-name'>
                    <h5
                        component="button"
                        underline="none"
                        fontSize="sm"
                        fontWeight="lg"
                        textColor="text.primary"
                        marginRight={1}
                    >
                        {author["indices"][i]["index"].toLowerCase().replaceAll("\_", "-")
                            + author["indices"][i]["source"].toLowerCase().replaceAll("\_", " ").replace("without", " w/o").replace("standard", "")}
                    </h5>
                </div>
                <div className='stat-value'>
                    {author["indices"][i]["value"]}
                </div>
            </div>
        )
    }

    const generateIndices = () => {
        let indices = [];
        for (let i = 0; i < author["indices"].length; i++) {
            indices.push(generateIndex({i}));
        }


        return (
            <>
                {
                    indices.map((item) => {
                        return item;
                    })
                }
            </>
        )
    }

    return (
        <>
            <Card className="author-card"
                  variant="plain"
                  sx={{
                      minWidth: 550,
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
                                    width: 80,
                                    height: 80
                                }}
                            />
                        </Box>
                    </div>
                    <div className='author-data'>
                        <div className='author-name'>
                            <div>
                                <LinkTo className='author-name' onClick={handleProfileClick} to={`/authorprofile/${author.name}`}>
                                    {author["name"] /* should get the authors name */}
                                </LinkTo>
                            </div>

                            <div>
                                {((author["affiliations"] || author["affiliations"] === [""]) && author["affiliations"].length > 0 && author["affiliations"][0] !== "")
                                    ? " -" + author["affiliations"] : "" /* should get the authors affiliation */}
                            </div>
                        </div>
                        <div className="author-statistics-table">
                            <StatisticsTable author={author}/>
                        </div>
                        {/*
                <div className='author-statistics'>
                    <div className='stat-item'>
                        <div className='stat-name'>
                            <h5 to={author["url"]}
                            component="button"
                            underline="none"
                            fontSize="sm"
                            fontWeight="lg"
                            textColor="text.primary"
                            marginRight={1}
                            >
                            Papers Published
                            </h5>
                        </div>
                        <div className='stat-value'>
                            {author["papers"].length /* should get the number of published papers */}
                        {/*
                        </div>
                    </div>

                    <div className='stat-item'>
                        <div className='stat-name'>
                            <h5
                            component="button"
                            underline="none"
                            fontSize="sm"
                            fontWeight="lg"
                            textColor="text.primary"
                            marginRight={1}
                            >
                            Total Citations
                            </h5>
                        </div>
                        <div className='stat-value'>
                            {author["citations"] /* should get the number of citations of the author */}
                        {/**
                         </div>
                         </div>
                         <div className='stat-item'>
                         <div className='stat-name'>
                         <h5
                         component="button"
                         underline="none"
                         fontSize="sm"
                         fontWeight="lg"
                         textColor="text.primary"
                         marginRight={1}
                         >
                         Citations w/o Self Citations
                         </h5>
                         </div>
                         <div className='stat-value'>
                         {author["nonSelfCitations"] /* should get the number of citations of the author */}
                        {/**
                         </div>
                         </div>
                         <div className='stat-item'>
                         <div className='stat-name'>
                         <h5
                         component="button"
                         underline="none"
                         fontSize="sm"
                         fontWeight="lg"
                         textColor="text.primary"
                         marginRight={1}
                         >
                         Citations w/o Co-Author Citations
                         </h5>
                         </div>
                         <div className='stat-value'>
                         {author["nonCoAuthorCitations"] /* should get the number of citations of the author */}
                        {/**
                         </div>
                         </div>
                         <div className='stat-item'>
                         <div className='stat-name'>
                         <h5
                         component="button"
                         underline="none"
                         fontSize="sm"
                         fontWeight="lg"
                         textColor="text.primary"
                         marginRight={1}
                         >
                         Citations w/o Self and Co-Author Citations
                         </h5>
                         </div>
                         <div className='stat-value'>
                         {author["nonSelfAndCoAuthorCitations"] /* should get the number of citations of the author */}
                        {/**
                         </div>
                         </div>
                         {
                        generateIndices()
                    }
                         </div>
                         */}

                        <div className='follow-section'>
                            <div className='follow'>
                                <FollowButton author={author}/>
                            </div>
                            <div className='compare'>
                                <LinkTo className="compare-link" onClick={handleCompare} to="/compare"
                                        component="button"
                                        underline="none"
                                        fontSize="sm"
                                        fontWeight="lg"
                                        textColor="text.primary"
                                        marginBottom={1}
                                >
                                    <div className='compare-button'>
                                        Compare
                                    </div>
                                </LinkTo>
                            </div>
                        </div>
                    </div>
                </div>
            </Card>
        </>
    )
}
