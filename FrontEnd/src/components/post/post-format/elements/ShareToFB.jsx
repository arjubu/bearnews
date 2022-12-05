import React, { useEffect,useState } from 'react'
import {NotificationContainer, NotificationManager} from 'react-notifications';
import { useCookies } from 'react-cookie';

const shareToFB = ({slug}) =>{
    const [cookies, setCookie, removeCookie] = useCookies(['username']);

    const [errorMessages, error_login] = useState('');
    const renderErrorMessage = (name) =>
    name === errorMessages.name && (
      <div className="error">{errorMessages.message}</div>
    );

   const handleClick=()=> {
        if(cookies.username == undefined){
            error_login({ name: 'ID', message: "You have to login first"});
        }
           else{ }
      
    }
    

      return (
<>
          <button className="btn btn-primary" onClick={handleClick}>
            Share to FaceBook</button>
            {renderErrorMessage("ID")}
            <NotificationContainer/>

            </>
      );
    
  }
  
  export default shareToFB;