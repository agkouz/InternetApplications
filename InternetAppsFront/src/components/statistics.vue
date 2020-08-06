<template>
<div>
<table>
	<tr>
		<th class="selection" colspan="4">
			<label for="selection">Select group</label>
			<select id="selection">
				<option @click="selected=total">Total Transports per weather type</option>
				<option @click="selected=november">November - MAX transports per weather type</option>
				<option @click="selected=august">August - MAX transports per weather type</option>
			</select>
		</th>
	</tr>
	<tr>
		<th>Weather Class</th>
		<th>Event Date</th>
		<th>Day</th>
		<th>Total transportations</th>
	</tr>
	<tr v-for="dw in selected" v-bind:key="dw">
		<td>{{dw.weather_class}}</td>
		<td>{{dw.event_date}}</td>
		<td>{{days[dw.day-1]}}</td>
		<td>{{dw.total}}</td>
	</tr>
</table>
</div>
</template>

<script>

export default {

	data () {
		return {
			headers: {
				'Access-Control-Allow-Origin': 'http://localhost:8081/'
			},

			selected: [],
			total: [],
			august: [],
			november: [],

			days: [
				'Monday',
				'Tuesday',
				'Wednesday',
				'Thursday',
				'Friday',
				'Saturday',
				'Sunday'
			]
		}
	},
	methods:{},
	mounted(){
		let headers = this.headers;

		// initial call to get all and populate if needed. Calls for august and november after ready.
		this.$http.get("http://localhost:8080/api/app3/getData/", {headers})
			.then((res) => {
				this.total = res.body;
				this.total.sort((a, b) => a.day > b.day);
				this.selected = this.total;
				this.$http.get("http://localhost:8080/api/app3/getData/08", {headers}).then(res => this.august = res.body);
				this.$http.get("http://localhost:8080/api/app3/getData/11", {headers}).then(res => this.november = res.body);
			}
		);
	}
}
</script>
			
<style scoped>
div{
	  height: 85vh;
  overflow-y: auto;
}

table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  text-align: left;
  padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2}

th {
  background-color: #4CAF50;
  color: white;
}

.selection{
	text-align: center;
}
</style>


