import React from 'react'

function test() {
  if (navigator.geolocation) {
  navigator.geolocation.getCurrentPosition(showPosition);
} else {
  document.getElementById("demo").innerHTML =
  "Geolocation is not supported by this browser.";
}

function showPosition(position) {
  document.getElementById("demo").innerHTML =
  "Latitude: " + position.coords.latitude +
  " Longitude: " + position.coords.longitude;
}
  return (
    <div id="demo">
      Hey there
    </div>
  )
}

export default test
