import {Cookies} from "react-cookie";

const cookies = new Cookies();

export const setCookie = (name, value, option) => {
    console.log("setCookie", name, value);
    return cookies.set(name, value, {...option});
}

export const getCookie = (name) => {
    console.log("getCookie", name);
    return cookies.get(name);
}