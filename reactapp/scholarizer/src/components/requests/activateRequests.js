import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

export function activateRequest(key, func) {
    const options = {
        method: 'GET',
        redirect: 'follow'
    }

    fetch(PATH + 'activate/' + key, options)
        .then(
            (res) => res.status === 200 && res.body ? func(true) : Promise.reject(res.status)
        ).catch(
        (error) => {
            console.log("bookmarkRequests.js: error: " + error);
        }
    )
}