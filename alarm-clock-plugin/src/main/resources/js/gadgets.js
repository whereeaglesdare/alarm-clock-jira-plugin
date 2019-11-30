/**
* Adding row to table
*/
function addRow(tbody, date, description, isAcknowledged, isAdministrative) {
    var row = tbody.insertRow(-1);
    row.insertCell(0).innerHTML = date;
    row.insertCell(1).innerHTML  = description;
    countdown( new Date(date).getTime(), row.insertCell(2));
}


function simpleTimer(countDownDate, cell) {

  // Get today's date and time
  var now = new Date().getTime();

  // Find the distance between now and the count down date
  var distance = countDownDate - now;

  // Time calculations for days, hours, minutes and seconds
  var days = Math.floor(distance / (1000 * 60 * 60 * 24));
  var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
  var seconds = Math.floor((distance % (1000 * 60)) / 1000);

  // Display the result in the element with id="demo"
  cell = days + "d " + hours + "h "  + minutes + "m " + seconds + "s ";

  // If the count down is finished, write some text
  if (distance < 0) {
    clearInterval();
    cell = "EXPIRED";
  }
}

function countdown(finish_date, cell) {

    var x = setInterval(function() {
        var now = new Date().getTime();

        var distance = finish_date - now;

        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        cell.innerHTML = days + "<span style='font-weight:normal'>d</span> " + hours + "h " + minutes + "m " + seconds + "s ";


        if (distance < 0) {
            clearInterval(x);
            cell.innerHTML = "ALARM EXPIRED";
        }
    }, 1000);
}
