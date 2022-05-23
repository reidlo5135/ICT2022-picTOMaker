import React from "react";

export default function Logout() {
    try {
        localStorage.clear();
    } catch (err) {
        console.error(err);
    }
}