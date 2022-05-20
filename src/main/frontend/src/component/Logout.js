import React from "react";

const Logout = async () => {

    try {
        localStorage.clear();
    } catch (err) {
        console.error(err);
    }
}

export default Logout;