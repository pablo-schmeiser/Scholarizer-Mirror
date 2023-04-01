import {passAuthorToPage} from "../pages/AuthorProfilePage";
import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

export function requestAuthorFromId(id) {
    const options = {
        method: 'GET',
        redirect: 'follow'
    }

    return fetch(PATH + 'author_lookup/' + id, options)
        .then(
            res => res.text()
        ).then(
            res => {
                try {
                    passAuthorToPage(JSON.parse(res));
                } catch (e) {
                    return e;
                }
            }
        ).catch(
            (error) => {
                console.log("requestAuthorFromId.js: error: " + error);
            })
}