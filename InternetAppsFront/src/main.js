import Vue from 'vue'
import App from './App.vue'
import VueResource from 'vue-resource'

import 'leaflet/dist/leaflet.css';

// use the plugin for HTTP requests
Vue.use(VueResource);

new Vue({
  el: '#app',
  render: h => h(App)
})
