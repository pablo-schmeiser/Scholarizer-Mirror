import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

export function addFollowRequest(follow, funcToCall, mockRequired = false) {
    console.log("followRequests.js: addFollowRequest() called");
    console.log(follow);
    console.log(sessionStorage.getItem('token'));
    const options = {
        method: 'POST',
        headers: {
            'Authentication': sessionStorage.getItem('token'),
            'Content-Type': 'application/json'
        },
        body: mockRequired
            ? {
                type: "author", id: follow.id, citations: 0, nonSelfAndCoAuthorCitations: 0, name: follow.name,
                nonSelfCitations: 0, nonCoAuthorCitations: 0, affiliations: [], frequentCoAuthors: null,
                frequentCiters: null, indices: [], papers: null
            }
            : JSON.stringify(follow),
        redirect: 'follow'
    }
    console.log(options);

    fetch(PATH + 'follow', options)
        .then(
            (res) => {
                if (res.status === 200) {
                    funcToCall(true);
                } else {
                    return Promise.reject(res.status);
                }
            }
        ).catch(
        (error) => {
            console.log("followRequests.js: error: " + error);
        })
}


export function deleteFollowRequest(follow, funcToCall) {
    console.log("followRequests.js: deleteFollowRequest() called");
    console.log(follow);
    console.log(sessionStorage.getItem('token'));
    const options = {
        method: 'DELETE',
        headers: {
            'Authentication': sessionStorage.getItem('token')
        },
        redirect: 'follow'
    }
    console.log(options);

    fetch(PATH + 'follow/' + follow["id"], options)
        .then(
            (res) => {
                if (res.status === 200) {
                    funcToCall(false);
                } else {
                    return Promise.reject(res.status);
                }
            }
        ).catch(
        (error) => {
            console.log("followRequests.js: error: " + error);
        })
}