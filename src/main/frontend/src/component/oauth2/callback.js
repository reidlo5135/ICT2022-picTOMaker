import React, { Component} from 'react';
import {useHistory} from "react-router";
import axios from "axios";

class Callback extends Component{

    render() {
        const code = new URL(window.location.href).searchParams.get("code");
        console.log('code : ', code);

        if(code.toString() == null) {
            alert('Token is Null');
        }

        try {
            const resp = axios.post('/oauth2/token/kakao', {
                code
            },{
                baseURL: 'http://localhost:8080',
                withCredentials: true
            }).then((response) => {
                console.log('res data : ', response.data);
                console.log('res data.data : ', response.data.data);

                const access_token = response.data.data.access_token;
                const refresh_token = response.data.data.refresh_token;

                localStorage.setItem("access_token", access_token);
                localStorage.setItem("refresh_token", refresh_token);

                axios.post('/oauth2/profile/kakao', {
                    access_token
                },{
                    baseURL: 'http://localhost:8080',
                    withCredentials: true
                }).then((response) => {
                    console.log('profile res data : ', response.data);
                    console.log('profile res data.data : ', response.data.data);
                });
            });

            console.log('resp : ', {resp});
        } catch (err) {
            alert(err);
            console.error(err);
        }

        return <div>Loading....</div>;
    }

}

export default Callback;