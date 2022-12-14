import React, { useState , Component} from "react";
//import ReactDOM from "react-dom";
import logo from '../../public/images/Bear_Mark_1_Color_01.jpg';
import backgroundImage from '../../public/images/Background.jpg';
//import { useNavigate } from 'react-router-dom';
import Image from "next/image";
import Link from "next/link";
import { useCookies } from 'react-cookie';


function OTP() {
    const [errorMessages, error_login] = useState({});

    const [otp, setOTP] = useState();
    const [cookies, setCookie] = useCookies(['username']);
  
    const errors = {
      username: "This user Id does not exit or invalid password",
     
    };
  
    const login_handle = (event) => {
        event.preventDefault();
        console.log(cookies.username);
        console.log(otp);

        fetch('http://137.184.37.205:8080/validateOtp', {
        method: 'POST',
        body: JSON.stringify({
            email: cookies.username,
            otp: otp
        }),
        headers: {
          "Content-type": "application/json; charset=UTF-8"
        }
      })
        .then(response => {
           
          if (response.status == 200) {
            console.log('go'); 
            return response.json();
            
          } else {
            error_login({ name: "ID", message: errors.username});
            throw new Error('Something went wrong ...');
  
          }
            
          }).then(data=>{
            window.location.href = "/";
          });
        
        
    };
  
    const renderErrorMessage = (name) =>
      name === errorMessages.name && (
        <div className="error">{errorMessages.message}</div>
        );

    function otpChangeHandler(input) {
        setOTP(input);
        console.log(otp);
    }
  
      var sectionStyle = {
        width: "100%",
        height: "400px",
        backgroundImage: backgroundImage
      };

    const renderForm = (
      <div className="form">
        <form onSubmit={login_handle}>
          <div className="input-container">
            <label>Your 6 digits</label>
                    <input type="text" name="username" id="username" required onChange={e => otpChangeHandler(e.target.value)}/>
            {renderErrorMessage("username")}
          </div>

          <div className="button-container">
            <input type="submit" value="Confirm"/>
          </div>
          <div className="forgotandreg">
          <div className="regis">
                <a href={"/login"}>
                  <l className="regisText"  n/>Back to Login
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
          <div className="title">Forgot Password</div>
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
       /* if (islogin) {
          navigate('/User/'+id, { state: { id: id}});
        } else {*/
          return (
            renderForm
          )
        //}
      })()}   
        </div>
        </div>
      </div>
    );
  }
  
 // const rootElement = document.getElementById("root");
  //ReactDOM.render(<App />, rootElement);
  export default OTP;