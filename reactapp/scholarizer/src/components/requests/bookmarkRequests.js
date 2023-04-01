import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

export function getBookmarksRequest(funcToCall) {
    const options = {
        method: 'GET',
        headers: {
            'Authentication': sessionStorage.getItem('token')
        },
        redirect: 'follow'
    }

    fetch(PATH + 'bookmark', options)
        .then(
            (res) => res.status === 200 ? res.json() : Promise.reject(res.status)
        ).then(
            (result) => {
            console.log("bookmarkRequests.js: result: " + result);
            funcToCall(result);
        }
    ).catch(
        (error) => {
            console.log("bookmarkRequests.js: error: " + error);
        }
    )
}

export function addBookmarkRequest(publication) {
    const options = {
        method: 'POST',
        headers: {
            'Authentication': sessionStorage.getItem('token'),
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(publication),
        redirect: 'follow'
    }

    fetch(PATH + 'bookmark', options)
        .then(
            (res) => res.status === 200 ? res.json() : Promise.reject(res.status)
        ).catch(
        (error) => {
            console.log("bookmarkRequests.js: error: " + error);
        }
    )
}

export function deleteBookmarkRequest(publication) {
    const options = {
        method: 'DELETE',
        headers: {
            'Authentication': sessionStorage.getItem('token')
        },
        redirect: 'follow'
    }

    fetch(PATH + 'bookmark/' + publication["id"], options)
        .then(
            (res) => res.status === 200 ? res.json() : Promise.reject(res.status)
        ).catch(
        (error) => {
            console.log("bookmarkRequests.js: error: " + error);
        }
    )
}