import Vue from 'vue';
import Router from 'vue-router';
import routes from './route.config.js';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes
});
