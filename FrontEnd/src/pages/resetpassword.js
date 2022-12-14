import React, { useState , Component} from "react";
//import ReactDOM from "react-dom";
import logo from '../../public/images/Bear_Mark_1_Color_01.jpg';
import backgroundImage from '../../public/images/Background.jpg';
//import { useNavigate } from 'react-router-dom';
import Image from "next/image";
import Link from "next/link";


function Login() {
    const [errorMessages, error_login] = useState({});
    const [islogin, login_set_true] = useState(false);
    const [username, setUserName] = useState();
    const [password, setPassword] = useState();
    const [otp, setOtp] =useState("");
    const [repassword, setRepassword] = useState("");
    const [id, setID] = useState();

  
    const errors = {
      username: "This user Id does not exit or invalid password",
     
    };
  
    const login_handle = (event) => {
        event.preventDefault();
    
          fetch('http://137.184.37.205:8080/resetPassword', {
          method: 'POST',
          body: JSON.stringify({
            username : username,
            otp:otp,
            newPassword:password,
            retypeNewPassword:repassword
          }),
          headers: {
            "Content-type": "application/json; charset=UTF-8"
          }
        })
          .then(response => {
            return response.json();
             
            }).then(data=>{
              if (data.data != undefined) {
                window.location.href =  "/login";
              }else{
                error_login({ name: "ID", message: data.responseMessage.message});
              }
            });
          
          
      };
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
        <form onSubmit={login_handle}>
          <div className="input-container">
            <label>Email address</label>
            <input type="text" name="username" id="username" required onChange={e => setUserName(e.target.value)}/>
           
            <label>Code</label>
            <input type="text" name="username" id="username" required onChange={e => setOtp(e.target.value)}/>
            <label>New password</label>
            <input type="password" name="username" id="username" required onChange={e => setPassword(e.target.value)}/>
            <label>Retype password</label>
            <input type="password" name="username" id="username" required onChange={e => setRepassword(e.target.value)}/>
            {renderErrorMessage("ID")}
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

          return (
            renderForm
          )
        
      })()}   
        </div>
        </div>
      </div>
    );
  }
  
 // const rootElement = document.getElementById("root");
  //ReactDOM.render(<App />, rootElement);
  export default Login;