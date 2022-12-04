import axios from "axios";

const BASE_URL = "http://137.184.37.205:8080";
const API_URL = "/api/v1/";
const EVENT_API = "event"

class EventService {

    getAllEvent() {
        return axios.get(BASE_URL + API_URL + EVENT_API +"/getAllEvent");
    }

    saveEvent(data) {
        return axios.post(BASE_URL + API_URL + EVENT_API + "/createEvent", data);
    }

}

export default new EventService();
