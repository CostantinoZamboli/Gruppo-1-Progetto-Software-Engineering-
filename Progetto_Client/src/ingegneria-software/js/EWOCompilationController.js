class EWOCompilationController {

    serviceEndpointActivity;
    serviceEndpointAllSkill;
    id_activity;
    week;
    skill_number;
    day;
    id_site;
    id_typology;
    flag_info;
    flag_skill;


    constructor(serviceEndpointAllSkill, serviceEndpointActivity, id_activity) {
        this.id_activity = id_activity;
        this.serviceEndpointAllSkill = serviceEndpointAllSkill;
        this.serviceEndpointActivity = serviceEndpointActivity + "?id=" + this.id_activity;
        this.flag_info = false;
        this.flag_skill = false;
    }

    initBrowseView() {

        let controller = this;

        $.getJSON(this.serviceEndpointActivity, function (data) {
            controller.renderGUI(data);
        }).done(function () {
            console.log("All done");
        }).fail(function () {
            console.log("Error while requesting service: " + controller.serviceEndPoint);
        });

        $.getJSON(this.serviceEndpointAllSkill, function (data) {
            controller.renderSkill(data);
        }).done(function () {
            console.log("All done");
        }).fail(function () {
            console.log("Error while requesting service: " + controller.serviceEndpointAllSkill);
        });
    }

    renderGUI(data) {
        let controller = this;
        $.each(data, function (index, obj) {
            if (index == 0) {
                controller.week = obj.week;
                controller.day = obj.day;
                $("#week_textarea").val(controller.week);
                $("#day_textarea").val(controller.day);
            } else {
                controller.id_site = obj.id_site;
                controller.id_typology = obj.id_typology;
                let activity  = "EWO " + obj.ewo_id + " - " + obj.site + " - " + obj.area + " - " + obj.typology;
                $("#activity_info").val(activity);
                $("#workspace_notes").val(obj.workspace_notes);
            }
        });
    }

    renderSkill(data){

        let controller = this;
        let skill_count = 0;
        $.each(data, function (index, obj){
            skill_count = index;
            let id_skill = obj.id_skill;
            let competenza = obj.competenza;
            $("#skills_form").append('<input class="check_box_skill" type="checkbox" name="id_skill" value='+ id_skill +'>');
            $("#skills_form").append('<label for="id_skill">'+ competenza +'</label>' + '<br>');
        });
        this.skill_number = skill_count;
        $("#skills_form").append('<br>')
    }

    submitSkill(){

        let checked_skills = document.querySelectorAll('.check_box_skill:checked');

        if(!checked_skills.length){
            alert("Selezionare almeno una skill");
            this.flag_skill = false;
            return false;
        }

        this.flag_skill = true;
        $.post(SELECTED_MICRO_SERVICE_ENDPOINT_SELECTED_SKILL,
            {
                data: (JSON.stringify($("#skills_form").serializeArray())),
                id_activity: this.id_activity,
            }
            ).done(function(msg){
                alert(msg);
            })
            .fail(function(xhr, status, error){
                    console.log(status, error);
            });
    }

    submitInfo(){
        let intervention_description_object = $("#intervention_description")[0];
        let time_object = $("#time")[0];

        if(!time_object.checkValidity()){
            alert(time_object.validationMessage + ": Estimated Intervention Time ");
            this.flag_info = false;
            return false;
        }

        if(!intervention_description_object.checkValidity()){
            alert(intervention_description_object.validationMessage + ": Intervention Description");
            this.flag_info = false;
            return false;
        }

        this.flag_info = true;
        let intervention_description = $("#intervention_description").val();
        let time = $("#time").val();

        $.post(SELECTED_MICRO_SERVICE_ENDPOINT_EWO_INSERT,
            {
                id_activity : this.id_activity,
                intervention_description : intervention_description,
                time : time,
            },
            function(data, status){
                alert("Data: " + data + "\nStatus: " + status);
            });
    }

    checkFlag(){

        return !(!this.flag_info || !this.flag_skill);

    }
}