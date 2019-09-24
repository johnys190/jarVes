import { API_BASE_URL, POLL_LIST_SIZE, ACCESS_TOKEN } from '../constants';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    });
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
    .then(response => {
            if(!response.ok) {
                return Promise.reject(response);
            }
            if (response.status === 204) {
                return Promise.resolve(response);
            }
            return response.json();
        }
    );
};

const downloadFile = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    });

    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
        .then(response => {
            if(!response.ok) {
                return Promise.reject(response);
            }
            if (response.status === 204) {
                return Promise.resolve(response);
            }
            const filename =  options.name;
            response.blob().then(blob => {
                let url = window.URL.createObjectURL(blob);
                let a = document.createElement('a');
                a.href = url;
                a.download = filename;
                a.dispatchEvent(new MouseEvent('click'));
            });
            }
        );
};

export function login(loginRequest) {
    console.log(loginRequest);
    return request({
        url: API_BASE_URL + "/auth/signin",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    return request({
        url: API_BASE_URL + "/users",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function checkUsernameAvailability(username) {
    return request({
        url: API_BASE_URL + "/user/checkUsernameAvailability?username=" + username,
        method: 'GET'
    });
}

export function checkEmailAvailability(email) {
    return request({
        url: API_BASE_URL + "/user/checkEmailAvailability?email=" + email,
        method: 'GET'
    });
}

export function getCurrentUser() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/users/me",
        method: 'GET'
    });
}

export function getUserProfile(username) {
    return request({
        url: API_BASE_URL + "/users/" + username,
        method: 'GET'
    });
}

export function createVessel(body) {
    return request({
        url: API_BASE_URL + "/vessels",
        method: 'POST',
        body: JSON.stringify(body)
    });
}

export function deleteVesselById(id) {
    return request({
        url: API_BASE_URL + "/vessels/" + id,
        method: 'DELETE'
    });
}

export function updateVesselById(id, body) {
    return request({
        url: API_BASE_URL + "/vessels/" + id,
        method: 'PUT',
        body: JSON.stringify(body)
    });
}

export function getVesselById(id) {
    return request({
        url: API_BASE_URL + "/vessels/" + id,
        method: 'GET'
    });
}

export function getAllVessels() {
    return request({
        url: API_BASE_URL + "/vessels",
        method: 'GET'
    });
}

export function makeVesselImportant(id) {
    return request({
        url: API_BASE_URL + "/vessels/important/"+id,
        method: 'POST'
    });
}

export function makeVesselCommon(id) {
    return request({
        url: API_BASE_URL + "/vessels/important/"+id,
        method: 'DELETE'
    });
}

export function createTCEstimate(body) {
    return request({
        url: API_BASE_URL + "/tc-estimates",
        method: 'POST',
        body: JSON.stringify(body)
    });
}

export function deleteTCEstimateById(id) {
    return request({
        url: API_BASE_URL + "/tc-estimates/" + id,
        method: 'DELETE'
    });
}

export function updateTCEstimateById(id, body) {
    return request({
        url: API_BASE_URL + "/tc-estimates/" + id,
        method: 'PUT',
        body: JSON.stringify(body)
    });
}

export function getTCEstimateById(id) {
    return request({
        url: API_BASE_URL + "/tc-estimates/" + id,
        method: 'GET'
    });
}

export function getAllTCEstimates() {
    return request({
        url: API_BASE_URL + "/tc-estimates",
        method: 'GET'
    });
}

export function createVoyEstimate(body) {
    return request({
        url: API_BASE_URL + "/voy-estimates",
        method: 'POST',
        body: JSON.stringify(body)
    });
}

export function deleteVoyEstimateById(id) {
    return request({
        url: API_BASE_URL + "/voy-estimates/" + id,
        method: 'DELETE'
    });
}

export function updateVoyEstimateById(id, body) {
    return request({
        url: API_BASE_URL + "/voy-estimates/" + id,
        method: 'PUT',
        body: JSON.stringify(body)
    });
}

export function getVoyEstimateById(id) {
    return request({
        url: API_BASE_URL + "/voy-estimates/" + id,
        method: 'GET'
    });
}

export function getAllVoyEstimates() {
    return request({
        url: API_BASE_URL + "/voy-estimates",
        method: 'GET'
    });
}

export function getVoyEstimatePDF() {
    return request({
        url: API_BASE_URL + "/voy-estimates/voyestimate-pdf",
        method: 'GET'
    });
}

export function getAllUsers() {
    return request({
        url: API_BASE_URL + "/users",
        method: 'GET'
    });
}

export function deleteUserById(id) {
    return request({
        url: API_BASE_URL + "/users/" + id,
        method: 'DELETE'
    });
}

export function updateUserById(id, body) {
    return request({
        url: API_BASE_URL + "/users/" + id,
        method: 'PUT',
        body: JSON.stringify(body)
    });
}

export function getUserById(id) {
    return request({
        url: API_BASE_URL + "/users/" + id,
        method: 'GET'
    });
}

export function getTXT(id, name){
    return downloadFile({
        url: API_BASE_URL + '/voy-estimates/txt/' +id,
        method: 'GET',
        name: name
    });
}

export function getPDF(id, name){
    return downloadFile({
        url: API_BASE_URL + '/voy-estimates/pdf/' +id,
        method: 'GET',
        name: name
    });
}

