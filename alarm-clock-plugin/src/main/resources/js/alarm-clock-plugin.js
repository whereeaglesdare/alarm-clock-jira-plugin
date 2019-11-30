AJS.$(document).ready(function() {
    //Datepicker initialization
    AJS.$('#alarm-date').datePicker({'overrideBrowserDefault': true});

    // Getting data from REST resource and displaying in user profile table
    function setTableValues(selector, integration, status, dateFrom, dateTo, action) {
        $.ajax({
            dataType: "json",
            url: AJS.contextPath() + "/rest/alarm/1.0/alarm",
        }).done(function(msg) {
             var tbody =  document.getElementById("alarm-table-body");
             for (i = 0; i < msg.length; i++) {
                addRow(tbody, msg[i].date, msg[i].description, msg[i].isAcknowledged, msg[i].isAdministrative);
             }
        });
    }
    // AJAX call for creating new alarm
    AJS.$("#alarm-create-button").on('click', function (e) {
        var formData = {
            "date": $("#alarm-date").val() + " " + $("#time-input").val(),
            "description": $("#description-input").val()
        }
        $.ajax({
            type: 'POST',
            data: JSON.stringify(formData),
            dataType: 'json',
            encode: true,
            contentType: 'application/json',
            url: AJS.contextPath() + "/rest/alarm/1.0/alarm"
        })
        .done(function(data) {
             var myFlag = AJS.flag({
                 type: 'success',
                 body: "Your alarm successfully created",
                 close: "auto"
             });

             $('#alarm-table-body').empty();
             setTableValues("alarms-table");
             $("#alarm-creation-form")[0].reset();
        })
        .fail(function(data) {
             if (data) {
                  AJS.flag({
                     type: 'error',
                     body: data.responseJSON.message,
                     close: "auto"
                 });
             }
        });
     });

    // Adding row to table
    function addRow(tbody, date, description, isAcknowledged, isAdministrative) {
        var row = tbody.insertRow(-1);
        var d = new Date();
        row.insertCell(0).innerHTML = date;
        row.insertCell(1).innerHTML  = description;
    }

    setTableValues("alarms-table");

});

