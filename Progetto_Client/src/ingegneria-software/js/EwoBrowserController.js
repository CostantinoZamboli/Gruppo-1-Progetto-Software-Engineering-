class EwoBrowserController{

    serviceEndPoint;

    constructor(microServiceEndPoint) {
        this.serviceEndPoint = microServiceEndPoint;
    }

    initBrowseView(){
        let controller = this;

        $.getJSON(this.serviceEndPoint, function (data) {
            controller.renderGUI(data);
        }).done(function () {
            controller.showMessageStatus("green","All done");
        }).fail(function () {
            controller.showMessageStatus("red","Error while requesting service: " + controller.serviceEndPoint);
        });

        this.showMessageStatus("black","Requesting data from service: " + this.serviceEndPoint);
    }

    renderGUI(data) {
        let staticHtml = $("#activity-template").html();

        let week;
        let day;
        $.each(data, function (index, obj) {
            if (index == 0) {
                week = obj.week;
                day = obj.day;
                $("#week_textarea").val(week);
                $("#day_textarea").val(day);
            } else {
                let row = staticHtml;
                row = row.replace(/{Ewo-id}/ig, obj.ewo_id);
                row = row.replace(/{Site}/ig, obj.site);
                row = row.replace(/{Area}/ig, obj.area);
                row = row.replace(/{Type}/ig, obj.typology);
                row = row.replace(/{Stato}/ig, obj.stato);
                row = row.replace(/{Select}/ig,
                    '<div id="select_div">' +
                    '<form method="get" action="EWO_compilation.html">' +
                    '<input type="hidden" name="id" value=' + obj.ewo_id + '>' +
                    '<input type="submit" class="btn btn-primary" value="Select">' +
                    '</form>' +
                    '</div>'
                );
                $('#activity-rows').append(row);
            }
        });

        if (data.length === 0) {
            $("tfoot :first-child").hide();
            $("tfoot").html('<tr><th colspan="6">No records</th></tr>');
        }


    }

    showMessageStatus (color,message) {
        $("#request-status").css("color",color);
        $("#request-status").html(message);
    }

}