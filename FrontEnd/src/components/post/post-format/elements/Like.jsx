import React, { useState,useEffect } from 'react';


const LikeButton = ({slug}) => {
  const [likes, setLikes] = useState(0);
  const [isClicked, setIsClicked] = useState(false);

  useEffect(() => {
    fetch('http://localhost:8080/likecount?articleId='+slug
  )
    .then(response => {
       
      if (response.status == 200) {
        console.log('go'); 
        return response.json();
        
      } else {
        
        console.log("Something went wrong ...");

      }
        
      }).then(data=>{
            setLikes(data);
            return data;
        //console.log(Mylist);
      });
  });

  const handleClick = () => {
    window.location.reload(false);

    fetch('http://localhost:8080/addlike?articleId='+slug
    )
      .then(response => {
         
        if (response.status == 200) {
          console.log('go'); 
         
          
        } else {
          setLikes(likes+1);
          console.log("Something went wrong ...");
  
        }
          
        });
  };

  return (
    <button className={ `btn btn-primary` } onClick={ handleClick }>
      <span className="likes-counter">{ `Like | ${likes}` }</span>
    </button>
  );
};

export default LikeButton;