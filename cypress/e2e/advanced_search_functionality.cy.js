describe('Advanced Search functionality', () => {
    it('Searches for an author and displays results', () => {
        cy.visit('/');
        cy.get('.advanced-search-button-button').click(); // click on the "Advanced Search" button
        cy.get('input[name="author"]').click(); // select the "Author" option
        cy.get('input[type="submit"]').click(); // click on the "Apply Filters" button
        cy.get('input[type="home-search"]').type('Lokesh Siddhu{enter}');
        cy.wait(10000); // wait for the API call to complete
        cy.url().should('include', '/searchresults/Lokesh%20Siddhu');
        cy.contains('Lokesh Siddhu').should('be.visible');
    });
});

describe('Advanced Search functionality', () => {
    it('Searches for papers and displays results', () => {
        cy.visit('/');
        cy.get('.advanced-search-button-button').click(); // click on the "Advanced Search" button
        cy.get('input[name="paper"]').click(); // select the "Paper" option
        cy.get('input[type="submit"]').click(); // click on the "Apply Filters" button
        cy.get('input[type="home-search"]').type('Applications of Ion Chromatography in the Analysis of Pharmaceutical and Biological Products{enter}');
        cy.wait(30000); // wait for the API call to complete
        cy.url().should('include', '/searchresults/Applications%20of%20Ion%20Chromatography%20in%20the%20Analysis%20of%20Pharmaceutical%20and%20Biological%20Products');
        cy.contains('Applications of Ion Chromatography in the Analysis of Pharmaceutical and Biological Products').should('be.visible');
    });
});