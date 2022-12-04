import React, { useEffect } from 'react'
import {NotificationContainer, NotificationManager} from 'react-notifications';

class Love extends React.Component {
    constructor() {
      super();
      this.state = {
        liked: false
      };
      this.handleClick = this.handleClick.bind(this);
    } 
    
    handleClick() {
        console.log("there you go");
      this.setState({
        liked: !this.state.liked
      });
      
    }
    
    render() {
      const text = this.state.liked ? 'liked' : 'haven\'t liked';
      const label = this.state.liked ? 'UnLove' : 'Love'
      return (
<>
          <button className="btn btn-primary" onClick={this.handleClick}>
            {label}</button>
            </>
      );
    }
  }
  
  export default Love;