describe('Stock Management', () => {
    beforeEach(() => {
      // Replace with the URL of your application
      cy.visit('http://localhost:4200/stock');
    });
  
    it('displays the list of stocks', () => {
      cy.get('h3').contains('List Stock').should('be.visible');
      cy.get('table').should('be.visible');
      cy.get('thead').within(() => {
        cy.contains('Id').should('be.visible');
        cy.contains('Libelle').should('be.visible');
        cy.contains('Quantité').should('be.visible');
        cy.contains('Quantité Min').should('be.visible');
        cy.contains('Action').should('be.visible');
      });
    });
  
    it('adds a new stock', () => {
      cy.get('.btn-success').contains('Add new Stock').click();
      cy.get('#lib').type('New Stock');
      cy.get('#qte').type('100');
      cy.get('#qteM').type('10');
      cy.get('.modal-footer .btn-success').click();
  
      // Add assertions to verify the new stock is in the list
      cy.get('table tbody tr').last().within(() => {
        cy.contains('New Stock');
        cy.contains('100');
        cy.contains('10');
      });
    });
  
    it('edits an existing stock', () => {
      // Assuming there's at least one stock in the list
      cy.get('table tbody tr').first().within(() => {
        cy.get('.btn-outline-primary').click();
      });
  
      cy.get('#lib').clear().type('Updated Stock');
      cy.get('#qte').clear().type('150');
      cy.get('#qteM').clear().type('15');
      cy.get('.modal-footer .btn-success').click();
  
      // Add assertions to verify the stock has been updated
      cy.get('table tbody tr').first().within(() => {
        cy.contains('Updated Stock');
        cy.contains('150');
        cy.contains('15');
      });
    });
  
    it('deletes an existing stock', () => {
      // Assuming there's at least one stock in the list
      cy.get('table tbody tr').then(($rows) => {
        const initialRowCount = $rows.length;
        cy.get('table tbody tr').first().within(() => {
          cy.get('.btn-outline-danger').click();
        });
  
        // Wait for the deletion to be processed and then verify the row count has decreased
        cy.get('table tbody tr', { timeout: 10000 }).should('have.length.lessThan', initialRowCount);
      });
    });
  });
  