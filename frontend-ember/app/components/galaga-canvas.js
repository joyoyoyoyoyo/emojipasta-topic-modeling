import Ember from 'ember';

export default Ember.Component.extend({
  didInsertElement() {
    this._super(...arguments);
    var canvas = document.getElementById('fight-to-the-death')
    var ctx = canvas.getContext('2d')

    // 2D-Sphere
    var x = canvas.width / 2;
    var y = canvas.height - 30;
    var dx = 2;
    var dy = -2;

    var draw = function() {
      ctx.beginPath();
      ctx.arc(x = x + dx, y = y + dy, 10, 0, Math.PI * 2);
      ctx.fillStyle = '#0095DD';
      ctx.fill();
      ctx.closePath();
    }
    // the draw function will be executed every 10 seconds
    setInterval(draw, 10);
  },

});
