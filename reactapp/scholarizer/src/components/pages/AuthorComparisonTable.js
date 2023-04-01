import React from 'react'
import Card from '@mui/joy/Card';
import AuthorComparisonCard from './AuthorComparisonCard';
import './AuthorComparisonTable.css'

export default function AuthorComparisonTable() {
    return (
        <>
            <div className='authorcomptable'>
                <Card className="author-comparison-cards"
                      variant="plain"
                      sx={{
                          minWidth: 1000,
                          minHeight: 850,
                          '--Card-radius': (theme) => theme.vars.radius.xs,
                          borderWidth: 1,
                      }}
                >

                    <div className='cardstable'>
                        <AuthorComparisonCard></AuthorComparisonCard>
                        <AuthorComparisonCard></AuthorComparisonCard>
                        <AuthorComparisonCard></AuthorComparisonCard>
                        <AuthorComparisonCard></AuthorComparisonCard>

                    </div>


                </Card>
            </div>
        </>
    )
}
