describe('Basic Search functionality', () => {
    it('Searches and displays results', () => {
        cy.visit('/');
        cy.get('input[type="home-search"]').type('Lokesh Siddhu{enter}');
        cy.wait(30000); // wait for 30 seconds for the API call to complete
        cy.url().should('include', '/searchresults/Lokesh%20Siddhu');
    });
});

describe('Basic Search functionality', () => {
    it('Searches and displays results and goes back to homepage by clicking on the logo', () => {
        cy.visit('/');
        cy.get('input[type="home-search"]').type('Lokesh Siddhu{enter}');
        cy.wait(30000); // wait for 30 seconds for the API call to complete
        cy.url().should('include', '/searchresults/Lokesh%20Siddhu');
        cy.get('.navbar-logo').click(); // click on the logo with class ".navbar-logo"
    });
});
