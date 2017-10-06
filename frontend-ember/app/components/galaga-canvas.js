import Ember from 'ember';

export default Ember.Component.extend({
  didInsertElement() {
    this._super(...arguments);
      // storing a reference to the canvas and creating a 2D rendering context

      var canvas =  document.getElementById("fight-to-the-death");
      var ctx = canvas.getContext("2d");

      // Red square
      ctx.beginPath();
      ctx.rect(20, 40, 50, 50);
      ctx.fillStyle = "#FF0000";
      ctx.fill();
      ctx.closePath();

      // Green circle
      ctx.beginPath();
      ctx.arc(240, 160, 20, 0, Math.PI * 2, false);
      ctx.fillStyle = "green";
      ctx.fill();
      ctx.closePath();

      // Blue outline
      ctx.beginPath();
      ctx.rect(160, 10, 100, 40);
      ctx.strokeStyle = "rgba(0, 0, 255, 0.5)";
      ctx.stroke();
      ctx.closePath();
  }

});


// TODO:Add UI menu:
/**
  * IM FORGETTING YOU, GUMWAA!😭👋
  * ██]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]] 10% complete.....
  * ████]]]]]]]]]]]]]]]]]]]]]]]]]]] 35% complete....
  * ███████]]]]]]]]]]]]]]]] 60% complete....
  * ███████████] 99% complete..... 🚫ERROR!🚫
  **/
// Font stuff for later
// ctx.font = '48px serif';
// ctx.fillText('😭', 50, 100);
