import Ember from 'ember';

export default Ember.Component.extend({
  didInsertElement() {
    this._super(...arguments);
    var canvas = document.getElementById('fight-to-the-death');
    var ctx = canvas.getContext('2d');
    var ball = {
      x: canvas.width / 2,
      y: canvas.height - 30,
      dx: 2,
      dy: -2,
      render: function(context) {
        context.beginPath();
        context.arc(
          ball.x = ball.x + ball.dx,
          ball.y = ball.y + ball.dy,
          10, 0, Math.PI * 2
        );
        context.fillStyle = '#0095DD';
        context.fill();
        context.closePath();
      }
    }

    var draw = function() {
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      ball.render(ctx);
    };

    // the draw function will be executed every 10 seconds
    setInterval(draw, 10);
  },

});
