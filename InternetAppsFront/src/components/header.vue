<template>
<div>
    <ul id="saturday">
        <li><a href="#" @click.prevent="changeComp('app-map')" v-bind:class="current['app-map']"><span>Map</span></a></li>
        <li><a href="#" @click.prevent="changeComp('app-clear')" v-bind:class="current['app-clear']"><span>Clear Weather</span></a></li>
        <li><a href="#" @click.prevent="changeComp('app-statistics')" v-bind:class="current['app-statistics']"><span>Statistics</span></a></li>
    </ul>
</div>
</template>

<script>

export default {
    data () {
        return {
            map: 'current',
            clear: '',
            stat: '',
            current: {
                'app-map':'current',
            }
        }
    },
    methods:{
        
        // change active tab and emmit changeComp to change component
        changeComp(comp){
            this.current = {};
            this.current[comp] = 'active'
            this.$emit('changeComp', comp);
        }
    }
}
</script>

<style scoped>
    @import '../assets/styles/header.css';
</style>

