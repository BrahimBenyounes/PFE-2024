describe('Product Management', () => {
    beforeEach(() => {
      // Replace with the URL of your application
      cy.visit('http://localhost:4200/product');
    });
  
    it('displays the list of products', () => {
      cy.get('h3').contains('List Product').should('be.visible');
      cy.get('table').should('be.visible');
      cy.get('thead').within(() => {
        cy.contains('Id').should('be.visible');
        cy.contains('Code').should('be.visible');
        cy.contains('Name').should('be.visible');
        cy.contains('Price').should('be.visible');
        cy.contains('Create Date').should('be.visible');
        cy.contains('Update Date').should('be.visible');
        cy.contains('Action').should('be.visible');
      });
    });
  
    it('adds a new product', () => {
      cy.get('.btn-success').contains('Add new Product').click();
      cy.get('#Product-code').type('P123');
      cy.get('#Product-name').type('New Product');
      cy.get('#Product-prix').type('100');
      cy.get('#Product-cdate').type('2023-05-27');
      cy.get('#Product-mdate').type('2023-05-27');
      cy.get('.modal-footer .btn-success').click();
      
      // Add assertions to verify the new product is in the list
      cy.get('table tbody tr').last().within(() => {
        cy.contains('P123');
        cy.contains('New Product');
        cy.contains('100');
      });
    });
  
    it('edits an existing product', () => {
      // Assuming there's at least one product in the list
      cy.get('table tbody tr').first().within(() => {
        cy.get('.btn-outline-primary').click();
      });
  
      cy.get('#Product-code').clear().type('P456');
      cy.get('#Product-name').clear().type('Updated Product');
      cy.get('#Product-prix').clear().type('150');
      cy.get('.modal-footer .btn-success').click();
  
      // Add assertions to verify the product has been updated
      cy.get('table tbody tr').first().within(() => {
        cy.contains('P456');
        cy.contains('Updated Product');
        cy.contains('150');
      });
    });
  
    it('deletes an existing product', () => {
      // Assuming there's at least one product in the list
      cy.get('table tbody tr').first().within(() => {
        cy.get('.btn-outline-danger').click();
      });
  
      // Add assertions to verify the product has been deleted
      cy.get('table tbody tr').should('have.length.lessThan', 1);
    });
  });
  