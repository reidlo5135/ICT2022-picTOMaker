import axios from "axios";

export const get = async (url, config) => {
    try {
        return await axios.get(url, config);
    } catch (e) {
        console.error(e);
        return e;
    }
}

export const post = async (url, data, config) => {
    try {
        return await axios.post(url, data, config);
    } catch (e) {
        console.error(e);
        return e;
    }
}

export const put = async (url, data, config) => {
    try {
        return await axios.put(url, data, config);
    } catch (e) {
        console.error(e);
        return e;
    }
}

export const del = async (url, config) => {
    try {
        return await axios.delete(url, config);
    } catch (e) {
        console.error(e);
        return e;
    }
}