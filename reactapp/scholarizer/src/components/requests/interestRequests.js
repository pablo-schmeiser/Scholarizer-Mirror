import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

export function addInterestsRequest(interests, errorFunc) {
    const options = {
        method: 'PUT',
        body: interests.join(","),
        redirect: 'follow'
    }


    fetch(PATH + 'account/add-interests?token=' + sessionStorage.getItem('token'), options)
        .then(
            (res) => {
                if (res.status !== 200) {
                    return Promise.reject(res.status);
                }
                errorFunc("");
            }
        ).catch(
        (error) => {
            console.log("followRequests.js: error: " + error);
            errorFunc("Oops! Something went wrong. Please try again.");
        })
}

export function removeInterestsRequest(interest) {
    const options = {
        method: 'PUT',
        body: interest,
        redirect: 'follow'
    }
    console.log(options);


    fetch(PATH + 'account/remove-interests?token=' + sessionStorage.getItem('token'), options)
        .then(
            (res) => res.status === 200 ? res.json() : Promise.reject(res.status)
        ).catch(
        (error) => {
            console.log("followRequests.js: error: " + error);
        })
}

export function getInterestsRequest(functionToCall) {
    const options = {
        method: 'GET',
        redirect: 'follow'
    }

    fetch(PATH + 'account/interests?token=' + sessionStorage.getItem('token'), options)
        .then(
            (res) => res.status === 200 ? res.json() : Promise.reject(res.status)
        ).then(
            (result) => {
                functionToCall(result);
            }
    ).catch(
        (error) => {
            console.log("interestRequests.js: error: " + error);
        }
    )
}