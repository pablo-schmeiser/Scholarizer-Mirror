/* eslint-disable jsx-a11y/anchor-is-valid */
import * as React from 'react';
import Box from '@mui/joy/Box';
import Card from '@mui/joy/Card';
import CardOverflow from '@mui/joy/CardOverflow';
import Link from '@mui/joy/Link';
import './ProfileCard.css'
import {useState} from "react";
import {useNavigate} from "react-router-dom";

export default function ProfileCard(props = {mail: ""}) {
    const [mail, setMail] = useState(props.mail);
    const [password, setPassword] = useState("");
    const [passwordInput, setPasswordInput] = useState("");
    const [passwordConfirm, setPasswordConfirm] = useState("");
    const [mailInput, setMailInput] = useState(mail);

    const [passwordErrorMessage, setPasswordErrorMessage] = useState("");
    const [mailErrorMessage, setMailErrorMessage] = useState("");

    const [passwordActive, setPasswordActive] = useState(false);
    const [mailActive, setMailActive] = useState(false);

    const navigate = useNavigate();

    const isEducational = (email) => {
        if (email === "") {
            return false;
        }
        const emailParts = email.split("@")[1].split(".");
        return (emailParts[emailParts.length - 1] === "edu");
    }


    return (
        <>
            <Card className="card"
                  variant="plain"
                  sx={{
                      minWidth: 720, '--Card-radius': (theme) => theme.vars.radius.xs, borderWidth: 1,
                  }}
            >
                <div className='header-box'>

                </div>
                <div className='user-table'>
                    {/*
                    <div className='profile-picture'>
                        <Box
                            sx={{
                                position: 'relative', '&:before': {
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
                                    p: 0.1, border: '2px solid', borderColor: 'background.body', width: 150, height: 150
                                }}
                            />
                        </Box>
                    </div>
                    */}
                    <div className='user-data'>
                        <CardOverflow>
                            <Link
                                component="button"
                                underline="none"
                                fontSize="sm"
                                fontWeight="lg"
                                textColor="text.primary"
                                marginRight={1}

                            >
                                Email:
                            </Link>
                            {mail && mail !== "" ? mail : "No mail was identified."}
                        </CardOverflow>
                        <CardOverflow>
                            <Link
                                component="button"
                                underline="none"
                                fontSize="sm"
                                fontWeight="lg"
                                textColor="text.primary"
                                marginRight={2}
                            >
                                University:
                            </Link>
                            {isEducational(mail) ? mail.split("@")[1].split(".")[0] : "No educational mail was identified."}
                        </CardOverflow>
                        <Box sx={{display: 'flex', alignItems: 'center', mx: -1, my: 1}}>
                        </Box>
                        <Link
                            component="button"
                            underline="none"
                            fontSize="sm"
                            fontWeight="lg"
                            textColor="text.primary"
                            marginBottom={2}
                            onClick={() => {
                                navigate("/change-password");
                            }}
                        >
                            <div className='reset'>
                                Change Password
                            </div>
                        </Link>
                            <Link
                                component="button"
                                underline="none"
                                fontSize="sm"
                                fontWeight="lg"
                                textColor="text.primary"
                                marginBottom={2}
                                onClick={() => {
                                        navigate("/change-email");
                                    }
                                }
                            >
                                <div className='reset'>
                                    Change Email
                                </div>
                            </Link>
                    </div>
                </div>
            </Card>
        </>
    );
}