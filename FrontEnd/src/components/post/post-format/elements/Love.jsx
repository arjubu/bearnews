import React, { useEffect,useState } from 'react'
import {NotificationContainer, NotificationManager} from 'react-notifications';
import { useCookies } from 'react-cookie';

const Love = ({slug}) =>{
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
           else{  NotificationManager.info('Added to your favorite article');
            fetch('http://137.184.37.205:8080/addfav?articleId='+ slug+'&&userEmail=' +cookies.username
        )
          .then(response => {
            });}
      
    }
    

      return (
<>
          <button className="btn btn-primary" onClick={handleClick}>
            Love</button>
            {renderErrorMessage("ID")}
            <NotificationContainer/>

            </>
      );
    
  }
  
  export default Love;