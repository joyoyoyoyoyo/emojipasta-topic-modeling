import Ember from 'ember';

export default Ember.Component.extend({
  actions: {
    draw(some) {
      alert(some)
      // storing a reference to the canvas and creating a 2D rendering context
      var canvas = document.getElementById("fight-to-the-death");
      var ctx = canvas.getContext("2d");

      ctx.beginPath();
      ctx.rect(20, 40, 50, 50);
      ctx.fillStyle = "#FF000";
      ctx.closePath();
    }
  }
});
