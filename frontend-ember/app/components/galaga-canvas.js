import Ember from 'ember';

export default Ember.Component.extend({
  didInsertElement() {
    this._super(...arguments);
    var canvas = document.getElementById('fight-to-the-death');
    var ctx = canvas.getContext('2d');


    // 2D-Sphere
    var ball = {
      x: canvas.width / 2,
      y: canvas.height - 30,
      dx: 2,
      dy: -2
    }

    var draw = function() {
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      drawBall(ball);
    };

    var drawBall = function(ball) {
      ctx.beginPath();
      ctx.arc(ball.x = ball.x + ball.dx, ball.y = ball.y + ball.dy, 10, 0, Math.PI * 2);
      ctx.fillStyle = '#0095DD';
      ctx.fill();
      ctx.closePath();
    };

    // the draw function will be executed every 10 seconds
    setInterval(draw, 10);
  },

});
