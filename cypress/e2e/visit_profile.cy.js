describe('Search for author and visit his profile', () => {
    it('Searches for an author and visits his profile', () => {
        cy.visit('/');
        cy.get('.advanced-search-button-button').click(); // click on the "Advanced Search" button
        cy.get('input[name="author"]').click(); // select the "Author" option
        cy.get('input[type="submit"]').click(); // click on the "Apply Filters" button
        cy.get('input[type="home-search"]').type('Lokesh Siddhu{enter}');
        cy.wait(10000); // wait for the API call to complete
        cy.url().should('include', '/searchresults/Lokesh%20Siddhu');
        cy.contains('Lokesh Siddhu').should('be.visible');
        cy.get('a.author-name').click(); // click on the button with class "a.author-name"
        cy.url().should('include', '/authorprofile/Lokesh%20Siddhu');
    });
});
