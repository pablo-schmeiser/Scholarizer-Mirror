import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

export const searchRequest = function (query, type, offset, func) {
    const requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };
    fetch(PATH + 'search?query=' + query.replaceAll(" ", "+")
        + (type ? ("&type=" + type) : "") + ("&offset=" + offset), requestOptions)
        .then(res => res.text())
        .then(body => {
            func(JSON.parse(body))
        })
        .catch(
            error => {
                console.error('error', error);
            }
        );
}