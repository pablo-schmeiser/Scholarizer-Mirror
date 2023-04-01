import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

export function exploreRequest(errorFunc, successFunc) {
    const options = {
        method: 'GET',
        headers: {
            'Authentication': sessionStorage.getItem('token')
        },
        redirect: 'follow'
    }

    fetch(PATH + 'recommended', options)
        .then(
            (res) => res.status === 200 ? res.text() : Promise.reject(res.status)
        ).then((res) => {
            try {
                successFunc(JSON.parse(res));
            } catch (error) {
                errorFunc(error);
            }
        }
    ).catch(
        (error) => {
            errorFunc(error);
        }
    )
}