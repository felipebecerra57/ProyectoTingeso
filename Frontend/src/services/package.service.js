import httpClient from "../http-common";
import {data} from "react-router-dom";

const getAll = () => {
    return httpClient.get('/api/turisticPackages/all');
}
const create = data => {
    return httpClient.post("/api/turisticPackages/saveNew", data);
}


export default { getAll , create };