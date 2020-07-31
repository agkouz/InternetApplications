<template>
<div>
  <app-header v-on:changeComp="appSwap($event)"></app-header> <!-- header listens for changeComp event -->
  <component v-bind:is="component"></component>               <!-- component selected is inserted here -->
  <app-footer></app-footer>                                   <!-- just the footer -->
</div>
</template>

<script>

import Header from './components/header.vue'
import Footer from './components/footer.vue'

import MapApp from './components/map.vue'
import Clear from './components/clear.vue'
import Statistics from './components/statistics.vue'

export default {
  components:{
    'app-header': Header,
    'app-footer': Footer,

    'app-map': MapApp,
    'app-clear': Clear,
    'app-statistics': Statistics
  },
  data () {
    return {
      component: ''
    }
  },
  methods:{
    // swap component
    appSwap(comp){
      this.component = comp;
    }
  }
}
</script>

<style>
  @import './assets/styles/layout.css';
</style>
