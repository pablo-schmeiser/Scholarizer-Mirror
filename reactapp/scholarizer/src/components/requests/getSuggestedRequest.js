import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

export function getSuggestedRequest (id, functionToCall) {
    const options = {
        method: 'GET',
        redirect: 'follow'
    }
    fetch(PATH + 'author_lookup/' + id + '/frequents', options)
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