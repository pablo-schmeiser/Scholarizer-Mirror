describe('Author compare functionality', () => {
    it('Searches for an author and clicks on compare button', () => {
        cy.visit('/');
        cy.get('.advanced-search-button-button').click(); // click on the "Advanced Search" button
        cy.get('input[name="author"]').click(); // select the "Author" option
        cy.get('input[type="submit"]').click(); // click on the "Apply Filters" button
        cy.get('input[type="home-search"]').type('Lokesh Siddhu{enter}');
        cy.wait(10000); // wait for the API call to complete
        cy.url().should('include', '/searchresults/Lokesh%20Siddhu');
        cy.contains('Lokesh Siddhu').should('be.visible');
        cy.get('div.compare-button').click(); // click on the compare button "div.compare-button"
        cy.url().should('include', '/compare');
        cy.contains('a.author-name', 'Lokesh Siddhu').should(($button) => {
            expect($button.text().trim()).to.equal('Lokesh Siddhu');
        });
    });
});

describe('Author compare functionality', () => {
    it('Searches for an author and clicks on compare button, then searches for another to include him to the comparison', () => {
        cy.visit('/');
        cy.get('.advanced-search-button-button').click(); // click on the "Advanced Search" button
        cy.get('input[name="author"]').click(); // select the "Author" option
        cy.get('input[type="submit"]').click(); // click on the "Apply Filters" button
        cy.get('input[type="home-search"]').type('Heba Khdr{enter}');
        cy.wait(20000); // wait for the API call to complete
        cy.url().should('include', '/searchresults/Heba%20Khdr');
        cy.contains('Heba Khdr').should('be.visible');
        cy.get('div.compare-button').first().click(); // click on the compare button "div.compare-button"
        cy.url().should('include', '/compare');
        cy.contains('a.author-name', 'Heba Khdr').should(($button) => {
            expect($button.text().trim()).to.equal('Heba Khdr');
        });
        cy.get('.navbar-logo').click(); // click on the logo with class ".navbar-logo"
        cy.get('.advanced-search-button-button').click(); // click on the "Advanced Search" button
        cy.get('input[name="author"]').click(); // select the "Author" option
        cy.get('input[type="submit"]').click(); // click on the "Apply Filters" button
        cy.get('input[type="home-search"]').type('Lokesh Siddhu{enter}');
        cy.wait(10000); // wait for the API call to complete
        cy.url().should('include', '/searchresults/Lokesh%20Siddhu');
        cy.contains('Lokesh Siddhu').should('be.visible');
        cy.get('div.compare-button').click(); // click on the compare button "div.compare-button"
        cy.url().should('include', '/compare');
        cy.contains('a.author-name', 'Heba Khdr').should(($button) => {
            expect($button.text().trim()).to.equal('Heba Khdr');
        });
        cy.contains('a.author-name', 'Lokesh Siddhu').should(($button) => {
            expect($button.text().trim()).to.equal('Lokesh Siddhu');
        });
    });
});

describe('Author compare functionality', () => {
    it('Searches for an author and clicks on compare button, then searches for another to include him to the comparison,' +
        'then removes one and only the remaining one should be shown', () => {
        cy.visit('/');
        cy.get('.advanced-search-button-button').click(); // click on the "Advanced Search" button
        cy.get('input[name="author"]').click(); // select the "Author" option
        cy.get('input[type="submit"]').click(); // click on the "Apply Filters" button
        cy.get('input[type="home-search"]').type('Heba Khdr{enter}');
        cy.wait(10000); // wait for the API call to complete
        cy.url().should('include', '/searchresults/Heba%20Khdr');
        cy.contains('Heba Khdr').should('be.visible');
        cy.get('div.compare-button').first().click(); // click on the compare button "div.compare-button"
        cy.url().should('include', '/compare');
        cy.contains('a.author-name', 'Heba Khdr').should(($button) => {
            expect($button.text().trim()).to.equal('Heba Khdr');
        });
        cy.get('.navbar-logo').click(); // click on the logo with class ".navbar-logo"
        cy.get('.advanced-search-button-button').click(); // click on the "Advanced Search" button
        cy.get('input[name="author"]').click(); // select the "Author" option
        cy.get('input[type="submit"]').click(); // click on the "Apply Filters" button
        cy.get('input[type="home-search"]').type('Lokesh Siddhu{enter}');
        cy.wait(10000); // wait for the API call to complete
        cy.url().should('include', '/searchresults/Lokesh%20Siddhu');
        cy.contains('Lokesh Siddhu').should('be.visible');
        cy.get('div.compare-button').click(); // click on the compare button "div.compare-button"
        cy.url().should('include', '/compare');
        cy.contains('a.author-name', 'Heba Khdr').should(($button) => {
            expect($button.text().trim()).to.equal('Heba Khdr');
        });
        cy.contains('a.author-name', 'Lokesh Siddhu').should(($button) => {
            expect($button.text().trim()).to.equal('Lokesh Siddhu');
        });
        cy.get('button.remove-scholar-button').first().click(); // click on the remove button of the first author "button.remove-scholar-button"
        cy.contains('a.author-name', 'Lokesh Siddhu').should(($button) => {
            expect($button.text().trim()).to.equal('Lokesh Siddhu');
        });
    });
});