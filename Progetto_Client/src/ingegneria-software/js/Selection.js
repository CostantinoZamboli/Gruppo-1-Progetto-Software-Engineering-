
class Selection {

    _activity_id;
    _workspace_notes_id;
    _serviceEndPointActivity;
    _serviceEndPointSkill;
    constructor(microServiceEndPoint1, microServiceEndPoint2, activity) {
        this._activity_id = activity;
        this._serviceEndPointActivity = microServiceEndPoint1 + "?id=" + this._activity_id;
        console.log(this._serviceEndPointActivity)
        this._serviceEndPointSkill = microServiceEndPoint2 + "?id=" + this._activity_id;
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
    }


    renderGUI(data, flag) {

        let controller = this;
        if (flag) {
            $.each(data, function (index, obj) {
                controller._workspace_notes_id = obj.id_workspace_notes;

                $("#workspace_notes").val(obj.workspace_notes);
                $("#intervention_description").val(obj.intervention_description);
                $("#week_textarea").val(obj.week);

                $("#smp_link").attr("href", "http://192.168.1.77:8080/project/" + obj.smp);
                let activity = obj.id + "-" + obj.site + " " + obj.area + "-" + obj.typology + "-" + obj.time;
                $("#select_id_activity").val(obj.id);
                $("#week").val(obj.week);

                $("#activity_info").val(activity);

            });
        }
        else{
            $.each(data, function (index, obj) {
                console.log(obj.skills);
                var ul = $("#dynamic-list");
                var li = document.createElement("li");
                $(li).attr("id", obj.skills);
                $(li).append(document.createTextNode(obj.skills));
                $(ul).append(li);
            });
        }

        console.log(this._workspace_notes_id);
        /* When empty address-book */
        if (data.length === 0) {
            $("tfoot :first-child").hide();
            $("tfoot").html('<tr><th colspan="6">No records</th></tr>');
        }
    }

    editWorkspaceNotes(){
        $.post("http://192.168.1.77:8080/project/update_workspace_notes.jsp",
            {
                id: this._workspace_notes_id,
                description: $('#workspace_notes').val(),
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
