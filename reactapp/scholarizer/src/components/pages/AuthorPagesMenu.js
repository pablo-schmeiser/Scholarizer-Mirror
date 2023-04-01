import React from 'react'
import Link from '@mui/joy/Link';
import './AuthorPagesMenu.css'


export default function AuthorPagesMenu() {


    return (
        <>
            <h1>
                <div className='menu-items'>
                    <div className='menu-item'>
                        <Link
                            component="button"
                            underline="none"
                            fontSize="sm"
                            fontWeight="lg"
                            textColor="text.secondary"
                        >
                            <Link className='link'>
                                Publications
                            </Link>
                        </Link>
                    </div>
                    <div className='menu-item'>
                        <Link
                            component="button"
                            underline="none"
                            fontSize="sm"
                            fontWeight="lg"
                            textColor="text.secondary"
                        >
                            <Link className='link'>
                                Statistics
                            </Link>
                        </Link>
                    </div>
                </div>
            </h1>

        </>

    )
}


