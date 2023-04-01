import React, {useState} from 'react'
import './CompareAuthorsList.css'
import './NewInterestTable.css'
import InterestsEntry from "./InterestsEntry";
import {
    addInterestsRequest,
    getInterestsRequest,
    removeInterestsRequest
} from "../requests/interestRequests";

export default function NewInterestsTable() {
    const [inputArr, setInputArr] = useState([]);
    const [inputData, setInputData] = useState("");
    const [isEmpty, setIsEmpty] = useState(false);
    const [error, setError] = useState("");

    if (inputArr.length === 0 && inputData === "" && !isEmpty) {
        getInterestsRequest(setInputArr);
        setIsEmpty(true);
    }

    function handleKeyDown(event) {
        if (event.keyCode === 13) {
            addInterest();
        }
    }

    let handleDelete = (i) => {
        let temp = [...inputArr];
        removeInterestsRequest(temp[i]);
        temp.splice(i, 1);
        setInputArr(temp);
    }

    function addInterest() {
        let temp = [...inputArr];
        temp.push(inputData);
        setInputArr(temp);
        addInterestsRequest(temp, setError);
        setInputData("");
    }

    return (<div className="compare-author-list">
        <input className='input-scholar-data'
               type="text"
               autoComplete='off'
               name='name'
               value={inputData}
               onChange={event => setInputData(event.target.value)}
               onKeyDown={event => handleKeyDown(event)}
               placeholder="Enter New Interest"/>

        <button className='add-scholar-button' onClick={addInterest}>
            Add Interest
        </button>
        {!error ?
            ""
            : <div id={"interests-error"} className={"error-message"}>
                {error}
            </div>
        }
        <br/>

        <table className='selected-interests-table' border={1} width="100%" cellPadding={10}>
            <tbody className="interest-entries">
            <tr-interests/>
            {inputArr.length < 1 ? <tr-interests>
                <td-interests className='empty-message' colSpan={4}>No Interests Added Yet</td-interests>
            </tr-interests> : inputArr.map((value, index) => {
                return (<tr-interests key={index}>
                    <td-interests className='interests-table-data'>
                        <InterestsEntry data={value} deleteFunc={() => handleDelete(index)}/>
                    </td-interests>
                </tr-interests>)
            })}
            </tbody>
        </table>
    </div>);
}
