import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

export function passwordResetRequest(password, token, errorFunc, successFunc) {

    const options = {
        method: 'POST',
        body: password,
        redirect: 'follow'
    }

    fetch(PATH + 'account/reset?token=' + token, options)
        .then(
            (res) => {
                if (res.status === 200) {
                    successFunc("Password reset successfully.");
                } else {
                    return Promise.reject(res.status);
                }
            }
        ).catch(
        (error) => {
            console.log("interestRequests.js: error: " + error);
            errorFunc("Oops! Something went wrong. Please try again.");
        }
    )
}