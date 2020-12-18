

class BrowseController {

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

        var week;
        $.each(data, function (index, obj) {
            if (index == 0) {
                week = obj.week;
                $("#week_textarea").val(week);
            } else {
                let row = staticHtml;
                let id_object = obj.id;
                row = row.replace(/{Id}/ig, obj.id);
                row = row.replace(/{Site}/ig, obj.site);
                row = row.replace(/{Area}/ig, obj.area);
                row = row.replace(/{Type}/ig, obj.typology);
                row = row.replace(/{Time}/ig, obj.time);
                row = row.replace(/{Select}/ig,
                    '<div id="select_div">' +
                    '<form method="get" action="selection_view.html">' +
                    '<input type="hidden" name="id" value=' + obj.id + '>' +
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