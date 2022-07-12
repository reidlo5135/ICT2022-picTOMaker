import axios from 'axios';
import React, { Component, useEffect, useState } from 'react';
import Slider from "react-slick";
import "../../css/community.css";
import "../../css/font.css"
import Top from "./Top";
import Footer from "./Footer";

export default function Community(){
    /* async function getData(){
        let rawResponse = await fetch('https://jsonplaceholder.typicode.com/posts');
        let jsonResponse = await rawResponse.json();
        console.log(jsonResponse);
    }

    getData(); */
        
    const [users, setUsers] = useState([]);

    useEffect(() => {
        axios.get('https://jsonplaceholder.typicode.com/users')
            .then(response => {
                setUsers(response.data);
            });
    }, []);
    console.log(users)
        
    return (
            <>
                <Top/>
                <div className='Community'>
                    
                    <h2 className="commu-title">커뮤니티</h2>
                    <div className ="row">
                        <table className="commu-table">
                            <thead>
                                <tr>
                                {users.map(user => {
                                        return (<th key={user.id}>
                                            {user.id}
                                        </th>)
                                    })}
{/*                                     <th>글 번호</th>
                                    <th>타이틀 </th>
                                    <th>작성자 </th>
                                    <th>작성일 </th> */}
                                </tr>
                            </thead>
                            <tbody>

                                {users.map(user => {
                                        return (<tr key={user.id}>
                                            <th>{user.name}</th>
                                            <th>{user.username}</th>
                                            <th>{user.email}</th>
                                            <th>{user.address.street}</th>
                                            <th>{user.company.name}</th>
                                            <th>{user.company.catchPhrase}</th>
                                        </tr>)
                                    })}
{/*                                     <td> 1 </td>
                                    <td> 2 </td>
                                    <td> 3</td>
                                    <td> 4 </td> */}     
                            </tbody>
                        </table>
                    </div>
            </div>
            <Footer/>
        </>
    );
}