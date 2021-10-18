<template id="car-detail">
    <div v-if="car" class="detail-car-container">
        {{console.log(this.car)}}
        <h1>{{car.name}}</h1>
        <img v-if="car.pictureUrl" class="cover-image" v-bind:src="car.pictureUrl">
        <img v-else class="cover-image" src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/d9/Icon-round-Question_mark.svg/480px-Icon-round-Question_mark.svg.png">

        <p>The mass of {{car.name}} is {{car.mass}} Rjup, it has a radius of {{car.radius}} Mjup,
            the eccentricity or the deviation of orbit is from a circularity is {{car.eccentricity}}.</p>
        <p>It spins around the star {{car.centralCelestialBody.name}} with an orbiting period of {{car.orbitalPeriod}} days.</p>
        <p>
          <a class="button" :href="`/api/car-system/${planetSystemName}/cars/${car.name}/delete`">Delete</a>
          <a class="button" :href="`/car-system/${planetSystemName}/cars/${car.name}/update`">Edit</a>
        </p>
        <br />
      <p>
        <a class="button" :href="`/car-system/${planetSystemName}/`">Back</a>
      </p>
    </div>
</template>
<script>
    Vue.component("car-detail", {
        template: "#car-detail",
        data: () => ({
            car: null,
            planetSystemName: "",
        }),
        created() {
            const planetSystemId = this.$javalin.pathParams["car-system-id"];
            this.planetSystemName = planetSystemId;
            console.log("Car system id: " + planetSystemId);
            const planetId = this.$javalin.pathParams["car-id"];
            fetch(`/api/car-system/${planetSystemId}/cars/${planetId}`)
                .then(res => res.json())
                .then(res => this.car = res)
                .catch(() => alert("Error while fetching car"));
        }
    });
</script>
<style>
    ul{
       color:white;
    }
    div.detail-car-container > p {
        max-width: 30em;
    }
    div.detail-car-container{
        padding-top: 10px;
        overflow: hidden;
        width: 500px;
        background-color: #000000;
        color: white;
        margin: 0 auto;
        opacity: 0.96;
        text-align: center;
        -webkit-box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.25);
        -moz-box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.25);
        box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.25);
    }

    img.car-cover-image {
        height: 320px;
        width: 320px;
        padding-bottom: 20px;
    }

    .button {
        padding: 10px;
        margin: 10px;
        border: 1px solid white;
        color: white;
        border-radius: 15px;
    }

    button a{
        color: white;
        text-decoration: none;
        font-weight: bold;
    }

    .button:hover{
        border: 2px solid white;
    }

</style>