import React, { useState , Component} from "react";
//import ReactDOM from "react-dom";
import logo from '../../public/images/Bear_Mark_1_Color_01.jpg';
import backgroundImage from '../../public/images/Background.jpg';
//import { useNavigate } from 'react-router-dom';
import Image from "next/image";


function Login() {
    const [errorMessages, error_login] = useState({});
    const [islogin, login_set_true] = useState(false);

    const [username, setUserName] = useState();
    const [password, setPassword] = useState();
    const [repassword, setRepassword] = useState();
    const [firstname, setFirstname] = useState();
    const [lastname, setLastname] = useState();
    const [id, setID] = useState();

  
    const errors = {
      username: "This user Id does not exit or invalid password",
     
    };
  
    const login_handle = (event) => {
      event.preventDefault();
  
      fetch('http://localhost:8080/applyForAccount', {
        method: 'POST',
        body: JSON.stringify({
          email : username,
          password : password,
          firstname : firstname,
          lastname : lastname,
          password : password
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
            error_login({ name: "ID", message: response.json()});
            throw new Error('Something went wrong ...');
  
          }
            
          }).then(data=>{

            navigator("/login");
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
            <label>User Id </label>
            <input type="text" name="username" id="username" required onChange={e => setUserName(e.target.value)}/>
            {renderErrorMessage("username")}
          </div>
          <div className="input-container">
            <label>Password </label>
            <input type="text" name="Password" required onChange={e => setPassword(e.target.value)}/>
            {renderErrorMessage("Password")}{renderErrorMessage("ID")}
          </div>
          <div className="input-container">
            <label>Re-enter Password </label>
            <input type="text" name="Password" required onChange={e => setRepassword(e.target.value)}/>
            {renderErrorMessage("Password")}{renderErrorMessage("ID")}
          </div>
          <div className="input-container">
            <label>First Name </label>
            <input type="text" name="Password" required onChange={e => setFirstname(e.target.value)}/>
            {renderErrorMessage("Password")}{renderErrorMessage("ID")}
          </div>
          <div className="input-container">
            <label>Last Name </label>
            <input type="text" name="Password" required onChange={e => setLastname(e.target.value)}/>
            {renderErrorMessage("Password")}{renderErrorMessage("ID")}
          </div>
          <div className="button-container">
            <input type="submit" value="Login"/>
          </div>
          <div className="forgotandreg">


                <div className="forgotP">
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
          <div className="title">Sign Up</div>
          <a>
                    <Image
                    className="logo"
                      src="/images/Bear_Mark_1_Color_01.jpg"
                      alt="brand-logo"
                      width={100}
                      height={80}
                    />
                  </a>
          {(() => {
        if (islogin) {
          navigate('/User/'+id, { state: { id: id}});
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
  
 // const rootElement = document.getElementById("root");
  //ReactDOM.render(<App />, rootElement);
  export default Login;