import React from 'react';
import {render, fireEvent, getByText, getByAltText} from '@testing-library/react';
import Login from './LogIn';
import LandingPage from "./LandingPage";

describe('Login component', () => {

    // test case to check whether you get redirected to the landing page when clicking on the logo
    // can't access the logo atm
    it('redirects to the landing page when clicking the logo', () => {
        const {getByRole} = render(<Login/>);
        const logo = getByRole('root/navbar/navbar-container/logo');
        fireEvent.click(logo);
        expect(window.location.pathname).toBe('/');
    });

    // test case to check whether the text "Login" is shown multiple times on the website
    it('renders multiple login buttons', async () => {
        const {findAllByText} = render(<Login/>);
        const buttons = await findAllByText('Login');
        expect(buttons.length).toBe(2);
    });
})