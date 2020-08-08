<template>
<div id="container">
 
    <!-- <div id="mapid" style="width: 600px; height: 400px;"></div> -->

    <l-map id="map" ref="myMap"> </l-map>
    
     
    <div class="container" id="resultsContainer">
        <form>
            <div class="row">
                <div class="col-50">
                    <label for="fname">Your coordinates</label>
                </div>
                <div class="col-25">
                    <input type="text" placeholder="Lat" readonly v-bind:value="src_lat">
                </div>
                <div class="col-25">
                    <input type="text" placeholder="Lon" readonly v-bind:value="src_lon">
                </div>
            </div>

            <div class="row">
                <div class="col-50">
                    <label for="fname">Destination coordinates</label>
                </div>
                <div class="col-25">
                    <input type="text" placeholder="Lat" readonly v-bind:value="tar_lat">
                </div>
                <div class="col-25">
                    <input type="text" placeholder="Lon" readonly v-bind:value="tar_lon">
                </div>
            </div>

            <div class="row">
                <div class="col-50">
                    <label for="fname">Distance in meters</label>
                </div>
                <div class="col-50">
                    <input type="text" placeholder="Distance" readonly v-bind:value="distance">
                </div>
            </div>
            
            <div class="row">
                <div class="col-50">
                    <label for="fname">Next arrival at</label>
                </div>
                <div class="col-50">
                    <input type="text" placeholder="Arrival" readonly v-bind:value="next_arrival">
                </div>
            </div>

            

        </form>
        
    
        
    </div>
</div>
</template>

<script>

// leaflet
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
            tar_id: 0,
            tar_lat: 0,
            tar_lon: 0,
            tar_sel: {},

            src_lat: 0,
            src_lon: 0,
            src_circle: {},

            next_arrival: 0,
            distance:0,

            selectedTar: false,
            selectedSrc: false,
            
            pathline:{},

            defaultIcon: L.icon({
                iconUrl: require('../assets/default.png'),
                iconSize: [58, 85],
                iconAnchor: [22, 94],
                popupAnchor: [-3, -76],
            }),
            selectedIcon: L.icon({
                iconUrl: require('../assets/marker.png'),
                iconSize: [58, 85],
                iconAnchor: [22, 94],
                popupAnchor: [-3, -76]
            }),
            
            skip: false,

            mymap: {}
        }
    },
    methods:{
        // on marker click --> will store the destination lat and lon
        markerClick(information_object){
         
        
            // if already selected restore previous marker
            if(this.selectedTar === true){
                this.mymap.removeLayer(this.tar_sel);
                
                L.marker([this.tar_lat, this.tar_lon], {title: this.tar_id})
                .setIcon(this.defaultIcon)
                .addTo(this.mymap)
                .on('click', this.markerClick);
            }

            // if same then unselect and return (toggle)
            if(this.tar_sel === information_object.target){
                this.tar_sel = {};
                this.selectedTar = false;
                this.tar_lat = 0;
                this.tar_lon = 0;
                this.distance = 0;
                this.next_arrival = 0;
                return;
            }
            
            // target lat, lon
            this.tar_lat = information_object.latlng.lat;
            this.tar_lon = information_object.latlng.lng;
            this.tar_id = information_object.target.options.title;

            // selected icon
            information_object.target.setIcon(this.selectedIcon);
            

            
            // calculate timestamp
            // CORS
            let headers = {
                'Access-Control-Allow-Origin': 'http://localhost:8081/'
            };
        
            // do a get request on our api --> will return current stop
            this.$http.get("http://localhost:8080/api/app1/getStops/"+this.tar_id, {headers})
            .then((res) =>{ 
                console.log(res);
                this.next_arrival = new Date(res.body.next_arrival);
            });


            // for view and next
            this.selectedTar = true;
            this.tar_sel = information_object.target;
        
        },

        // on map click --> will store your lat and lon
        mapClick(information_object){
           
            if(this.pathline !== null) this.mymap.removeLayer(this.pathline);

            if(this.skip) this.skip = !this.skip;
            else{
                // remove prev circle
                if(this.src_circle !== null) this.mymap.removeLayer(this.src_circle);

                // get src and dst lat, lon
                this.src_lat = information_object.latlng.lat;
                this.src_lon = information_object.latlng.lng;
                
                // set selected and add circle
                this.selectedSrc = true;
                this.src_circle = L.circle([this.src_lat, this.src_lon], 100, {
                    color: 'red',
                    fillColor: '#f03',
                    fillOpacity: 0.5
                });

                // clicl listener for source circe --> removes circle and skips next mapClick
                this.src_circle.addTo(this.mymap).on('click', (e) => {
                    this.skip = true;
                    this.mymap.removeLayer(this.src_circle);
                    this.selectedSrc = false;
                    this.src_lon = this.src_lat = 0;
                    this.distance = 0;
                    
                });
            }

        },

    },
    mounted(){
        // CORS
        let headers = {
            'Access-Control-Allow-Origin': 'http://localhost:8081/'
        };
        
        // do a get request on our api --> will return all stops
        this.$http.get("http://localhost:8080/api/app1/getStops/", {headers})
        .then((data) => {
            let stops = data.body;
            var i;    

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



    
            // populate map
            for(i=0; i<stops.length; i++) {
                L.marker([stops[i].lat, stops[i].lon],{title:stops[i].id})
                .setIcon(this.defaultIcon)
                .addTo(this.mymap)
                .on('click', this.markerClick);
            }

            this.mymap.on('click', this.mapClick);


        }).catch((err) => {
            console.log(err);
        });

    },
    updated(){
            if(!this.selectedSrc || !this.selectedTar) return;
         // draw a line from source to dest
            if(this.pathline !== null) this.mymap.removeLayer(this.pathline);
            var coords = [
                [this.src_lat, this.src_lon],
                [this.tar_lat, this.tar_lon]
            ];
            this.pathline = L.polyline(coords).addTo(this.mymap)


            // calculate distance
            const R = 6371e3; // metres
            const φ1 = this.src_lat * Math.PI/180; // φ, λ in radians
            const φ2 = this.tar_lat * Math.PI/180;
            const Δφ = (this.tar_lat-this.src_lat) * Math.PI/180;
            const Δλ = (this.tar_lon-this.src_lon) * Math.PI/180;

            const a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                    Math.cos(φ1) * Math.cos(φ2) *
                    Math.sin(Δλ/2) * Math.sin(Δλ/2);
            const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

            const d = R * c; // in metres
            this.distance = d;
    }
}




</script>

<style scoped>


    #map{
        height: 600px;
    }
    span{
        display: block;
    }

    * {
    box-sizing: border-box;
    }

    input[type=text], select, textarea {
    width: 100%;
    padding: 12px;
    border: 1px solid #ccc;
    border-radius: 4px;
    resize: vertical;
    }

    label {
    padding: 12px 12px 12px 0;
    display: inline-block;
    }

    input[type=submit] {
    background-color: #4CAF50;
    color: white;
    padding: 12px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    float: right;
    }

    input[type=submit]:hover {
    background-color: #45a049;
    }

    .container {
    border-radius: 5px;
    background-color: #f2f2f2;
    padding: 20px;
    }
    
    .col-25 {
    float: left;
    width: 25%;
    margin-top: 6px;
    }

    .col-50{
        float:left;
        width:50%;
        margin-top: 6px;
    }
    .col-75 {
    float: left;
    width: 75%;
    margin-top: 6px;
    }

    /* Clear floats after the columns */
    .row:after {
    content: "";
    display: table;
    clear: both;
    }

    /* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
    @media screen and (max-width: 600px) {
    .col-25, .col-75, input[type=submit] {
        width: 100%;
        margin-top: 0;
    }
    }
</style>


