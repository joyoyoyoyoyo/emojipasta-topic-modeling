import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('emojichefs');
  this.route('emojipasta');
  this.route('galaga');
});

export default Router;
