class MaintainerSelectionEWO{

    _id_activity;
    _serviceEndpointSelectedEWOActivity;
    _serviceEndpointSkill;
    _serviceEndpointTimeAvailabilityEWO;
    _week;
    _day;
    _name;
    _time_slot1;
    _time_slot2;
    _id_maintainer;


    constructor(id_activity, serviceEndpointSelectedEWOActivity, serviceEndpointSkill, serviceEndpointTimeAvailabilityEWO, day, week) {
        this._serviceEndpointSkill = serviceEndpointSkill + "?id=" + id_activity;
        this._week = week;
        this._day = day;
        this._serviceEndpointSelectedEWOActivity = serviceEndpointSelectedEWOActivity + "?id=" + id_activity;
        this._id_activity = id_activity;
        this._serviceEndpointTimeAvailabilityEWO = serviceEndpointTimeAvailabilityEWO + "?id=" + id_activity + "&day=" + this._day + "&week=" + this._week;
    }


    get name() {
        return this._name;
    }

    set name(value) {
        this._name = value;
    }

    get time_slot1() {
        return this._time_slot1;
    }

    set time_slot1(value) {
        this._time_slot1 = value;
    }

    get time_slot2() {
        return this._time_slot2;
    }

    set time_slot2(value) {
        this._time_slot2 = value;
    }

    get id_maintainer() {
        return this._id_maintainer;
    }

    set id_maintainer(value) {
        this._id_maintainer = value;
    }

    get id_activity() {
        return this._id_activity;
    }

    set id_activity(value) {
        this._id_activity = value;
    }

    get serviceEndpointSelectedEWOActivity() {
        return this._serviceEndpointSelectedEWOActivity;
    }

    set serviceEndpointSelectedEWOActivity(value) {
        this._serviceEndpointSelectedEWOActivity = value;
    }

    get serviceEndpointSkill() {
        return this._serviceEndpointSkill;
    }

    set serviceEndpointSkill(value) {
        this._serviceEndpointSkill = value;
    }

    get serviceEndpointTimeAvailabilityEWO() {
        return this._serviceEndpointTimeAvailabilityEWO;
    }

    set serviceEndpointTimeAvailabilityEWO(value) {
        this._serviceEndpointTimeAvailabilityEWO = value;
    }

    get week() {
        return this._week;
    }

    set week(value) {
        this._week = value;
    }

    get day() {
        return this._day;
    }

    set day(value) {
        this._day = value;
    }

    initBrowseView(){

        let controller = this;

        $.getJSON(this.serviceEndpointSelectedEWOActivity, function (data) {
            controller.renderGUI(data);
        }).done(function () {
            console.log("All done");
        }).fail(function () {
            console.log("Error while requesting service: " + controller.serviceEndpointSelectedEWOActivity);
        });



        $.getJSON(this.serviceEndpointSkill, function (data) {
            controller.renderSkill(data);
        }).done(function () {
            console.log("All done");
        }).fail(function () {
            console.log("Error while requesting service: " + controller.serviceEndpointSkill);
        });

        $.getJSON(controller.serviceEndpointTimeAvailabilityEWO, function (data) {
            controller.renderTable(data);
        }).done(function () {
            console.log("All done");
        }).fail(function (d, textStatus, error) {
            console.error("getJSON failed, status: " + textStatus + ", error: "+error)
        });

    }


    renderGUI(data){

        $("#week_textarea").val(this.week);
        $("#day").val(this.day);

        let controller = this;
        $.each(data, function (index, obj){
            let activity =  "EWO " + obj.id + " - " + obj.site + " - " + obj.area + " - " + obj.typology;
            $("#activity_info").val(activity);
            $("#workspace_notes").val(obj.workspace_notes);
            $("#time_required").val(obj.time);

        });

    }

    renderSkill(data){


        $.each(data, function (index, obj){
            var ul = $("#skill_list");
            var li = document.createElement("li");
            $(li).attr("id", obj.skills);
            $(li).append(document.createTextNode(obj.skills));
            $(ul).append(li);
        });
    }

    renderTable(data){
    let controller = this;
    let staticHtml = $("#availabilityTimeSlot-template").html();
    $.each(data, function (index, obj){
        let row = staticHtml;
        row = row.replace(/{Id_Maintainer}/ig, obj.id_maintainer);
        row = row.replace(/{Nome}/ig, obj.nome);
        row = row.replace(/{Skills}/ig, obj.skill_match);
        row = row.replace(/{Avail8_9}/ig, obj.avail_8_9);
        row = row.replace(/{Avail9_10}/ig, obj.avail_9_10);
        row = row.replace(/{Avail10_11}/ig, obj.avail_10_11);
        row = row.replace(/{Avail11_12}/ig, obj.avail_11_12);
        row = row.replace(/{Avail12_13}/ig, obj.avail_12_13);
        row = row.replace(/{Avail13_14}/ig, obj.avail_13_14);
        row = row.replace(/{Avail14_15}/ig, obj.avail_14_15);

        $('#availabilityTimeSlot-rows').append(row);
        if (data.length === 0) {
            $("tfoot :first-child").hide();
            $("tfoot").html('<tr><th colspan="12">No records</th></tr>');
        }
    });

}

    sendEmail(){

        let info = {
            id_maintainer: this.id_maintainer,
            week: this.week,
            day: this.day,
            time1: this.time_slot1,
            time2: this.time_slot2,
            name: this.name,
            timestamp: new Date(),
        }

        console.log(JSON.stringify(info));
        $.post("http://192.168.1.77:8080/project/email_EWO.jsp",
            {

                data: "[" + JSON.stringify(info) + "]",
                activity_id: this.id_activity,

            },function(data, status){
                console.log(data);
                alert("Data: " + data + "\nStatus: " + status);
            });
    }

}