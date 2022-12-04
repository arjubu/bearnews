import React, { useEffect } from 'react'

class Love extends React.Component {
    constructor() {
      super();
      this.state = {
        liked: false
      };
      this.handleClick = this.handleClick.bind(this);
    } 
    
    handleClick() {
      this.setState({
        liked: !this.state.liked
      });
    }
    
    render() {
      const text = this.state.liked ? 'liked' : 'haven\'t liked';
      const label = this.state.liked ? 'UnLove' : 'Love'
      return (

          <button className="btn btn-primary" onClick={this.handleClick}>
            {label}</button>

      );
    }
  }
  
  export default Love;