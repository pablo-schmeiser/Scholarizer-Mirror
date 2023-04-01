import React, {useState} from 'react'
import './CompareAuthorsList.css'
import {setGlobalAuthors} from "./ComparePage";
import {Link as LinkTo} from "react-router-dom";


export default function CompareAuthorsList({authors, refreshFunc}) {
    const [inputArr, setInputArr] = useState(authors)
    const [inputData, setInputData] = useState({name: "", rollNo: ""})


    function handleChange(e) {

        setInputData({
            ...inputData,
            [e.target.name]: e.target.value
        })
    }

    let handleDelete = (i) => {
        let newDataArr = [...inputArr];
        newDataArr.splice(i, 1);
        //TODO: Check if this is the right way to update the global authors
        setGlobalAuthors(newDataArr);
        setInputArr(newDataArr)
        refreshFunc();
    }


    return (
        <div className="compare-author-list">
            {/*
            <input className='input-scholar-data'
                   type="text"
                   autoComplete='off'
                   name='name'
                   value={inputData.name}
                   onChange={handleChange}
                   placeholder="Enter Scholar Name"/>
                   you could add here scholar ID to check for unique author
            <input className='input-scholar-data'
                type="text"
                autoComplete='off'
                name='rollNo'
                value={inputdata.rollNo}
                onChange={changehandle}
                placeholder="Scholar ID"/>

            <button className='add-scholar-button' /*TODO: onClick={ Add method to set search }>Add Scholar
            </button>
            <br/>
            for Domi, this could be helpful to track the back end logic, i have a video that explains it
            <button className='add-scholar-button' onClick={changhandle2}>Check Array in console
            </button><br/><br/> */}

            <table className='selected-authors-table' border={1} width="100%" cellPadding={10}>
                <tbody>
                <tr>
                    <th>Scholar Name</th>
                    {/*<th>Scholar ID</th>*/}
                    <th>Options</th>
                </tr>
                {inputArr.length < 1 ?
                    <tr>
                        <td className='empty-message' colSpan={4}>No Scholars Selected</td>
                    </tr> :
                    inputArr.map((author, index) => {
                        return (
                            <tr key={index}>
                                <td className='table-data'>
                                    {/* TODO: Fix onClick */}
                                    <LinkTo className='author-name'>
                                        {author.name}
                                    </LinkTo>
                                </td>
                                {/*<td className='table-data'>{info.rollNo}</td>*/}
                                <td className='table-data'>
                                    <button className='remove-scholar-button' onClick={() => handleDelete(index)}>
                                        Remove
                                    </button>
                                </td>
                            </tr>
                        )
                    })
                }
                </tbody>
            </table>
        </div>
    );
}
