import * as React from "react";


function IconCheckmarkCircleOutline(props) {
    return (
        <svg
            viewBox="0 0 512 512"
            fill="green"
            height="1em"
            width="1em"
            {...props}
        >
            <path
                fill="none"
                stroke="green"
                strokeMiterlimit={10}
                strokeWidth={32}
                d="M448 256c0-106-86-192-192-192S64 150 64 256s86 192 192 192 192-86 192-192z"
            />
            <path
                fill="none"
                stroke="green"
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={32}
                d="M352 176L217.6 336 160 272"
            />
        </svg>
    );
}

export default IconCheckmarkCircleOutline;