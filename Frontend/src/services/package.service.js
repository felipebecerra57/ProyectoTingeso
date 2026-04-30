import httpClient from "../http-common";

const getAll = () => {
    return httpClient.get('/api/turisticPackages/all');
}
const create = (data, token) => {
    return httpClient.post('/api/turisticPackages/create', data, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};
const remove = (id, token) => {
    return httpClient.delete(`/api/turisticPackages/delete${id}`, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });
};
const update = (id, data, token) => {
    return httpClient.put(`/api/turisticPackages/update${id}`, data, {
        headers: { Authorization: `Bearer ${token}` }
    });
};

export default { getAll , create, remove, update };