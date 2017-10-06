import { moduleForComponent, test } from 'ember-qunit';
import hbs from 'htmlbars-inline-precompile';

moduleForComponent('galaga-canvas', 'Integration | Component | galaga canvas', {
  integration: true
});

test('it renders', function(assert) {
  // Set any properties with this.set('myProperty', 'value');
  // Handle any actions with this.on('myAction', function(val) { ... });

  this.render(hbs`{{galaga-canvas}}`);

  assert.equal(this.$().text().trim(), '');

  // Template block usage:
  this.render(hbs`
    {{#galaga-canvas}}
      template block text
    {{/galaga-canvas}}
  `);

  assert.equal(this.$().text().trim(), 'template block text');
});
