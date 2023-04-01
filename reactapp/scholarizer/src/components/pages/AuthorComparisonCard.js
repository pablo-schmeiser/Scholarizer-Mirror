import React from 'react'
import Card from '@mui/joy/Card';
import Avatar from '@mui/joy/Avatar';
import './AuthorComparisonCard.css'

export default function AuthorComparisonCard() {
    return (
        <>
            <div className='authorcompcard'>

                <Card className="author-comparison--card"
                      variant="plain"
                      sx={{
                          minWidth: 250,
                          minHeight: 850,
                          '--Card-radius': (theme) => theme.vars.radius.xs,
                          borderWidth: 1,
                      }}
                >
                    <div className='author-profile-picture'>
                        <Avatar
                            size="lg"
                            src="/static/logo.png"
                            sx={{
                                border: '2px solid',
                                borderColor: 'background.body',
                                width: 150,
                                height: 150,
                                marginLeft: 5
                            }}
                        />
                    </div>

                    <div className='author-comparison-name'>
                        Author Name
                    </div>
                    <div className='author-occupation'>
                        University of Chicago
                    </div>
                    <div className='statistics-title'>
                        Statistics
                    </div>
                    {
                        /*
                        <div className='comparison-graph'>
                            <ComparisonRadarChart></ComparisonRadarChart>
                        </div>
                        */
                    }
                    <div className='author-comparison-statistics'>

                        <div className='author-comparison-stat-item'>
                            <div className='author-comparison-stat-name'>
                                Papers Published
                            </div>
                            <div className='author-comparison-stat-value'>
                                100
                            </div>

                        </div>

                        <div className='author-comparison-stat-item'>
                            <div className='author-comparison-stat-name'>
                                Total Citations
                            </div>
                            <div className='author-comparison-stat-value-total'>
                                3200
                            </div>
                            <div className='author-comparison-stat-value-no-self'>
                                3120
                            </div>
                            <div className='author-comparison-stat-value-no-coauthors'>
                                3050
                            </div>
                            <div className='author-comparison-stat-value-no-self-no-coauthors'>
                                2940
                            </div>

                        </div>

                        <div className='author-comparison-stat-item'>
                            <div className='author-comparison-stat-name'>
                                h-Index
                            </div>
                            <div className='author-comparison-stat-value-total'>
                                78
                            </div>
                            <div className='author-comparison-stat-value-no-self'>
                                72
                            </div>
                            <div className='author-comparison-stat-value-no-coauthors'>
                                69
                            </div>
                            <div className='author-comparison-stat-value-no-self-no-coauthors'>
                                65
                            </div>

                        </div>

                        <div className='author-comparison-stat-item'>
                            <div className='author-comparison-stat-name'>
                                i10-Index
                            </div>
                            <div className='author-comparison-stat-value-total'>
                                88
                            </div>
                            <div className='author-comparison-stat-value-no-self'>
                                84
                            </div>
                            <div className='author-comparison-stat-value-no-coauthors'>
                                80
                            </div>
                            <div className='author-comparison-stat-value-no-self-no-coauthors'>
                                78
                            </div>

                        </div>

                    </div>

                </Card>
            </div>
        </>
    )
}
