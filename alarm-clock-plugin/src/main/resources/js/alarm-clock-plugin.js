AJS.$(document).ready(function() {
    AJS.$('#alarm-date').datePicker({'overrideBrowserDefault': true});

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

    function addRow(tbody, date, description, isAcknowledged, isAdministrative) {
        var row = tbody.insertRow(-1);
        var d = new Date();
        row.insertCell(0).innerHTML = getFormattedDate(new Date(date));
        row.insertCell(1).innerHTML  = description;
    }

    function getFormattedDate(m) {
        return m.getUTCFullYear() +"/"+ (m.getUTCMonth()+1) +"/"+ m.getUTCDate() + " " + m.getUTCHours() + ":" + m.getUTCMinutes();
    }

    AJS.$("#alarm-create-button").on('click', function (e) {
        var formData = {
            "date": new Date($("#alarm-date").val() + " " + $("#time-input").val()).getTime(),
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
        })
        .fail(function(data) {
             if (data.message) {
                  AJS.flag({
                     type: 'error',
                     body: data.message,
                     close: "auto"
                 });
             }
        });
     });

    setTableValues("alarms-table");
});

