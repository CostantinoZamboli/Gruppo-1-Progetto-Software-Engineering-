class TimeSelection{

    _activity_id;
    _id_maintainer;
    _week;
    _day;
    _name;
    _time;
    _service_endpoint_activity_workspace;
    _service_enpoint_time;

    constructor(activity_id, week, id_maintainer, day, microservice_activity, microservice_time) {
        this._activity_id = activity_id;
        this._id_maintainer = id_maintainer;
        this._week = week;
        this._day = day;
        this._service_enpoint_time = microservice_time + "?id=" + this.activity_id + "&week=" + this.week + "&day=" + this.day + "&id_maintainer=" + this.id_maintainer;
        this._service_endpoint_activity_workspace = microservice_activity + "?id=" + this.activity_id;
    }

    get name() {
        return this._name;
    }

    set name(value) {
        this._name = value;
    }

    get time() {
        return this._time;
    }

    set time(value) {
        this._time = value;
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

    get service_enpoint_time() {
        return this._service_enpoint_time;
    }

    set service_enpoint_time(value) {
        this._service_enpoint_time = value;
    }

    get service_endpoint_activity_workspace() {
        return this._service_endpoint_activity_workspace;
    }

    set service_endpoint_activity_workspace(value) {
        this._service_endpoint_activity_workspace = value;
    }

    get activity_id() {
        return this._activity_id;
    }

    set activity_id(value) {
        this._activity_id = value;
    }


    get id_maintainer() {
        return this._id_maintainer;
    }

    set id_maintainer(value) {
        this._id_maintainer = value;
    }

    initBrowseView(){
        let controller = this;

        /* Call the microservice and evaluate data and result status */
        $.getJSON(this.service_endpoint_activity_workspace, function (data) {
            controller.renderGUI(data);
        }).done(function () {
            controller.showMessageStatus("green","All done");
        }).fail(function () {
            controller.showMessageStatus("red","Error while requesting service: " + controller.service_endpoint_activity_workspace);
        });

        this.showMessageStatus("black","Requesting data from service: " + this.service_endpoint_activity_workspace);

        /* Call the microservice and evaluate data and result status */
        $.getJSON(this.service_enpoint_time, function (data) {
            controller.renderTable(data);
        }).done(function () {
            controller.showMessageStatus("green","All done");
        }).fail(function () {
            controller.showMessageStatus("red","Error while requesting service: " + controller.service_enpoint_time);
        });

        this.showMessageStatus("black","Requesting data from service: " + this.service_enpoint_time);

    }

    renderGUI(data){

        $.each(data, function (index, obj){

            let id = obj.id;
            let site = obj.site;
            let area = obj.area;
            let typology = obj.typology;
            let time = obj.time;

            let activity = id + " - " + site + " - " + area + " - " + typology + " - " + time;
            $('#activity_info').val(activity);
            $('#workspace_notes').val(obj.workspace_notes);
        });

    }

    renderTable(data){
        let controller = this;
        let staticHtml = $("#availabilityTimeSlot-template").html();
        $.each(data, function (index, obj){
            let row = staticHtml;
            row = row.replace(/{Id_Maintainer}/ig, controller.id_maintainer);
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
                $("tfoot").html('<tr><th colspan="6">No records</th></tr>');
            }
        });

    }

    sendEmail(){

        $.post("http://192.168.1.77:8080/project/email.jsp",
            {
                id_maintainer: this.id_maintainer,
                activity_id: this.activity_id,
                week: this.week,
                day: this.day,
                time: this.time,
                name: this.name,
                date: new Date(),
            },
            function(data, status){
                alert("Data: " + data + "\nStatus: " + status);
            });
    }

    showMessageStatus (color,message) {
        $("#request-status").css("color",color);
        $("#request-status").html(message);
    }

}