import axios from "axios";

const BASE_APP_URL = "http://localhost:8080/api";

class AppService{
    static getAllNearMechanics(currentLocation){
        return axios.get(BASE_APP_URL+`/mechanics?location=${currentLocation}`,{})
    }

    static async authenticate({ email, password, isMechanic }) {
        try {
            const endpoint =isMechanic ? 'mechanic/auth/authenticate' : 'motorist/auth/authenticate';
            const response = await axios.post(`${BASE_APP_URL}/${endpoint}`, { email, password });
            const decodedToken = parseJwt(response.data.token);
            sessionStorage.setItem("token", response.data.token);
            localStorage.setItem("token", response.data.token)
            sessionStorage.setItem("authorities", decodedToken.role);
            sessionStorage.setItem("id", decodedToken.id);
            sessionStorage.setItem("userName", decodedToken.name);
            const expiration = decodedToken.exp * 1000;
            const logoutTimeout = expiration - Date.now();
            console.log("logout timeout", logoutTimeout);
            setTimeout(logout, logoutTimeout);
            return decodedToken.sub;
        } catch (error) {
            throw error;
        }
    }
    static async register(client, isMechanic) {
        try {
            let response = null;
            if (isMechanic){
                response = await axios.post(`${BASE_APP_URL}/mechanic/auth/register`, client);
            }else {
                response = await axios.post(BASE_APP_URL+`/motorist/auth/register`,client)
            }

            return response.data;
        } catch (error) {
            throw error;
        }
    }

    static async getMechanicById(id) {
        return axios.get(BASE_APP_URL+`/mechanic?id=${id}`,{ })
    }

    static async getAllMechanics() {
        return axios.get(BASE_APP_URL+`/main/mechanics/all`,{});
    }
}
export default AppService;
export const logout = async () => {
    try {
        await axios.post(`${BASE_APP_URL}/logout`, {});
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("authorities");
        sessionStorage.removeItem("id");
        sessionStorage.removeItem("userName");
        localStorage.removeItem("token");
    } catch (error) {
        console.error('Error logging out', error);
        throw error;
    }
};

// Helper function to decode JWT token
function parseJwt(token) {
    try {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)).join(''));
        return JSON.parse(jsonPayload);
    } catch (error) {
        console.error("Error decoding JWT token", error);
        throw error;
    }
}