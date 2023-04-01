/* eslint-disable jsx-a11y/anchor-is-valid */
import * as React from 'react';
import Card from '@mui/joy/Card';
import Typography from '@mui/joy/Typography';
import './Interests.css'
import {useState} from "react";

export default function Interests(props = {interests: []}) {
    const [interests, setInterests] = useState(props.interests);
    //TODO: Add functionality to add interests
    //TODO: Iterate through interests and display them
    return (
        <Card className="interests-card"
              variant="plain"
              sx={{
                  minWidth: 850,
                  '--Card-radius': (theme) => theme.vars.radius.xs,
                  borderWidth: 1,
              }}
        >

            <div className='interests-table'>
                <div className='interests-title'>
                    <Typography fontWeight="md">Fields of Interests :</Typography>
                </div>
                <div className='interests'>
                    <div className='interest'>
                        Cinema
                    </div>
                    <div className='interest'>
                        Videography
                    </div>
                    <div className='interest'>
                        Poetry
                    </div>
                    <div className='interest'>
                        Physics
                    </div>
                    <div className='interest'>
                        Biology
                    </div>
                    <div className='add-interest'>
                        Add Interests +
                    </div>
                </div>

            </div>


        </Card>
    );

}
