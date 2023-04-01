describe('Basic page switching', () => {
  it('Switches from the landing page to the login area', () => {
    cy.visit('/');
    cy.get('nav').contains('Log In').click();
    cy.url().should('include', '/log-in');
  });
});

describe('Basic page switching', () => {
  it('Switches from the landing page to the profile area, but gets redirected to login area, when not logged in', () => {
    cy.visit('/');
    cy.get('nav').contains('Profile').click();
    cy.url().should('include', '/log-in');
  });
});

describe('Basic page switching', () => {
  it('Switches from the landing page to the profile area, but gets redirected to login area, when not logged in,' +
      'then clicks on the logo and gets redirected to homepage again', () => {
    cy.visit('/');
    cy.get('nav').contains('Profile').click();
    cy.url().should('include', '/log-in');
    cy.get('.navbar-logo').click();
    cy.url().should('include', '');
  });
});