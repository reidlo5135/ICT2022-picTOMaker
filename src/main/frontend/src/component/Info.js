import React, { useState, useEffect, Component } from 'react';
import axios from "axios";
import Logo from "../image/Logo.png";
import "../css/SignUp.css"
import "../css/font.css"
import log from "tailwindcss/lib/util/log";

function Info() {
    const [users, setUsers] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchUsers = async () => {
        try {
            setError(null);
            setUsers(null);
            setLoading(true);
            const response = await axios.get('oauth2/redirect/kakao');
            setUsers(response.data);
        } catch (e) {
            setError(e);
        }
        setLoading(false);
    };

    useEffect(() => {
        fetchUsers().then(r => console.log(r));
    }, []);

    if(loading) return <div>Loading...</div>;
    if(error) return <div>Error Occurred...</div>;
    if(!users) return null;

    return (
        <>
            <ul>
                {users.map(user => (
                    <li key={user.id}>
                        {user.username} ({user.name})
                    </li>
                ))}
            </ul>
            <button onClick={fetchUsers}>RELOAD</button>
        </>
    );
}

export default Info;