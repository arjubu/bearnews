import React, { useState,useEffect } from 'react';


const LikeButton = ({slug}) => {
  const [likes, setLikes] = useState(0);
  const [isClicked, setIsClicked] = useState(false);

  useEffect(() => {
    
  });

  const handleClick = () => {
    if (isClicked) {
      setLikes(likes - 1);
    } else {
      setLikes(likes + 1);
    }
    setIsClicked(!isClicked);
  };

  return (
    <button className={ `btn btn-primary` } onClick={ handleClick }>
      <span className="likes-counter">{ `Like | ${likes}` }</span>
    </button>
  );
};

export default LikeButton;