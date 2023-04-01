import {BACKEND} from "../../WebAddresses";

const PATH = BACKEND;

export function changeEmailRequest (newMail, errorFunc, successFunc) {
    const options = {
        method: 'PUT',
        body: newMail
    }

    fetch(PATH + 'account/change-mail?token=' + sessionStorage.getItem("token"), options)
        .then((res) => res.status === 200 ? res.text() : Promise.reject(res.status))
        .then((res) => {
            successFunc("Email changed successfully.");
        }
        ).catch(error => {
            errorFunc(error);
        });
}

export function changePasswordRequest (newPassword, errorFunc, successFunc) {
    const options = {
        method: 'PUT',
        body: newPassword
    }

    fetch(PATH + 'account/change-password?token=' + sessionStorage.getItem("token"), options)
        .then((res) => res.status === 200 ? res.text() : Promise.reject(res.status))
        .then((res) => {
            successFunc("Password changed successfully.");
        }
        ).catch(error => {
            errorFunc(error);
        });
}