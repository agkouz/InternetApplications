<template>
<div id="container">
	<div id="selectionContainer">
		<ul class="w3-ul w3-card-4">
			<li class="w3-bar" v-for="path in paths" v-bind:key="path">{{path.name}}</li>
		</ul>
	</div>
	
		<l-map id="map" ref="myMap"> </l-map>
	
</div>
</template>

<script>
import L from 'leaflet';
import { LMap, LTileLayer, LMarker } from 'vue2-leaflet';

export default {
   components:{
        'l-map': LMap,
        'l-tile-layer': LTileLayer,
        'l-marker': LMarker
    },
  data () {
    return {
		paths: [],
		mymap: {}
    }
  },

  mounted(){
    // get all paths
      // CORS
        let headers = {
            'Access-Control-Allow-Origin': 'http://localhost:8081/'
        };
        
        // do a get request on our api --> will return all stops
        this.$http.get("http://localhost:8080/api/app2", {headers})
        .then((res) => {
			this.paths = res.body;
			  // create a map
            this.mymap = this.$refs.myMap.mapObject.setView([40.481426, 23.179408], 13);
            
            // tile for our map
            L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
                maxZoom: 18,
                attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
                    '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
                    'Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
                id: 'mapbox/streets-v11',
                tileSize: 512,
                zoomOffset: -1
            }).addTo(this.mymap);
        })
  }
}
</script>

<style scoped>
	@import url('https://www.w3schools.com/w3css/4/w3.css');


		#selectionContainer{
		
		text-align: left;
		padding-left: 0px;	
		width: 30%;
  	}


	li{
		margin-top:20px;
	}
</style>


