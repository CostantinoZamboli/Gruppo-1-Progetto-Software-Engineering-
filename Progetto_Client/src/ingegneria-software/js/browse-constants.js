const JAVA_TOMCAT_HOST = "192.168.1.77:8080";


const MICRO_SERVICES_ENDPOINTS =[

    "http://"+ JAVA_TOMCAT_HOST +"/project/prova.jsp",

    "http://"+ JAVA_TOMCAT_HOST +"/project/activity_selection.jsp",

    "http://"+ JAVA_TOMCAT_HOST +"/project/skill_selection.jsp",

    "http://"+ JAVA_TOMCAT_HOST +"/project/maintainer_availability.jsp",

    "http://"+ JAVA_TOMCAT_HOST +"/project/time_availability.jsp",

];

/**
 * SELECTED_MICRO_SERVICE_ENDPOINT
 * @type string
 *
 * Selected service endpoint
 */
const SELECTED_MICRO_SERVICE_ENDPOINT = MICRO_SERVICES_ENDPOINTS[0];
const SELECTED_MICRO_SERVICE_ENDPOINT_ACTIVITY = MICRO_SERVICES_ENDPOINTS[1];
const SELECTED_MICRO_SERVICE_ENDPOINT_SKILL = MICRO_SERVICES_ENDPOINTS[2];
const SELECTED_MICRO_SERVICE_ENDPOINT_AVAILABILITY = MICRO_SERVICES_ENDPOINTS[3];
const SELECTED_MICRO_SERVICE_ENDPOINT_TIME_AVAILABILITY = MICRO_SERVICES_ENDPOINTS[4];