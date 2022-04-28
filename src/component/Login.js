import React, { Component } from 'react';
import "../css/Login.css"


class Login extends Component{
    render(){
      return(
       <div className='Login'>
            <div className='Login-Content'>
                <div className='Input-Content'>
                    <div className='Login-Text'>
                        Login
                    </div>

                    <form action="" method="get">
                        <div className='form'>
                            <label>E-mail</label>
                            <input type={'email'} />
                        </div>
                        <div className='form'>
                            <label>Password</label>
                            <input type={'password'} />
                        </div>
                        <div class="form-example">
                            <input type="submit" value="로그인" />
                        </div>
                    </form>
                </div>

                <div className='UI-Content'>
                    asdfasdfasdfasdfasdf
                </div>
            </div>
        </div>

      );
    }
  }

export default Login;