import React, { Component} from 'react';
import axios from "axios";

class Callback extends Component{

    render() {
        const code = new URL(window.location.href).searchParams.get("code");
        console.log('code : ', code);

        try {
            axios.post('/oauth2/token/kakao', {
                code
            },{
                baseURL: 'http://localhost:8080',
                withCredentials: true
            }).then((response) => {
                console.log('res data : ', response.data);
            });
        } catch (err) {
            alert(err);
            console.error(err);
        }

        return <div>Loading....</div>;
    }

}

export default Callback;