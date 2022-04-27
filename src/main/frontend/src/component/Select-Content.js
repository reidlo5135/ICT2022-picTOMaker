import React, { Component } from 'react';
import Human from "../image/Human.png"
import Things from "../image/Things.jpg"
import Edit from "../image/Edit.png"
import { Link } from "react-router-dom";
import "../css/Select-Content.css"

class SelectCotent extends Component{
  
    render(){
      return(
        <div className='Select-Content'>

          <div className='HT-Content'>
            <div className='Human-content'>
                <div className='Human-Image'>
                    <img src={Human} alt="Human-Img" style={{width:"10%",height:"10%"}}/>
                </div>
                <div className='Human-des'>
                      인체를 인식하여 <br />사람 형태의 그래픽을 생성해줍니다.<br />
                      카메라에 신체를 맞춰 포즈를 취해보세요!
                </div>
                <Link to="/Human">
                    <button className='Human-Button'>
                    인체인식
                    </button>  
                </Link>
            </div> 

            <div className='Edit-Content'>
                <div className='Edit-Image'>
                    <img src={Edit} alt="Edit-Img" style={{width:"10%",height:"10%"}}/>
                </div>
                <div className='Edit-des'>
                      본인이 만든 픽토그램을 <br />취향에 맞게 자유롭게 수정해 보세요! <br />
                </div>
                <Link to="/Edit">
                    <button className='Edit-Button'>
                    픽토수정
                    </button>
                </Link>
                
            </div> 

            <div className='Things-Content'>
                <div className='Things-Image'>
                    <img src={Things} alt="Edit-Img" style={{width:"10%",height:"10%"}}/>
                </div>
                <div className='Edit-des'>
                      사물을 인식하여 <br />사물 형태의 그래픽을 생성해줍니다.<br />
                      카메라에 사물에 맞춰 그래픽을 생성해 보세요!
                </div>
                <Link to="/Things">
                    <button className='Things-Button'>
                    사물인식
                    </button>
                </Link>
            </div>   
            
          </div>

        </div>
        /*ㅁㄴㅇㅁㄴㅇ */
      );
    }
  }

export default SelectCotent;