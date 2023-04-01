import * as React from 'react';
import {styled} from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, {tableCellClasses} from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import "./StatisticsTable.css"
/*theme.palette.common.black
backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
*/
const StyledTableCell = styled(TableCell)(({theme}) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: "#e7e7e7",
        color: theme.palette.common.black,
        fontSize: 11,


    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 11,

    },
}));

const StyledTableRow = styled(TableRow)(({theme}) => ({
    '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
    },
    // hide last border
    '&:last-child td, &:last-child th': {
        border: 0,
    },
}));

function createData(metric, totalstats, stats_without_self, stats_without_coauthors, stats_without_self_and_coauthors) {
    return {metric, totalstats, stats_without_self, stats_without_coauthors, stats_without_self_and_coauthors};
}


export default function StatisticsTable({author}) {
    const rows = [
        createData('Citations', author["citations"], author["nonSelfCitations"], author["nonCoAuthorCitations"], author["nonSelfAndCoAuthorCitations"]),
        createData('h-Index', author["indices"][0]["value"], author["indices"][2]["value"], author["indices"][4]["value"], author["indices"][6]["value"]),
        createData('i10-Index', author["indices"][1]["value"], author["indices"][3]["value"], author["indices"][5]["value"], author["indices"][7]["value"]),

    ];

    return (

        <TableContainer component={Paper}>
            <Table sx={{width: 700, height: 10}} aria-label="customized table">
                <TableHead>
                    <TableRow>
                        <StyledTableCell align="center">Metric</StyledTableCell>
                        <StyledTableCell align="center">Total Statistics</StyledTableCell>
                        <StyledTableCell align="center">Statistics w/o Self Citations</StyledTableCell>
                        <StyledTableCell align="center">Statistics w/o Co-Authors</StyledTableCell>
                        <StyledTableCell align="center">Statistics w/o Self & Co-Authors</StyledTableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row) => (
                        <StyledTableRow key={row.name}>
                            <StyledTableCell component="th" scope="row">
                                {row.metric}
                            </StyledTableCell>
                            <StyledTableCell align="right">{row.totalstats}</StyledTableCell>
                            <StyledTableCell align="right">{row.stats_without_self}</StyledTableCell>
                            <StyledTableCell align="right">{row.stats_without_coauthors}</StyledTableCell>
                            <StyledTableCell align="right">{row.stats_without_self_and_coauthors}</StyledTableCell>
                        </StyledTableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}