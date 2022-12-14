import React, { useState , Component, useEffect} from "react";
//import ReactDOM from "react-dom";
import logo from '../../public/images/Bear_Mark_1_Color_01.jpg';
import backgroundImage from '../../public/images/Background.jpg';
//import { useNavigate } from 'react-router-dom';
import Image from "next/image";
import Link from "next/link";
import { useCookies } from 'react-cookie';


function Login() {
    const [errorMessages, error_login] = useState('');
    const [islogin, login_set_true] = useState(false);
    const [username, setUserName] = useState();
    const [password, setPassword] = useState();
    const [id, setID] = useState();
    const [cookies, setCookie, removeCookie] = useCookies(['username']);

  
    const errors = {
      username: "This user Id does not exit or invalid password",
     
    };
    const [err, setErr] = useState('');

    useEffect(() => {
    }, [password]);

    const test = (event) => {
        event.preventDefault();
        console.log('Test')

        fetch('http://137.184.37.205:8080/userLogin', {
            mode: 'cors',
            method: 'POST',
            body: JSON.stringify({
                email: username,
                password: password,
            }),
            headers: {
                'Content-type': 'application/json',
            },
        })
            .then(response => response.json())
            .then((response) => {
                console.log('response', response);
                if (response.hasError == false) {
                    console.log('login-working');
                    setCookie('username', username);
                    login_set_true(true);
                } else {
                    console.log(response)
                    setErr(response.responseMessage);
                    console.log(err);
                    error_login({ name: 'ID', message: response.responseMessage.message });
                }
            })
            .then((data) => {
                console.log('---data---',);
             
            });
    }


    const renderErrorMessage = (name) =>
      name === errorMessages.name && (
        <div className="error">{errorMessages.message}</div>
      );
  
      var sectionStyle = {
        width: "100%",
        height: "400px",
        backgroundImage: backgroundImage
      };

    const renderForm = (
        <div className="form">
            <form onSubmit={test}>
          <div className="input-container">
            <label>User Id </label>
            <input type="text" name="username" id="username" required onChange={e => setUserName(e.target.value)}/>
            {renderErrorMessage("username")}
          </div>
          <div className="input-container">
            <label>Password </label>
            <input type="password" name="Password" required onChange={e => setPassword(e.target.value)}/>
            {renderErrorMessage("Password")}{renderErrorMessage("ID")}
          </div>
          <div className="button-container">
            <input type="submit" value="Login"/>
          </div>
          <div className="forgotandreg">
          <div className="regis">
                <a href={"/registration"}>
                  <l className="regisText"  n/>Sign up
                </a>
                </div>

                <div className="forgotP">
                <a href={"/forgotpassword"}>
                  <l className="regisText"  n/>Forget Password?
                </a>
                </div>
            </div>

        </form>
      </div>
    );
  

    return (
<div style={{
        backgroundImage: `url(${backgroundImage.src})` ,
        height:'100%',
        backgroundSize: 'cover',
        backgroundRepeat: 'no-repeat',
                        }}>
      <div className="app">
        <div className="login_frame">
          <div className="title">Log In</div>
          <Link href="/">
          <a>
                    <Image
                    className="logo"
                      src="/images/Bear_Mark_1_Color_01.jpg"
                      alt="brand-logo"
                      width={100}
                      height={80}
                    />
                  </a>
                  </Link>
          {(() => {
                        if (islogin) {
                            window.location.href = "/userHome";
                        } else {
          return (
            renderForm
          )
        }
      })()}   
        </div>
        </div>
      </div>
    );
  }
  
  export default Login;
