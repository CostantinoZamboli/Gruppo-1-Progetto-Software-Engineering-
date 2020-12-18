class MaintainerSelection{

    _activity_id;
    _week_id;
    _serviceEndPointActivity;
    _serviceEndPointSkill;
    _serviceEndpointTable;
    constructor(microServiceEndPointActivity, microServiceEndPointSkill,microServiceEndPointTable, activity, week) {
        this._activity_id = activity;
        this._week_id = week;
        this._serviceEndpointTable = microServiceEndPointTable + "?id=" + this._activity_id + "&week=" + this._week_id;
        this._serviceEndPointActivity = microServiceEndPointActivity + "?id=" + this._activity_id;     // id necessario per acquisire le info delle skill relative ad un attività
        this._serviceEndPointSkill = microServiceEndPointSkill + "?id=" + this._activity_id;        // id necessario per effettuare il match tra le skill del maintainer e quelle dell'attività
    }

    get activity_id() {
        return this._activity_id;
    }

    set activity_id(value) {
        this._activity_id = value;
    }

    get serviceEndPointSkill() {
        return this._serviceEndPointSkill;
    }

    set serviceEndPointSkill(value) {
        this._serviceEndPointSkill = value;
    }

    get serviceEndpointTable() {
        return this._serviceEndpointTable;
    }

    set serviceEndpointTable(value) {
        this._serviceEndpointTable = value;
    }

    get serviceEndPointActivity() {
        return this._serviceEndPointActivity;
    }

    set serviceEndPointActivity(value) {
        this._serviceEndPointActivity = value;
    }

    initBrowseView(){
        let controller = this;

        $.getJSON(this.serviceEndPointActivity, function (data) {
            controller.renderGUI(data, true);
        }).done(function () {
            controller.showMessageStatus("green","All done");
        }).fail(function () {
            controller.showMessageStatus("red","Error while requesting service: " + controller.serviceEndPointActivity);
        });

        this.showMessageStatus("black","Requesting data from service: " + this.serviceEndPointActivity);

        $.getJSON(this.serviceEndPointSkill, function (data) {
            controller.renderGUI(data, false);
        }).done(function () {
            controller.showMessageStatus("green","All done");
        }).fail(function () {
            controller.showMessageStatus("red","Error while requesting service: " + controller.serviceEndPointSkill);
        });

        this.showMessageStatus("black","Requesting data from service: " + this.serviceEndPointSkill);

        $.getJSON(this.serviceEndpointTable,function (data) {
            controller.renderTable(data);
        }).done(function () {
            controller.showMessageStatus("green","All done");
        }).fail( function(d, textStatus, error) {
                console.error("getJSON failed, status: " + textStatus + ", error: "+error)
        });

        this.showMessageStatus("black","Requesting data from service: " + this.serviceEndpointTable);
    }



    renderGUI(data, flag) {
        let staticHtmlActivityInfo = document.getElementById("activity_info");
        let staticHtmlWeek = document.getElementById("week_textarea");

        if (flag) {
            $.each(data, function (index, obj) {
                staticHtmlWeek.value = obj.week;

                let activity = obj.id + "-" + obj.site + " " + obj.area + "-" + obj.typology + "-" + obj.time;
                staticHtmlActivityInfo.value = activity;
            });
        } else {
            $.each(data, function (index, obj) {
                console.log(obj.skills);
                var ul = $("#dynamic-list");
                var li = document.createElement("li");
                $(li).attr("id", obj.skills);
                $(li).append(document.createTextNode(obj.skills));
                $(ul).append(li);
            });
        }
    }

    renderTable(data){
        let staticHtml = $("#availability-template").html();
        $.each(data, function (index, obj){
            let row = staticHtml;
            row = row.replace(/{Id}/ig, obj.id_maintainer);
            row = row.replace(/{Nome}/ig, obj.nome);
            row = row.replace(/{Skills}/ig, obj.skill_match);
            row = row.replace(/{AvailLun}/ig, obj.avail_lun + "%");
            row = row.replace(/{AvailMar}/ig, obj.avail_mar + "%");
            row = row.replace(/{AvailMerc}/ig, obj.avail_mer + "%");
            row = row.replace(/{AvailGiov}/ig, obj.avail_gio + "%");
            row = row.replace(/{AvailVen}/ig, obj.avail_ven + "%");
            row = row.replace(/{AvailSab}/ig, obj.avail_sab + "%");
            row = row.replace(/{AvailDom}/ig, obj.avail_dom + "%");

            $('#availability-rows').append(row);
            if (data.length === 0) {
                $("tfoot :first-child").hide();
                $("tfoot").html('<tr><th colspan="6">No records</th></tr>');
            }
        });

    }

    showMessageStatus (color,message) {
        $("#request-status").css("color",color);
        $("#request-status").html(message);
    }


}
