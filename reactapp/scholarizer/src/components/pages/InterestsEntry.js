import * as React from 'react';
import Chip from '@mui/material/Chip';
import Stack from '@mui/material/Stack';
import DeleteIcon from '@mui/icons-material/Delete';

export default function InterestsEntry({data, deleteFunc}) {
    const handleClick = () => {
        console.info('You clicked the Chip.');
    };

    const handleDelete = () => {
        deleteFunc();
    };

    return (
        <Stack direction="row" spacing={1}>
            <Chip
                label={data}
                onClick={handleClick}
                onDelete={handleDelete}
                deleteIcon={<DeleteIcon/>}
                variant="outlined"
            />
        </Stack>
    );
}